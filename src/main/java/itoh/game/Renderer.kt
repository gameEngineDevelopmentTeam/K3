package itoh.game

import itoh.engine.Utils
import itoh.engine.Window
import itoh.engine.polygon.Obj3D
import itoh.engine.polygon.Transformation
import itoh.engine.polygon.Shader
import org.joml.Matrix4f
import org.lwjgl.opengl.GL11.GL_COLOR_BUFFER_BIT
import org.lwjgl.opengl.GL11.GL_DEPTH_BUFFER_BIT
import org.lwjgl.opengl.GL11.glClear
import org.lwjgl.opengl.GL11.glViewport


open class Renderer {
    companion object {
        private val fov: Float = Math.toRadians(60.0).toFloat()
        private val zNear: Float = 0.01f
        private val zFar: Float = 1000.0f
    }
    private val transformation: Transformation
    private lateinit var shader: Shader

    open fun clear() {
        glClear(GL_COLOR_BUFFER_BIT or GL_DEPTH_BUFFER_BIT)
    }

    open fun render(window: Window, objects: Array<Obj3D>) {
        clear()
        if (window.getResized()) {
            glViewport(0, 0, window.getWidth(), window.getHeight())
            window.setResized(false)
        }
        shader.bind()

        val projectionMatrix: Matrix4f = transformation.getProjectionMatrix(
                fov = fov,
                width = window.getWidth(),
                height = window.getHeight(),
                zNear = zNear,
                zFar = zFar
        )
        shader.setUniform("projectionMatrix", projectionMatrix)

        shader.setUniform("Texture", 0)

        for (i in objects) {
            val worldMatrix: Matrix4f = transformation.getWorldMatrix(
                    i.getPosition(),
                    i.getRotation(),
                    i.getScale()
            )
            shader.setUniform("worldMatrix", worldMatrix)
            i.getMesh().render()
        }
        shader.unbind()
    }

    open fun cleanup() {
        shader.cleanup()
    }

    fun initialization(window: Window) {
        shader = Shader()
        shader.createVertexShader(Utils.loadResource("vertex.glsl"))
        shader.createFragmentShader(Utils.loadResource("fragment.glsl"))
        shader.link()
        shader.createUniform("projectionMatrix")
        shader.createUniform("worldMatrix")
        shader.createUniform("Texture")
    }

    init {
        transformation = Transformation()
    }
}
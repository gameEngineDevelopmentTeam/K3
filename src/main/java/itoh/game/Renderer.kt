package itoh.game

import itoh.engine.Utils
import itoh.engine.Window
import itoh.engine.polygon.Camera
import itoh.engine.polygon.Obj3D
import itoh.engine.polygon.Shader
import itoh.engine.polygon.Transformation
import org.joml.Matrix4f
import org.lwjgl.opengl.GL11.GL_COLOR_BUFFER_BIT
import org.lwjgl.opengl.GL11.GL_DEPTH_BUFFER_BIT
import org.lwjgl.opengl.GL11.glClear
import org.lwjgl.opengl.GL11.glViewport

open class Renderer {
    companion object {
        private val fov: Float = Math.toRadians(60.0).toFloat()
        private const val zNear: Float = 0.01f
        private const val zFar: Float = 1000.0f
    }

    private val transformation: Transformation = Transformation()
    private lateinit var shader: Shader

    open fun clear() {
        glClear(GL_COLOR_BUFFER_BIT or GL_DEPTH_BUFFER_BIT)
    }

    open fun render(window: Window, camera: Camera, objects: Array<Obj3D>) {
        clear()
        if (window.resized) {
            glViewport(0, 0, window.getWidth(), window.getHeight())
            window.resized = true
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
        val viewMatrix = transformation.getViewMatrix(camera)

        shader.setUniform("Texture", 0)

        for (i in objects) {
            val mesh = i.getMesh()
            val modelViewMatrix = transformation.getModelViewMatrix(i, viewMatrix) ?: throw Exception()
            shader.setUniform("modelViewMatrix", modelViewMatrix)
            shader.setUniform("color", mesh.color)
            shader.setUniform("useColor", if (mesh.isTextured()) 0 else 1)
            mesh.render()
        }
        shader.unbind()
    }

    open fun cleanup() {
        shader.cleanup()
    }

    fun initialization() {
        shader = Shader()
        shader.createVertexShader(Utils.loadResource("vertex.glsl"))
        shader.createFragmentShader(Utils.loadResource("fragment.glsl"))
        shader.link()
        shader.createUniform("projectionMatrix")
        shader.createUniform("modelViewMatrix")
        shader.createUniform("Texture")
        shader.createUniform("color")
        shader.createUniform("useColor")
    }
}
package itoh.game

import itoh.engine.Utils
import itoh.engine.Window
import itoh.engine.graph.Shader
import itoh.engine.graph.poly.Obj3D
import itoh.engine.graph.poly.Transformation
import org.lwjgl.opengl.GL11.GL_COLOR_BUFFER_BIT
import org.lwjgl.opengl.GL11.GL_DEPTH_BUFFER_BIT
import org.lwjgl.opengl.GL11.glClear
import org.lwjgl.opengl.GL11.glViewport


open class Renderer {
    private val fov = Math.toRadians(60.0).toFloat()  // 視野角
    private val zNear = 0.01f  // 最小距離
    private val zFar = 1000f  // 最大距離
    private var transformation: Transformation = Transformation()
    var shader: Shader = Shader()

    fun initialization(window: Window) {
        shader.createVertexShader(Utils.loadResource("vertex.glsl"))
        shader.createFragmentShader(Utils.loadResource("fragment.glsl"))
        shader.link()

        shader.createUniform("projectionMatrix");
        shader.createUniform("worldMatrix");

        window.setClearColor(0.0f, 0.0f, 0.0f, 0.0f);
    }

    open fun clear() {
        glClear(GL_COLOR_BUFFER_BIT or GL_DEPTH_BUFFER_BIT)
    }

    open fun render(window: Window, obj3D: Array<Obj3D>) {
        clear()
        if (window.isResized()) {
            glViewport(0, 0, window.getWidth(), window.getHeight())
            window.setResized(false)
        }
        shader.bind()

        val projectionMatrix = transformation.getProjectionMatrix(fov, window.getWidth().toFloat(), window.getHeight().toFloat(), zNear, zFar)
        shader.setUniform("projectionMatrix", projectionMatrix)

        
        for (gameItem in obj3D) {
            
            val worldMatrix = transformation.getWorldMatrix(
                    gameItem.getPosition(),
                    gameItem.getRotation(),
                    gameItem.getScale())
            shader.setUniform("worldMatrix", worldMatrix)
            
            gameItem.getMesh().render()
        }
        shader.unbind()
    }

    open fun cleanup() {
        shader.cleanup()
    }
}
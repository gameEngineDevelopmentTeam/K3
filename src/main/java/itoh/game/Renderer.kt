package itoh.game

import itoh.engine.Utils
import itoh.engine.Window
import itoh.engine.graph.Mesh
import itoh.engine.graph.Shader
import org.lwjgl.opengl.GL11.GL_COLOR_BUFFER_BIT
import org.lwjgl.opengl.GL11.GL_DEPTH_BUFFER_BIT
import org.lwjgl.opengl.GL11.GL_TRIANGLES
import org.lwjgl.opengl.GL11.GL_UNSIGNED_INT
import org.lwjgl.opengl.GL11.glClear
import org.lwjgl.opengl.GL11.glDrawElements
import org.lwjgl.opengl.GL11.glViewport
import org.lwjgl.opengl.GL30.glBindVertexArray


open class Renderer {
    private var shader: Shader

    init {
        shader = Shader()
    }

    fun initialization() {
        shader.createVertexShader(Utils.loadResource("vertex.glsl"))
        shader.createFragmentShader(Utils.loadResource("fragment.glsl"))
        shader.link()
    }

    open fun clear() {
        glClear(GL_COLOR_BUFFER_BIT or GL_DEPTH_BUFFER_BIT)
    }

    open fun render(window: Window, mesh: Mesh) {
        clear()
        if (window.getResized()) {
            glViewport(0, 0, window.getWidth(), window.getHeight())
            window.setResized(false)
        }
        shader.bind()

        glBindVertexArray(mesh.getVaoId())
        glDrawElements(GL_TRIANGLES, mesh.getVertexCount(), GL_UNSIGNED_INT, 0)

        glBindVertexArray(0)
        shader.unbind()
    }

    open fun cleanup() {
        shader.cleanup()
    }
}
package itoh.game

import itoh.engine.Utils
import itoh.engine.Window
import itoh.engine.graph.Mesh
import itoh.engine.graph.ShaderProgram
import org.lwjgl.opengl.GL11.GL_COLOR_BUFFER_BIT
import org.lwjgl.opengl.GL11.GL_DEPTH_BUFFER_BIT
import org.lwjgl.opengl.GL11.GL_TRIANGLES
import org.lwjgl.opengl.GL11.GL_UNSIGNED_INT
import org.lwjgl.opengl.GL11.glClear
import org.lwjgl.opengl.GL11.glDrawElements
import org.lwjgl.opengl.GL11.glViewport
import org.lwjgl.opengl.GL30.glBindVertexArray


open class Renderer {
    var shaderProgram: ShaderProgram = ShaderProgram()

    @Throws(java.lang.Exception::class)
    fun initialization() {
        shaderProgram.createVertexShader(Utils.loadResource("/vertex.glsl"))
        shaderProgram.createFragmentShader(Utils.loadResource("/fragment.glsl"))
        shaderProgram.link()
    }

    open fun clear() {
        glClear(GL_COLOR_BUFFER_BIT or GL_DEPTH_BUFFER_BIT)
    }

    open fun render(window: Window, mesh: Mesh) {
        clear()
        if (window.isResized()) {
            glViewport(0, 0, window.getWidth(), window.getHeight())
            window.setResized(false)
        }
        shaderProgram.bind()

        glBindVertexArray(mesh.getVaoId())
        glDrawElements(GL_TRIANGLES, mesh.getVertexCount(), GL_UNSIGNED_INT, 0)

        glBindVertexArray(0)
        shaderProgram.unbind()
    }

    open fun cleanup() {
        shaderProgram.cleanup()
    }
}
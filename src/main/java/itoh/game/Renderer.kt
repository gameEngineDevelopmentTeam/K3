package itoh.game

import itoh.engine.Utils
import itoh.engine.Window
import itoh.engine.graph.ShaderProgram
import org.lwjgl.opengl.ARBVertexArrayObject.glBindVertexArray
import org.lwjgl.opengl.ARBVertexArrayObject.glDeleteVertexArrays
import org.lwjgl.opengl.ARBVertexArrayObject.glGenVertexArrays
import org.lwjgl.opengl.GL11.GL_COLOR_BUFFER_BIT
import org.lwjgl.opengl.GL11.GL_DEPTH_BUFFER_BIT
import org.lwjgl.opengl.GL11.GL_FLOAT
import org.lwjgl.opengl.GL11.GL_TRIANGLES
import org.lwjgl.opengl.GL11.glClear
import org.lwjgl.opengl.GL11.glDrawArrays
import org.lwjgl.opengl.GL11.glViewport
import org.lwjgl.opengl.GL15.GL_ARRAY_BUFFER
import org.lwjgl.opengl.GL15.GL_STATIC_DRAW
import org.lwjgl.opengl.GL15.glBindBuffer
import org.lwjgl.opengl.GL15.glBufferData
import org.lwjgl.opengl.GL15.glDeleteBuffers
import org.lwjgl.opengl.GL15.glGenBuffers
import org.lwjgl.opengl.GL20.glDisableVertexAttribArray
import org.lwjgl.opengl.GL20.glEnableVertexAttribArray
import org.lwjgl.opengl.GL20.glVertexAttribPointer
import org.lwjgl.system.MemoryUtil
import java.nio.FloatBuffer


open class Renderer {
    private var vboId: Int = 0
    private var vaoId: Int = 0
    var shaderProgram: ShaderProgram = ShaderProgram()

    @Throws(java.lang.Exception::class)
    fun initialization() {
        shaderProgram.createVertexShader(Utils.loadResource("/vertex.glsl"))
        shaderProgram.createFragmentShader(Utils.loadResource("/fragment.glsl"))
        shaderProgram.link()
        val vertices = floatArrayOf(
                0.0f, 0.5f, 0.0f,
                -0.5f, -0.5f, 0.0f,
                0.5f, -0.5f, 0.0f
        )

        var verticesBuffer: FloatBuffer? = null
        try {
            verticesBuffer = MemoryUtil.memAllocFloat(vertices.size)
            verticesBuffer.put(vertices).flip()
            vaoId = glGenVertexArrays()
            glBindVertexArray(vaoId)
            vboId = glGenBuffers()
            glBindBuffer(GL_ARRAY_BUFFER, vboId)
            glBufferData(GL_ARRAY_BUFFER, verticesBuffer!!, GL_STATIC_DRAW)
            glEnableVertexAttribArray(0)
            glVertexAttribPointer(0, 3, GL_FLOAT, false, 0, 0)
            glBindBuffer(GL_ARRAY_BUFFER, 0)
            glBindVertexArray(0)
        } finally {
            if (verticesBuffer != null) {
                MemoryUtil.memFree(verticesBuffer)
            }
        }
    }

    open fun clear() {
        glClear(GL_COLOR_BUFFER_BIT or GL_DEPTH_BUFFER_BIT)
    }

    open fun render(window: Window) {
        clear()
        if (window.isResized()) {
            glViewport(0, 0, window.getWidth(), window.getHeight())
            window.setResized(false)
        }
        shaderProgram.bind()
        glBindVertexArray(vaoId)
        glDrawArrays(GL_TRIANGLES, 0, 3)
        glBindVertexArray(0)
        shaderProgram.unbind()
    }

    open fun cleanup() {
        shaderProgram.cleanup()
        glDisableVertexAttribArray(0)
        glBindBuffer(GL_ARRAY_BUFFER, 0)
        glDeleteBuffers(vboId)
        glBindVertexArray(0)
        glDeleteVertexArrays(vaoId)
    }

}
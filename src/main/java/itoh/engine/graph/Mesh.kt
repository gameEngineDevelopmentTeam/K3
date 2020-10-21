package itoh.engine.graph

import org.lwjgl.opengl.GL11.GL_FLOAT
import org.lwjgl.opengl.GL15.GL_ELEMENT_ARRAY_BUFFER
import org.lwjgl.opengl.GL15.glDeleteBuffers
import org.lwjgl.opengl.GL20.glDisableVertexAttribArray
import org.lwjgl.opengl.GL20.glVertexAttribPointer
import org.lwjgl.opengl.GL30.GL_ARRAY_BUFFER
import org.lwjgl.opengl.GL30.GL_STATIC_DRAW
import org.lwjgl.opengl.GL30.glBindBuffer
import org.lwjgl.opengl.GL30.glBindVertexArray
import org.lwjgl.opengl.GL30.glBufferData
import org.lwjgl.opengl.GL30.glDeleteVertexArrays
import org.lwjgl.opengl.GL30.glEnableVertexAttribArray
import org.lwjgl.opengl.GL30.glGenBuffers
import org.lwjgl.opengl.GL30.glGenVertexArrays
import org.lwjgl.system.MemoryUtil
import java.nio.FloatBuffer
import java.nio.IntBuffer

class Mesh(positions: FloatArray, colors: FloatArray, indices: IntArray) {
    private val vaoId: Int
    private val posVboId: Int
    private val colorVboId: Int
    private val idxVboId: Int
    private val vertexCount: Int

    init {
        var posBuffer: FloatBuffer? = null
        var colorBuffer: FloatBuffer? = null
        var indicesBuffer: IntBuffer? = null
        try {
            vertexCount = indices.size
            vaoId = glGenVertexArrays()
            glBindVertexArray(vaoId)

            posVboId = glGenBuffers()
            posBuffer = MemoryUtil.memAllocFloat(positions.size)
            posBuffer.put(positions).flip()
            glBindBuffer(GL_ARRAY_BUFFER, posVboId)
            glBufferData(GL_ARRAY_BUFFER, posBuffer!!, GL_STATIC_DRAW)
            glEnableVertexAttribArray(0)
            glVertexAttribPointer(0, 3, GL_FLOAT, false, 0, 0)

            colorVboId = glGenBuffers()
            colorBuffer = MemoryUtil.memAllocFloat(colors.size)
            colorBuffer.put(colors).flip()
            glBindBuffer(GL_ARRAY_BUFFER, colorVboId)
            glBufferData(GL_ARRAY_BUFFER, colorBuffer!!, GL_STATIC_DRAW)
            glEnableVertexAttribArray(1)
            glVertexAttribPointer(1, 3, GL_FLOAT, false, 0, 0)

            idxVboId = glGenBuffers()
            indicesBuffer = MemoryUtil.memAllocInt(indices.size)
            indicesBuffer.put(indices).flip()
            glBindBuffer(GL_ELEMENT_ARRAY_BUFFER, idxVboId)
            glBufferData(GL_ELEMENT_ARRAY_BUFFER, indicesBuffer!!, GL_STATIC_DRAW)

            glBindBuffer(GL_ARRAY_BUFFER, 0)
            glBindVertexArray(0)
        } finally {
            MemoryUtil.memFree(posBuffer)
            MemoryUtil.memFree(colorBuffer)
            MemoryUtil.memFree(indicesBuffer)
        }
    }

    fun getVaoId(): Int {
        return vaoId
    }

    fun getVertexCount(): Int {
        return vertexCount
    }

    fun cleanUp() {
        glDisableVertexAttribArray(0)

        //VBOを削除
        glBindBuffer(GL_ARRAY_BUFFER, 0)
        glDeleteBuffers(posVboId)
        glDeleteBuffers(colorVboId)
        glDeleteBuffers(idxVboId)

        //VAOを削除
        glBindVertexArray(0)
        glDeleteVertexArrays(vaoId)
    }

}
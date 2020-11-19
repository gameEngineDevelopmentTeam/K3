package itoh.engine.polygon

import org.joml.Vector3f
import org.lwjgl.opengl.GL11.GL_FLOAT
import org.lwjgl.opengl.GL11.GL_TEXTURE_2D
import org.lwjgl.opengl.GL11.GL_TRIANGLES
import org.lwjgl.opengl.GL11.GL_UNSIGNED_INT
import org.lwjgl.opengl.GL11.glBindTexture
import org.lwjgl.opengl.GL11.glDrawElements
import org.lwjgl.opengl.GL15.GL_ELEMENT_ARRAY_BUFFER
import org.lwjgl.opengl.GL15.GL_TEXTURE0
import org.lwjgl.opengl.GL15.glActiveTexture
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

class Mesh constructor(positions: FloatArray, texCoords: FloatArray, normals: FloatArray, indices: IntArray) {
    companion object {
        private val defaultColor = Vector3f(1.0f, 1.0f, 1.0f)
    }

    private val vaoId: Int
    private val vertexCount: Int
    private val vboIdArray: ArrayList<Int> = ArrayList()
    private var color: Vector3f
    private lateinit var texture: Texture

    private lateinit var posBuffer: FloatBuffer
    private lateinit var texCoordsBuffer: FloatBuffer
    private lateinit var vecNormalBuffer: FloatBuffer
    private lateinit var indicesBuffer: IntBuffer

    init {
        try {
            color = defaultColor
            vertexCount = indices.size

            vaoId = glGenVertexArrays()
            glBindVertexArray(vaoId)

            var vboId: Int = glGenBuffers()
            vboIdArray.add(vboId)
            posBuffer = MemoryUtil.memAllocFloat(positions.size)
            posBuffer.put(positions).flip()
            glBindBuffer(GL_ARRAY_BUFFER, vboId)
            glBufferData(GL_ARRAY_BUFFER, posBuffer, GL_STATIC_DRAW)
            glEnableVertexAttribArray(0)
            glVertexAttribPointer(0, 3, GL_FLOAT, false, 0, 0)

            vboId = glGenBuffers()
            vboIdArray.add(vboId)
            texCoordsBuffer = MemoryUtil.memAllocFloat(texCoords.size)
            texCoordsBuffer.put(texCoords).flip()
            glBindBuffer(GL_ARRAY_BUFFER, vboId)
            glBufferData(GL_ARRAY_BUFFER, texCoordsBuffer, GL_STATIC_DRAW)
            glEnableVertexAttribArray(1)
            glVertexAttribPointer(1, 2, GL_FLOAT, false, 0, 0)

            vboId = glGenBuffers()
            vboIdArray.add(vboId)
            vecNormalBuffer = MemoryUtil.memAllocFloat(normals.size)
            vecNormalBuffer.put(normals).flip()
            glBindBuffer(GL_ARRAY_BUFFER, vboId)
            glBufferData(GL_ARRAY_BUFFER, vecNormalBuffer, GL_STATIC_DRAW)
            glEnableVertexAttribArray(2)
            glVertexAttribPointer(2, 3, GL_FLOAT, false, 0, 0)

            vboId = glGenBuffers()
            vboIdArray.add(vboId)
            indicesBuffer = MemoryUtil.memAllocInt(indices.size)
            indicesBuffer.put(indices).flip()
            glBindBuffer(GL_ELEMENT_ARRAY_BUFFER, vboId)
            glBufferData(GL_ELEMENT_ARRAY_BUFFER, indicesBuffer, GL_STATIC_DRAW)

            glBindBuffer(GL_ARRAY_BUFFER, 0)
            glBindVertexArray(0)
        } finally {
            MemoryUtil.memFree(posBuffer)
            MemoryUtil.memFree(texCoordsBuffer)
            MemoryUtil.memFree(vecNormalBuffer)
            MemoryUtil.memFree(indicesBuffer)
        }
    }

    internal fun render() {
        glActiveTexture(GL_TEXTURE0)
        glBindTexture(GL_TEXTURE_2D, texture.getId())
        glBindVertexArray(getVaoId())
        glDrawElements(GL_TRIANGLES, getVertexCount(), GL_UNSIGNED_INT, 0)
        glBindVertexArray(0)
        glBindTexture(GL_TEXTURE_2D, 0)
    }

    fun isTextured(): Boolean {
        return true
    }

    fun getTexture(): Texture? {
        return texture
    }

    fun setTexture(texture: Texture) {
        this.texture = texture
    }

    fun setColour(color: Vector3f) {
        this.color = color
    }

    fun getColour(): Vector3f {
        return color
    }

    private fun getVaoId(): Int {
        return vaoId
    }

    private fun getVertexCount(): Int {
        return vertexCount
    }

    internal fun cleanUp() {
        glDisableVertexAttribArray(0)

        //VBOを削除
        glBindBuffer(GL_ARRAY_BUFFER, 0)
        for (i in vboIdArray) {
            glDeleteBuffers(i)
        }
        texture.cleanup()
        //VAOを削除
        glBindVertexArray(0)
        glDeleteVertexArrays(vaoId)
    }
}
package itoh.engine.polygon

import org.lwjgl.opengl.GL11.GL_RGBA
import org.lwjgl.opengl.GL11.GL_TEXTURE_2D
import org.lwjgl.opengl.GL11.GL_UNPACK_ALIGNMENT
import org.lwjgl.opengl.GL11.GL_UNSIGNED_BYTE
import org.lwjgl.opengl.GL11.glBindTexture
import org.lwjgl.opengl.GL11.glDeleteTextures
import org.lwjgl.opengl.GL11.glGenTextures
import org.lwjgl.opengl.GL11.glPixelStorei
import org.lwjgl.opengl.GL11.glTexImage2D
import org.lwjgl.opengl.GL30.glGenerateMipmap
import org.lwjgl.stb.STBImage.stbi_image_free
import org.lwjgl.stb.STBImage.stbi_load
import org.lwjgl.system.MemoryStack
import java.nio.ByteBuffer
import java.nio.IntBuffer

class Texture constructor(fileName: String) {
    private val id: Int

    init {
        id = loadTexture(fileName)
    }

    companion object {
        private fun loadTexture(fileName: String): Int {
            val width: Int
            val height: Int
            val buffer: ByteBuffer
            val textureId: Int = glGenTextures()

            val memoryStack: MemoryStack = MemoryStack.stackPush()
            val w: IntBuffer = memoryStack.mallocInt(1)
            val h: IntBuffer = memoryStack.mallocInt(1)
            val channel: IntBuffer = memoryStack.mallocInt(1)

            buffer = stbi_load(fileName, w, h, channel, 4) ?: throw RuntimeException("err")
            println("テクスチャをロード : $fileName")
            width = w.get()
            height = h.get()

            glBindTexture(GL_TEXTURE_2D, textureId)
            glPixelStorei(GL_UNPACK_ALIGNMENT, 1)

            glTexImage2D(GL_TEXTURE_2D, 0, GL_RGBA, width, height, 0, GL_RGBA, GL_UNSIGNED_BYTE, buffer)

            glGenerateMipmap(GL_TEXTURE_2D)

            stbi_image_free(buffer)

            return textureId
        }
    }

    public fun bind(): Unit {
        glBindTexture(GL_TEXTURE_2D, id)
    }

    public fun getId(): Int {
        return id
    }

    fun cleanup() {
        glDeleteTextures(id)
    }
}
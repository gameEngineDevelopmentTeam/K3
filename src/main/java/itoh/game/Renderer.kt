package itoh.game

import org.lwjgl.opengl.GL11.GL_COLOR_BUFFER_BIT
import org.lwjgl.opengl.GL11.GL_DEPTH_BUFFER_BIT
import org.lwjgl.opengl.GL11.glClear

class Renderer {
    constructor()

    fun initialization(): Unit {}
    fun clear(): Unit {
        glClear(GL_COLOR_BUFFER_BIT.or(GL_DEPTH_BUFFER_BIT))
    }
}
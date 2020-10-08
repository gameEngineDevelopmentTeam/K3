package itoh.game

import itoh.engine.GameLogic
import itoh.engine.Window
import org.lwjgl.glfw.GLFW
import org.lwjgl.opengl.GL11


class Game : GameLogic {
    private var direction = 0
    private var color = 0.0f
    private val renderer: Renderer = Renderer()

    @Throws(Exception::class)
    override fun initialization() {
        renderer.initialization()
    }

    override fun input(window: Window) {
        direction = when {
            window.isKeyPressed(GLFW.GLFW_KEY_UP) -> 1
            window.isKeyPressed(GLFW.GLFW_KEY_DOWN) -> -1
            else -> 0
        }
    }

    override fun update(interval: Float) {
        color += direction * 0.01f
        if (color > 1) {
            color = 1.0f
        } else if (color < 0) {
            color = 0.0f
        }
    }

    override fun render(window: Window) {
        if (window.isResized()) {
            GL11.glViewport(0, 0, window.getWidth(), window.getHeight())
            window.setResized(false)
        }
        window.setClearColor(color, color, color, 0.0f)
        renderer.clear()
    }
}
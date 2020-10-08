package itoh.game

import itoh.engine.IGameLogic
import itoh.engine.Window
import org.lwjgl.glfw.GLFW
import org.lwjgl.opengl.GL11


class Game : IGameLogic {
    private var direction = 0
    private var color = 0.0f
    private val renderer: Renderer

    @Throws(Exception::class)
    override fun initialization():Unit {
        renderer.initialization()
    }

    override fun input(window: Window?) {
        if (window != null) {
            direction = if (window.isKeyPressed(GLFW.GLFW_KEY_UP)) {
                1
            } else if (window.isKeyPressed(GLFW.GLFW_KEY_DOWN)) {
                -1
            } else {
                0
            }
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

    override fun render(window: Window?) {
        if (window != null) {
            if (window.isResized()) {
                GL11.glViewport(0, 0, window.getWidth(), window.getHeight())
                window.setResized(false)
            }
        }
        if (window != null) {
            window.setClearColor(color, color, color, 0.0f)
        }
        renderer.clear()
    }

    init {
        renderer = Renderer()
    }
}
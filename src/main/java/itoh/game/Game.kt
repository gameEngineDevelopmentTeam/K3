package itoh.game

import itoh.engine.GameLogic
import itoh.engine.Window
import itoh.engine.graph.Mesh
import org.lwjgl.glfw.GLFW.GLFW_KEY_DOWN
import org.lwjgl.glfw.GLFW.GLFW_KEY_UP


open class Game : GameLogic {
    private var direction = 0
    private var color = 0.0f
    private val renderer: Renderer = Renderer()
    private lateinit var mesh: Mesh

    @Throws(Exception::class)
    override fun initialization() {
        renderer.initialization()
        val positions = floatArrayOf(
                -0.5f, 0.5f, 0.0f,
                -0.5f, -0.5f, 0.0f,
                0.5f, -0.5f, 0.0f,
                0.5f, 0.5f, 0.0f
        )

        val colors = floatArrayOf(
                0.5f, 0.0f, 0.0f,
                0.0f, 0.5f, 0.0f,
                0.0f, 0.0f, 0.5f,
                0.0f, 0.5f, 0.5f
        )

        val indices = intArrayOf(
                0, 1, 3, 3, 1, 2
        )
        mesh = Mesh(positions, colors, indices)
    }

    override fun input(window: Window) {
        direction = when {
            window.isKeyPressed(GLFW_KEY_UP) -> 1
            window.isKeyPressed(GLFW_KEY_DOWN) -> -1
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
        window.setClearColor(color, color, color, 0.0f)
        renderer.render(window, mesh)
    }

    override fun cleanup() {
        renderer.cleanup()
        mesh.cleanUp()
    }
}
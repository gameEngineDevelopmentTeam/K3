package itoh.game

import itoh.engine.GameLogic
import itoh.engine.Window
import itoh.engine.graph.Mesh

open class Game : GameLogic {
    private val bgColor = .0f
    private val renderer: Renderer = Renderer()
    private lateinit var mesh: Mesh

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
    }

    override fun update(interval: Float) {
    }

    override fun render(window: Window) {
        window.setClearColor(bgColor, bgColor, bgColor, 0.0f)
        renderer.render(window, mesh)
    }

    override fun cleanup() {
        renderer.cleanup()
        mesh.cleanUp()
    }
}
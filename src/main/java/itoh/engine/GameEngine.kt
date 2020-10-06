package itoh.engine

import java.lang.Exception

open class GameEngine : Runnable {
    val TARGET_FPS: Int = 75
    val TARGET_UPS: Int = 30
    val window: Window
    val timer: Timer
    val gameLogic: IGameLogic

    constructor(windowTitle: String, width: Int, height: Int, vSync: Boolean, gameLogic: IGameLogic) {
        window = Window(windowTitle, width, height, vSync)
        this.gameLogic = gameLogic
        this.timer = Timer()
    }

    override fun run(): Unit {
        try {
            initialization()
            gameLoop()
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    protected fun initialization(): Unit {
        window.initialization()
        timer.initialization()
        gameLogic.initialization()
    }

    protected fun gameLoop(): Unit {
        var elapsedTime: Float
        var accumulator: Float = 0f
        val interval: Float = 1f / TARGET_UPS
        var running: Boolean = true

        while (running && !window.windowShouldClose()!!) {
            elapsedTime = timer.getElapsedTime()
        }
    }

}
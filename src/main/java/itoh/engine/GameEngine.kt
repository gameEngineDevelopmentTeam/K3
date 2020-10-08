package itoh.engine

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
            accumulator += elapsedTime
            input()
            while (accumulator >= interval) {
                update(interval)
                accumulator -= interval
            }
            render()
            if (!window.isvSync()) {
                sync();
            }
        }
    }

    protected fun sync() {
        val loopSlot = 1f / TARGET_FPS
        val endTime = timer.lastLoopTime + loopSlot
        while (timer.getTime() < endTime) {
            try {
                Thread.sleep(1)
            } catch (ie: InterruptedException) {
            }
        }
    }

    protected open fun input() {
        gameLogic.input(window)
    }

    protected open fun update(interval: Float) {
        gameLogic.update(interval)
    }

    protected open fun render() {
        gameLogic.render(window)
        window.update()
    }

}
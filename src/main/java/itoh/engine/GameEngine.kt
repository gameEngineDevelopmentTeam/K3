package itoh.engine

open class GameEngine(
        windowTitle: String,
        width: Int,
        height: Int,
        vSync: Boolean,
        private val gameLogic: GameLogic
) : Runnable {
    private val targetFPS: Int = 75600
    private val targetUPS: Int = 30
    private val window: Window = Window(windowTitle, width, height, vSync)
    private val timer: Timer = Timer()

    override fun run() {
        try {
            initialization()
            gameLoop()
        } catch (e: Exception) {
            e.printStackTrace()
        } finally {
            cleanup()
        }
    }

    private fun initialization() {
        window.initialization()
        timer.initialization()
        gameLogic.initialization()
    }

    private fun gameLoop() {
        var accumulator: Float = 0f
        val interval: Float = 1f / targetUPS
        while (!window.getWindowShouldClose()!!) {
            accumulator += timer.getElapsedTime()
            input()
            while (accumulator >= interval) {
                update(interval)
                accumulator -= interval
            }
            render()
            if (!window.getVSync()) {
                sync()
            }
        }
    }

    protected open fun cleanup() {
        gameLogic.cleanup()
    }

    private fun sync() {
        val loopSlot = 1f / targetFPS
        val endTime = timer.lastLoopTime + loopSlot
        while (timer.getTime() < endTime) {
            Thread.sleep(1)
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
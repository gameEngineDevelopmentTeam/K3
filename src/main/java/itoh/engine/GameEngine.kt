package itoh.engine

open class GameEngine(
        windowTitle: String,
        width: Int,
        height: Int,
        vSync: Boolean,
        private val gameLogic: GameLogic
) : Runnable {
    private val targetFPS: Int = 75
    private val targetUPS: Int = 30
    private val window: Window = Window(windowTitle, width, height, vSync)
    private val mouseInput = MouseInput()
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
        mouseInput.initialization(window)
        gameLogic.initialization(window)
    }

    private fun gameLoop() {
        var accumulator: Float = 0f
        val interval: Float = 1f / targetUPS.toFloat()
        while (!window.getWindowShouldClose()) {
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

    private fun cleanup() {
        gameLogic.cleanup()
        window.cleanup()
    }

    private fun sync() {
        val loopSlot: Float = 1f / targetFPS.toFloat()
        val endTime: Double = timer.lastLoopTime + loopSlot
        while (timer.getTime() < endTime) {
            Thread.sleep(1)
        }
    }

    private fun input() {
        mouseInput.input(window)
        gameLogic.input(window, mouseInput)
    }

    private fun update(interval: Float) {
        gameLogic.update(interval, mouseInput)
    }

    private fun render() {
        gameLogic.render(window)
        window.update()
    }

}
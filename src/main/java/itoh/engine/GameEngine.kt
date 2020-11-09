package itoh.engine

open class GameEngine(
        windowTitle: String,
        width: Int,
        height: Int,
        vSync: Boolean,
        private val gameLogic: GameLogic
) : Runnable {
    private val targetFPS: Int
    private val targetUPS: Int
    private val window: Window
    private val timer: Timer

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

    internal fun initialization() {
        window.initialization()
        timer.initialization()
        gameLogic.initialization(window)
    }

    internal fun gameLoop() {
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

    internal fun cleanup() {
        gameLogic.cleanup()
    }

    private fun sync() {
        val loopSlot: Float = 1f / targetFPS.toFloat()
        val endTime: Double = timer.lastLoopTime + loopSlot
        while (timer.getTime() < endTime) {
            Thread.sleep(1)
        }
    }

    internal fun input() {
        gameLogic.input(window)
    }

    internal fun update(interval: Float) {
        gameLogic.update(interval)
    }

    internal fun render() {
        gameLogic.render(window)
        window.update()
    }

    init {
        targetFPS = 75
        targetUPS =1
        window = Window(windowTitle, width, height, vSync)
        timer = Timer()
    }
}
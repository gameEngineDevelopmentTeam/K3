package itoh.engine

interface GameLogic {
    fun initialization(window: Window)
    fun input(window: Window)
    fun update(interval: Float)
    fun render(window: Window)
    fun cleanup()
}
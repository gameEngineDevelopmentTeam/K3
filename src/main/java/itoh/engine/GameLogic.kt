package itoh.engine

interface GameLogic {
    fun initialization()
    fun input(window: Window)
    fun update(interval: Float)
    fun render(window: Window)
}
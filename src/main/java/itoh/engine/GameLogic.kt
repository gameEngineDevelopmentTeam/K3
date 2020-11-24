package itoh.engine

interface GameLogic {
    fun initialization(window: Window)
    fun input(window: Window, mouseInput: MouseInput)
    fun update(interval: Float, mouseInput: MouseInput)
    fun render(window: Window)
    fun cleanup()
}
package itoh.engine

interface IGameLogic {
    fun initialization(): Unit
    fun input(window: Window?): Unit
    fun update(interval: Float): Unit
    fun render(window: Window?): Unit
}
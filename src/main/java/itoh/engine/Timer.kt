package itoh.engine

class Timer(var lastLoopTime: Double) {
    init {
        var lastLoopTime = getTime()
    }

    fun initialization(): Unit {
        var lastLoopTime = getTime()
    }

    fun getTime(): Double {
        return System.nanoTime() / 1_000_000_000.0
    }

    fun getElapsedTime(): Float {
        val time: Double = getTime()
        val elapsedTime: Float = (time - lastLoopTime).toFloat()
        lastLoopTime = time
        return elapsedTime
    }
}
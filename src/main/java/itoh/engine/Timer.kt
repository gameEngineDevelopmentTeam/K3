package itoh.engine

class Timer {
    var lastLoopTime: Double = 0.0

    fun initialization() {
        lastLoopTime = getTime()
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
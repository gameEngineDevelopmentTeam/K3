package itoh.game

import itoh.engine.GameEngine
import itoh.engine.GameLogic
import kotlin.system.exitProcess

fun main() {
    try {
        val vSync = true
        val gameLogic: GameLogic = Game()
        val gameEng = GameEngine("GAME", 600, 480, vSync, gameLogic)
        gameEng.run()
    } catch (e: Exception) {
        e.printStackTrace()
        exitProcess(1)
    }
}
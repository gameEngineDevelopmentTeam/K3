package itoh.game

import itoh.engine.GameEngine
import itoh.engine.IGameLogic

fun main() {
    try {
        val vSync = true
        val gameLogic: IGameLogic = Game()
        val gameEng = GameEngine("GAME", 600, 480, vSync, gameLogic)
        gameEng.run()
    } catch (excp: Exception) {
        excp.printStackTrace()
        System.exit(-1)
    }
}

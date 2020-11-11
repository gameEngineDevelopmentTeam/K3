package itoh.game

import itoh.engine.GameEngine
import itoh.engine.GameLogic

fun main() {
    val vSync = true
    val gameLogic: GameLogic = Game()
    val gameEngine = GameEngine("ITOH", 600, 480, vSync, gameLogic)
    gameEngine.run()
}
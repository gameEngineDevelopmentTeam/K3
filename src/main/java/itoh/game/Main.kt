package itoh.game

import itoh.engine.GameEngine
import itoh.engine.GameLogic

fun main() {
    val vSync = true  // 垂直同期 現在はデフォルトでtrue
    val gameLogic: GameLogic = Game()
    val gameEngine = GameEngine("ITOH", 600, 480, vSync, gameLogic)
    gameEngine.run()
}
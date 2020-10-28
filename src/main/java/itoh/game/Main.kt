package itoh.game

import itoh.engine.GameEngine
import itoh.engine.GameLogic
import itoh.engine.graph.Mesh
import itoh.engine.graph.poly.Obj3D
import kotlin.system.exitProcess

fun main() {
    try {
        val vSync = true
        val gameLogic: GameLogic = Game()
        val gameEngine = GameEngine("ITOH", 600, 480, vSync, gameLogic)
        gameEngine.run()
    } catch (e: Exception) {
        e.printStackTrace()
        exitProcess(1)
    }
}
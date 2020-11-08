package itoh.game

import itoh.engine.GameLogic
import itoh.engine.Window
import itoh.engine.graph.Mesh
import itoh.engine.graph.poly.Obj3D
import org.joml.Vector3f
import org.lwjgl.glfw.GLFW.GLFW_KEY_A
import org.lwjgl.glfw.GLFW.GLFW_KEY_DOWN
import org.lwjgl.glfw.GLFW.GLFW_KEY_LEFT
import org.lwjgl.glfw.GLFW.GLFW_KEY_Q
import org.lwjgl.glfw.GLFW.GLFW_KEY_RIGHT
import org.lwjgl.glfw.GLFW.GLFW_KEY_UP
import org.lwjgl.glfw.GLFW.GLFW_KEY_X
import org.lwjgl.glfw.GLFW.GLFW_KEY_Z


open class Game : GameLogic {
    private var displxInc = 0
    private var displyInc = 0
    private var displzInc = 0
    private var scaleInc = 0
    private val renderer: Renderer = Renderer()
    private lateinit var obj3d: Array<Obj3D>


    override fun initialization(window: Window) {
        renderer.initialization(window)
        val positions = floatArrayOf(
                -0.5f, 0.5f, 0.5f,
                -0.5f, -0.5f, 0.5f,
                0.5f, -0.5f, 0.5f,
                0.5f, 0.5f, 0.5f
        )

        val colors = floatArrayOf(
                0.5f, 0.0f, 0.0f,
                0.0f, 0.5f, 0.0f,
                0.0f, 0.0f, 0.5f,
                0.0f, 0.5f, 0.5f
        )

        val indices = intArrayOf(
                0, 1, 3, 3, 1, 2
        )
        val mesh = Mesh(positions, colors, indices)
        val obj = Obj3D(mesh)
        obj.setPosition(0f, 0f, -2f)
        obj3d = arrayOf(obj)
    }

    override fun input(window: Window) {
        displyInc = 0
        displxInc = 0
        displzInc = 0
        scaleInc = 0
        when {
            window.isKeyPressed(GLFW_KEY_UP) -> {
                displyInc = 1
            }
            window.isKeyPressed(GLFW_KEY_DOWN) -> {
                displyInc = -1
            }
            window.isKeyPressed(GLFW_KEY_LEFT) -> {
                displxInc = -1
            }
            window.isKeyPressed(GLFW_KEY_RIGHT) -> {
                displxInc = 1
            }
            window.isKeyPressed(GLFW_KEY_A) -> {
                displzInc = -1;
            }
            window.isKeyPressed(GLFW_KEY_Q) -> {
                displzInc = 1;
            }
            window.isKeyPressed(GLFW_KEY_Z) -> {
                scaleInc = -1;
            }
            window.isKeyPressed(GLFW_KEY_X) -> {
                scaleInc = 1;
            }
        }
    }

    override fun update(interval: Float) {
        for (i in obj3d) {

            val itemPos: Vector3f = i.getPosition()
            val posx = itemPos.x + displxInc * 0.01f
            val posy = itemPos.y + displyInc * 0.01f
            val posz = itemPos.z + displzInc * 0.01f
            i.setPosition(posx, posy, posz)


            var scale: Float = i.getScale()
            scale += scaleInc * 0.05f
            if (scale < 0) {
                scale = 0f
            }
            i.setScale(scale)


            var rotation: Float = i.getRotation().z + 1.5f
            if (rotation > 360) {
                rotation = 0f
            }
            i.setRotation(0f, 0f, rotation)
        }
    }

    override fun render(window: Window) {
        renderer.render(window, obj3d)
    }

    override fun cleanup() {
        renderer.cleanup()
        for (i in obj3d) {
            i.getMesh().cleanUp()
        }
    }
}
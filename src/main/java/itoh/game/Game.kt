package itoh.game

import itoh.engine.GameLogic
import itoh.engine.Window
import itoh.engine.polygon.three_dimensional.Obj3D
import itoh.engine.polygon.two_dimensional.Mesh
import org.lwjgl.glfw.GLFW.GLFW_KEY_A
import org.lwjgl.glfw.GLFW.GLFW_KEY_DOWN
import org.lwjgl.glfw.GLFW.GLFW_KEY_LEFT
import org.lwjgl.glfw.GLFW.GLFW_KEY_Q
import org.lwjgl.glfw.GLFW.GLFW_KEY_RIGHT
import org.lwjgl.glfw.GLFW.GLFW_KEY_UP
import org.lwjgl.glfw.GLFW.GLFW_KEY_X
import org.lwjgl.glfw.GLFW.GLFW_KEY_Z


open class Game : GameLogic {
    private var dX: Int = 0
    private var dY: Int = 0
    private var dZ: Int = 0
    private var scale: Int = 0
    private val renderer: Renderer
    private lateinit var objects: Array<Obj3D>

    override fun initialization(window: Window) {
        renderer.initialization(window)
        val positions = floatArrayOf(
                -0.5f, 0.5f, 0.0f,
                -0.5f, -0.5f, 0.0f,
                0.5f, -0.5f, 0.0f,
                0.5f, 0.5f, 0.0f
        )

        val colors = floatArrayOf(
                1.0f, 0.0f, 0.0f,
                0.0f, 1.0f, 0.0f,
                0.0f, 0.0f, 1.0f,
                1.0f, 1.0f, 0.0f
        )

        val indices = intArrayOf(
                0, 1, 3, 3, 1, 2
        )
        val mesh = Mesh(positions, colors, indices)
        val obj = Obj3D(mesh)
        obj.setPosition(0f, 0f, -2f)
        objects = arrayOf<Obj3D>(obj)
    }

    override fun input(window: Window) {
        dX = 0
        dY = 0
        dZ = 0
        scale = 0
        if (window.getKeyPressed(GLFW_KEY_UP)) {
            dY = 1;
        } else if (window.getKeyPressed(GLFW_KEY_DOWN)) {
            dY = -1;
        } else if (window.getKeyPressed(GLFW_KEY_LEFT)) {
            dX = -1;
        } else if (window.getKeyPressed(GLFW_KEY_RIGHT)) {
            dX = 1;
        } else if (window.getKeyPressed(GLFW_KEY_A)) {
            dZ = -1;
        } else if (window.getKeyPressed(GLFW_KEY_Q)) {
            dZ = 1;
        } else if (window.getKeyPressed(GLFW_KEY_Z)) {
            scale = -1;
        } else if (window.getKeyPressed(GLFW_KEY_X)) {
            scale = 1;
        }
    }

    override fun update(interval: Float) {
        for (i in objects) {
            // Update position
            val itemPos = i.getPosition()
            val posx: Float = itemPos.x + dX * 0.01f
            val posy: Float = itemPos.y + dY * 0.01f
            val posz: Float = itemPos.z + dZ * 0.01f
            i.setPosition(posx, posy, posz)

            // Update scale
            var scale = i.getScale()
            scale += scale * 0.05f
            if (scale < 0) {
                scale = 0f
            }
            i.setScale(scale)

            // Update rotation angle
            var rotation = i.getRotation().z + 1.5f
            if (rotation > 360) {
                rotation = 0f
            }
            i.setRotation(0f, 0f, rotation)
        }
    }

    override fun render(window: Window) {
        renderer.render(window, objects)
    }

    override fun cleanup() {
        renderer.cleanup()
        for (i in objects) {
            i.getMesh().cleanUp()
        }
    }

    init {
        renderer = Renderer()
    }
}
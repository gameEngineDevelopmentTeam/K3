package itoh.game

import itoh.engine.GameLogic
import itoh.engine.Window
import itoh.engine.polygon.Mesh
import itoh.engine.polygon.Obj3D
import org.joml.Vector3f
import org.lwjgl.glfw.GLFW.GLFW_KEY_A
import org.lwjgl.glfw.GLFW.GLFW_KEY_DOWN
import org.lwjgl.glfw.GLFW.GLFW_KEY_LEFT
import org.lwjgl.glfw.GLFW.GLFW_KEY_LEFT_SHIFT
import org.lwjgl.glfw.GLFW.GLFW_KEY_Q
import org.lwjgl.glfw.GLFW.GLFW_KEY_R
import org.lwjgl.glfw.GLFW.GLFW_KEY_RIGHT
import org.lwjgl.glfw.GLFW.GLFW_KEY_T
import org.lwjgl.glfw.GLFW.GLFW_KEY_U
import org.lwjgl.glfw.GLFW.GLFW_KEY_UP
import org.lwjgl.glfw.GLFW.GLFW_KEY_X
import org.lwjgl.glfw.GLFW.GLFW_KEY_Y
import org.lwjgl.glfw.GLFW.GLFW_KEY_Z


open class Game : GameLogic {
    private var dX: Int = 0
    private var dY: Int = 0
    private var dZ: Int = 0
    private var scale: Int = 0
    private var rX: Int = 0
    private var rY: Int = 0
    private var rZ: Int = 0
    private var r: Boolean = false
    private val renderer: Renderer
    private lateinit var objects: Array<Obj3D>

    override fun initialization(window: Window) {
        renderer.initialization(window)
        val positions = floatArrayOf(
                -0.5f, 0.5f, 0.5f,
                -0.5f, -0.5f, 0.5f,
                0.5f, -0.5f, 0.5f,
                0.5f, 0.5f, 0.5f,
                -0.5f, 0.5f, -0.5f,
                0.5f, 0.5f, -0.5f,
                -0.5f, -0.5f, -0.5f,
                0.5f, -0.5f, -0.5f
        )

        val colors = floatArrayOf(
                1.0f, 0.0f, 0.0f,
                0.0f, 1.0f, 0.0f,
                0.0f, 0.0f, 1.0f,
                1.0f, 1.0f, 0.0f,
                0.0f, 1.0f, 1.0f,
                1.0f, 0.0f, 0.0f,
                0.0f, 1.0f, 0.0f,
                0.0f, 0.0f, 1.0f
        )

        val indices = intArrayOf(
                0, 1, 3, 3, 1, 2,
                4, 0, 3, 5, 4, 3,
                3, 2, 7, 5, 3, 7,
                6, 1, 0, 6, 0, 4,
                2, 1, 6, 2, 6, 7,
                7, 6, 4, 7, 4, 5
        )
        val mesh = Mesh(positions, colors, indices)
        val obj = Obj3D(mesh)
        obj.setPosition(0f, 0f, -2f)
        objects = arrayOf(obj)
    }

    override fun input(window: Window) {
        dX = 0
        dY = 0
        dZ = 0
        scale = 0
        rX = 0
        rY = 0
        rZ = 0
        r = false
        when {
            window.getKeyPressed(GLFW_KEY_UP) -> dY = 1
            window.getKeyPressed(GLFW_KEY_DOWN) -> dY = -1
            window.getKeyPressed(GLFW_KEY_LEFT) -> dX = -1
            window.getKeyPressed(GLFW_KEY_RIGHT) -> dX = 1
            window.getKeyPressed(GLFW_KEY_A) -> dZ = -1
            window.getKeyPressed(GLFW_KEY_Q) -> dZ = 1
            window.getKeyPressed(GLFW_KEY_Z) -> scale = -1
            window.getKeyPressed(GLFW_KEY_X) -> scale = 1
            window.getKeyPressed(GLFW_KEY_LEFT_SHIFT)->{
                when{
                    window.getKeyPressed(GLFW_KEY_R) -> rX = -1
                    window.getKeyPressed(GLFW_KEY_T) -> rY = -1
                    window.getKeyPressed(GLFW_KEY_Y) -> rZ = -1
                }
            }
            window.getKeyPressed(GLFW_KEY_R) -> rX = 1
            window.getKeyPressed(GLFW_KEY_T) -> rY = 1
            window.getKeyPressed(GLFW_KEY_Y) -> rZ = 1
            window.getKeyPressed(GLFW_KEY_U) -> {
                r = true
            }
        }
    }

    override fun update(interval: Float) {
        for (i in objects) {
            // Update position
            val itemPos: Vector3f = i.getPosition()
            val posX: Float = itemPos.x + dX * 0.01f
            val posY: Float = itemPos.y + dY * 0.01f
            val posZ: Float = itemPos.z + dZ * 0.01f
            i.setPosition(posX, posY, posZ)

            // Update scale
            var scaleU = i.getScale()
            scaleU += scale * 0.05f
            if (scaleU < 0) {
                scaleU = 0f
            }
            i.setScale(scaleU)

            // Update rotation angle
            var rRotation = i.getRotation().x + rX
            if (rRotation > 360) {
                rRotation = 0f
            }

            var yRotation = i.getRotation().y + rY
            if (yRotation > 360) {
                yRotation = 0f
            }

            var zRotation = i.getRotation().z + rZ
            if (zRotation > 360) {
                zRotation = 0f
            }
            i.setRotation(rRotation, yRotation, zRotation)
            if (r) {
                i.setRotation(0f, 0f, 0f)
                r=false
            }
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
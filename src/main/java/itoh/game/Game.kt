package itoh.game

import itoh.engine.GameLogic
import itoh.engine.Window
import itoh.engine.polygon.Mesh
import itoh.engine.polygon.Obj3D
import itoh.engine.polygon.Texture
import org.joml.Vector3f
import org.lwjgl.glfw.GLFW.GLFW_KEY_A
import org.lwjgl.glfw.GLFW.GLFW_KEY_DOWN
import org.lwjgl.glfw.GLFW.GLFW_KEY_F
import org.lwjgl.glfw.GLFW.GLFW_KEY_G
import org.lwjgl.glfw.GLFW.GLFW_KEY_H
import org.lwjgl.glfw.GLFW.GLFW_KEY_LEFT
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
    private val renderer: Renderer = Renderer()
    private lateinit var objects: Array<Obj3D>

    override fun initialization(window: Window) {
        renderer.initialization(window)
        val positions = floatArrayOf(
                -0.5f, 0.5f, 0.5f, //1f
                -0.5f, -0.5f, 0.5f, //2f
                0.5f, -0.5f, 0.5f, //3f
                0.5f, 0.5f, 0.5f, //4f

                -0.5f, 0.5f, -0.5f, //5bc
                0.5f, 0.5f, -0.5f, //6bc
                -0.5f, -0.5f, -0.5f, //7bc
                0.5f, -0.5f, -0.5f, //8bc

                -0.5f, 0.5f, -0.5f, //5b
                0.5f, 0.5f, -0.5f, //6b
                -0.5f, 0.5f, 0.5f, //1b
                0.5f, 0.5f, 0.5f, //4b

                0.5f, 0.5f, 0.5f, //4
                0.5f, -0.5f, 0.5f, //3
                -0.5f, 0.5f, 0.5f, //1
                -0.5f, -0.5f, 0.5f,//2

                -0.5f, -0.5f, -0.5f,
                0.5f, -0.5f, -0.5f,
                -0.5f, -0.5f, 0.5f,
                0.5f, -0.5f, 0.5f
        )

        val texCoords = floatArrayOf(
                1.0f, 0.0f,
                1.0f, 1.0f,
                0.0f, 1.0f,
                0.0f, 0.0f,

                1.0f, 0.0f,
                1.0f, 1.0f,
                0.0f, 0.0f,
                0.0f, 1.0f,

                0.0f, 0.0f,
                1.0f, 1.0f,
                0.0f, 1.0f,
                1.0f, 0.0f,

                1.0f, 0.0f,
                1.0f, 1.0f,
                0.0f, 1.0f,
                0.0f, 0.0f,

                1.0f, 0.0f,
                1.0f, 1.0f,
                0.0f, 1.0f,
                0.0f, 0.0f,

                1.0f, 0.0f,
                1.0f, 1.0f,
                0.0f, 1.0f,
                0.0f, 0.0f,
        )

        val indices = intArrayOf(
                0, 1, 3, 3, 1, 2,
                8, 10, 11, 9, 8, 11,
                12, 13, 7, 5, 12, 7,
                14, 15, 6, 4, 14, 6,
                16, 18, 19, 17, 16, 19,
                4, 6, 7, 5, 4, 7,
        )
        val texture = Texture("src/main/resources/lennaBlock.png")
        val mesh = Mesh(positions, texCoords, indices, texture)
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
        if (window.getKeyPressed(GLFW_KEY_UP)) dY = 1
        if (window.getKeyPressed(GLFW_KEY_DOWN)) dY = -1
        if (window.getKeyPressed(GLFW_KEY_LEFT)) dX = -1
        if (window.getKeyPressed(GLFW_KEY_RIGHT)) dX = 1
        if (window.getKeyPressed(GLFW_KEY_A)) dZ = -1
        if (window.getKeyPressed(GLFW_KEY_Q)) dZ = 1
        if (window.getKeyPressed(GLFW_KEY_Z)) scale = -1
        if (window.getKeyPressed(GLFW_KEY_X)) scale = 1

        if (window.getKeyPressed(GLFW_KEY_F)) rX = -1
        if (window.getKeyPressed(GLFW_KEY_G)) rY = -1
        if (window.getKeyPressed(GLFW_KEY_H)) rZ = -1

        if (window.getKeyPressed(GLFW_KEY_R)) rX = 1
        if (window.getKeyPressed(GLFW_KEY_T)) rY = 1
        if (window.getKeyPressed(GLFW_KEY_Y)) rZ = 1
        if (window.getKeyPressed(GLFW_KEY_U)) r = true
    }

    override fun update(interval: Float) {
        for (i in objects) {
            val itemPos: Vector3f = i.getPosition()
            val posX: Float = itemPos.x + dX * 0.01f
            val posY: Float = itemPos.y + dY * 0.01f
            val posZ: Float = itemPos.z + dZ * 0.01f
            i.setPosition(posX, posY, posZ)

            var scaleU = i.getScale()
            scaleU += scale * 0.05f
            if (scaleU < 0) {
                scaleU = 0f
            }
            i.setScale(scaleU)

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
                i.setPosition(0f, 0f, -2f)
                i.setScale(1f)
                i.setRotation(0f, 0f, 0f)
                r = false
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

}


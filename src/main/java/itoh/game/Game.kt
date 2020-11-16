package itoh.game

import itoh.engine.GameLogic
import itoh.engine.MouseInput
import itoh.engine.Window
import itoh.engine.polygon.Camera
import itoh.engine.polygon.Mesh
import itoh.engine.polygon.Obj3D
import itoh.engine.polygon.Texture
import org.joml.Vector3f
import org.lwjgl.glfw.GLFW.GLFW_KEY_A
import org.lwjgl.glfw.GLFW.GLFW_KEY_D
import org.lwjgl.glfw.GLFW.GLFW_KEY_S
import org.lwjgl.glfw.GLFW.GLFW_KEY_W
import org.lwjgl.glfw.GLFW.GLFW_KEY_X
import org.lwjgl.glfw.GLFW.GLFW_KEY_Z


open class Game : GameLogic {
    companion object {
        private val sensitivity = 0.2f
        private val cameraPosStep = 0.05f
    }

    private var rX: Int = 0
    private var rY: Int = 0
    private var rZ: Int = 0
    private var r: Boolean = false
    private val renderer: Renderer = Renderer()
    private lateinit var objects: Array<Obj3D>
    private val cameraInc: Vector3f = Vector3f()
    private val camera: Camera = Camera()


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
                0.5f, -0.5f, -0.5f,

                -0.5f, 0.5f, -0.5f,
                0.5f, 0.5f, -0.5f,
                -0.5f, 0.5f, 0.5f,
                0.5f, 0.5f, 0.5f,

                0.5f, 0.5f, 0.5f,
                0.5f, -0.5f, 0.5f,
                -0.5f, 0.5f, 0.5f,
                -0.5f, -0.5f, 0.5f,

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
                0.0f, 0.0f
        )

        val indices = intArrayOf(
                0, 1, 3, 3, 1, 2,
                8, 10, 11, 9, 8, 11,
                12, 13, 7, 5, 12, 7,
                14, 15, 6, 4, 14, 6,
                16, 18, 19, 17, 16, 19,
                4, 6, 7, 5, 4, 7
        )
        val texture = Texture("src/main/resources/lennaBlock.png")
        val mesh = Mesh(positions, texCoords, indices, texture)

        val objects1: Obj3D = Obj3D(mesh)
        objects1.setScale(.5f)
        objects1.setPosition(0f, 0f, -2f)

        val objects2: Obj3D = Obj3D(mesh)
        objects2.setScale(.5f)
        objects2.setPosition(.5f, .5f, -2f)

        val objects3: Obj3D = Obj3D(mesh)
        objects3.setScale(.5f)
        objects3.setPosition(0f, 0f, -2.5f)

        val objects4: Obj3D = Obj3D(mesh)
        objects4.setScale(.5f)
        objects4.setPosition(.5f, 0f, -2.5f)

        objects = arrayOf(objects1, objects2, objects3, objects4)
    }

    override fun input(window: Window, mouseInput: MouseInput) {
        cameraInc.set(0f, 0f, 0f)
        if (window.getKeyPressed(GLFW_KEY_W)) {
            cameraInc.z = -1f
        } else if (window.getKeyPressed(GLFW_KEY_S)) {
            cameraInc.z = 1f
        }
        if (window.getKeyPressed(GLFW_KEY_A)) {
            cameraInc.x = -1f
        } else if (window.getKeyPressed(GLFW_KEY_D)) {
            cameraInc.x = 1f
        }
        if (window.getKeyPressed(GLFW_KEY_Z)) {
            cameraInc.y = -1f
        } else if (window.getKeyPressed(GLFW_KEY_X)) {
            cameraInc.y = 1f
        }
    }

    override fun update(interval: Float, mouseInput: MouseInput) {
        camera.movePosition(
                cameraInc.x * cameraPosStep,
                cameraInc.y * cameraPosStep,
                cameraInc.z * cameraPosStep
        )
        if (mouseInput.getRightButtonPressed()) {
            val rotationV = mouseInput.getDisplayVec()
            camera.movePosition(
                    rotationV.x * sensitivity,
                    rotationV.y * sensitivity,
                    0f
            )
        }
    }

    override fun render(window: Window) {
        renderer.render(window, camera, objects)
    }

    override fun cleanup() {
        renderer.cleanup()
        for (i in objects) {
            i.getMesh().cleanUp()
        }
    }
}


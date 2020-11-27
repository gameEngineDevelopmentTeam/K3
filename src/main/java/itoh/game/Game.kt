package itoh.game

import itoh.engine.GameLogic
import itoh.engine.MouseInput
import itoh.engine.Window
import itoh.engine.polygon.*
import org.joml.Vector3f
import org.lwjgl.glfw.GLFW.*


open class Game : GameLogic {
    companion object {
        private const val sensitivity = 0.01f
        private const val cameraPosStep = 0.05f
    }

    private val renderer: Renderer = Renderer()
    private lateinit var objects: Array<Obj3D>
    private val cameraInc: Vector3f = Vector3f()
    private val cameraRo:Vector3f = Vector3f()
    private val camera: Camera = Camera()
    private val  objLoader:ObjLoader = ObjLoader()


    override fun initialization(window: Window) {
        renderer.initialization()
        val mesh = objLoader.objLoader("cube.obj")
        val texture = Texture("src/main/resources/lenna.png")
        mesh.texture = texture
        val gameItem = Obj3D(mesh)
        gameItem.scale = 0.5f
        gameItem.position = Vector3f(0f, 0f, -2f)
        objects = arrayOf(gameItem)
    }

    override fun input(window: Window, mouseInput: MouseInput) {
        cameraInc.set(0f, 0f, 0f)
        cameraRo.set(0f,0f,0f)
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
        if(window.getKeyPressed(GLFW_KEY_R)) cameraRo.x = 1f
        if(window.getKeyPressed(GLFW_KEY_T)) cameraRo.y = 1f
        if(window.getKeyPressed(GLFW_KEY_Y)) cameraRo.z = 1f
        if(window.getKeyPressed(GLFW_KEY_P)) {
            cameraInc.x = -camera.position.x
            cameraInc.y = -camera.position.y
            cameraInc.z = -camera.position.z
        }
    }

    override fun update(interval: Float, mouseInput: MouseInput) {
        camera.movePosition(
                cameraInc.x * cameraPosStep,
                cameraInc.y * cameraPosStep,
                cameraInc.z * cameraPosStep
        )
        camera.moveRotation(
                cameraRo.x,
                cameraRo.y,
                cameraRo.z
        )
        if (mouseInput.rightButton) {
            val rotationV = mouseInput.displayVec
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
            i.mesh.cleanUp()
        }

    }
}


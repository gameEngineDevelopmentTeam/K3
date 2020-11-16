package itoh.engine

import org.joml.Vector2d
import org.joml.Vector2f
import org.lwjgl.glfw.GLFW.GLFW_MOUSE_BUTTON_1
import org.lwjgl.glfw.GLFW.GLFW_MOUSE_BUTTON_2
import org.lwjgl.glfw.GLFW.GLFW_PRESS
import org.lwjgl.glfw.GLFW.glfwSetCursorEnterCallback
import org.lwjgl.glfw.GLFW.glfwSetCursorPosCallback
import org.lwjgl.glfw.GLFW.glfwSetMouseButtonCallback


class MouseInput {
    private val previousPos: Vector2d = Vector2d(-1.0, -1.0)
    private val currentPos: Vector2d = Vector2d(0.0, 0.0)
    private val displayVec: Vector2f = Vector2f()
    private var inWindow: Boolean = false
    private var leftButtonPressed = false
    private var rightButtonPressed = false

    fun initialization(window: Window) {
        glfwSetCursorPosCallback(window.getWindowHandle()) { _: Long, xPos: Double, yPos: Double ->
            currentPos.x = xPos
            currentPos.y = yPos
        }

        glfwSetCursorEnterCallback(window.getWindowHandle()) { _: Long, entered: Boolean ->
            inWindow = entered
        }

        glfwSetMouseButtonCallback(window.getWindowHandle()) { _: Long, button: Int, action: Int, _: Int ->
            leftButtonPressed = button == GLFW_MOUSE_BUTTON_1 && action == GLFW_PRESS
            rightButtonPressed = button == GLFW_MOUSE_BUTTON_2 && action == GLFW_PRESS
        }
    }

    fun input(window: Window) {
        displayVec.x = 0f
        displayVec.y = 0f
        if (previousPos.x > 0f && previousPos.y > 0f && inWindow) {
            val dX = currentPos.x - previousPos.x
            val dY = currentPos.y - previousPos.y
            val rotateX = dX != 0.0
            val rotateY = dY != 0.0
            if (rotateX) displayVec.y = dX.toFloat()
            if (rotateY) displayVec.x = dY.toFloat()
        }
        previousPos.x = currentPos.x
        previousPos.y = currentPos.y
    }

    fun getLeftButtonPressed(): Boolean {
        return leftButtonPressed
    }

    fun getRightButtonPressed(): Boolean {
        return rightButtonPressed
    }

    fun getDisplayVec(): Vector2f {
        return displayVec
    }
}
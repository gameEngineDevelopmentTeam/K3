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
    private var inWindow: Boolean = false

    private val _displayVec: Vector2f = Vector2f()
    val displayVec: Vector2f
        get() = _displayVec

    private var _leftButton = false
    val leftButton: Boolean
        get() = _leftButton

    private var _rightButton = false
    val rightButton: Boolean
        get() = _rightButton

    fun initialization(window: Window) {
        glfwSetCursorPosCallback(window.windowHandle) { _: Long, xPos: Double, yPos: Double ->
            currentPos.x = xPos
            currentPos.y = yPos
        }

        glfwSetCursorEnterCallback(window.windowHandle) { _: Long, entered: Boolean ->
            inWindow = entered
        }

        glfwSetMouseButtonCallback(window.windowHandle) { _: Long, button: Int, action: Int, _: Int ->
            _leftButton = button == GLFW_MOUSE_BUTTON_1 && action == GLFW_PRESS
            _rightButton = button == GLFW_MOUSE_BUTTON_2 && action == GLFW_PRESS
        }
    }

    fun input(window: Window) {
        _displayVec.x = 0f
        _displayVec.y = 0f
        if (previousPos.x > 0f && previousPos.y > 0f && inWindow) {
            val dX = currentPos.x - previousPos.x
            val dY = currentPos.y - previousPos.y
            val rotateX = dX != 0.0
            val rotateY = dY != 0.0
            if (rotateX) _displayVec.y = dX.toFloat()
            if (rotateY) _displayVec.x = dY.toFloat()
        }
        previousPos.x = currentPos.x
        previousPos.y = currentPos.y
    }
}
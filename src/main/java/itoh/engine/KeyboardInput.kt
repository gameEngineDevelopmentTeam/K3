package itoh.engine

import org.lwjgl.glfw.GLFW

open class KeyboardInput constructor(private val window: Window){
    public fun getKeyPressed(keyCode: Int): Boolean {
        return if (GLFW.glfwGetKey(window.getWindowHandle(), keyCode) == GLFW.GLFW_PRESS) {
            println("pressed : ${GLFW.glfwGetKeyScancode(keyCode)}")
            true
        } else {
            false
        }
    }
}
package itoh.engine

import org.lwjgl.glfw.GLFW
import org.lwjgl.glfw.GLFW.GLFW_CONTEXT_VERSION_MAJOR
import org.lwjgl.glfw.GLFW.GLFW_CONTEXT_VERSION_MINOR
import org.lwjgl.glfw.GLFW.GLFW_KEY_ESCAPE
import org.lwjgl.glfw.GLFW.GLFW_OPENGL_CORE_PROFILE
import org.lwjgl.glfw.GLFW.GLFW_OPENGL_FORWARD_COMPAT
import org.lwjgl.glfw.GLFW.GLFW_OPENGL_PROFILE
import org.lwjgl.glfw.GLFW.GLFW_PRESS
import org.lwjgl.glfw.GLFW.GLFW_RELEASE
import org.lwjgl.glfw.GLFW.GLFW_RESIZABLE
import org.lwjgl.glfw.GLFW.GLFW_VISIBLE
import org.lwjgl.glfw.GLFW.glfwCreateWindow
import org.lwjgl.glfw.GLFW.glfwDefaultWindowHints
import org.lwjgl.glfw.GLFW.glfwGetKey
import org.lwjgl.glfw.GLFW.glfwGetPrimaryMonitor
import org.lwjgl.glfw.GLFW.glfwGetVideoMode
import org.lwjgl.glfw.GLFW.glfwInit
import org.lwjgl.glfw.GLFW.glfwMakeContextCurrent
import org.lwjgl.glfw.GLFW.glfwPollEvents
import org.lwjgl.glfw.GLFW.glfwSetKeyCallback
import org.lwjgl.glfw.GLFW.glfwSetWindowPos
import org.lwjgl.glfw.GLFW.glfwSetWindowShouldClose
import org.lwjgl.glfw.GLFW.glfwShowWindow
import org.lwjgl.glfw.GLFW.glfwSwapBuffers
import org.lwjgl.glfw.GLFW.glfwSwapInterval
import org.lwjgl.glfw.GLFW.glfwWindowHint
import org.lwjgl.glfw.GLFW.glfwWindowShouldClose
import org.lwjgl.glfw.GLFWErrorCallback
import org.lwjgl.glfw.GLFWVidMode
import org.lwjgl.opengl.GL
import org.lwjgl.opengl.GL11.GL_FALSE
import org.lwjgl.opengl.GL11.GL_TRUE
import org.lwjgl.opengl.GL11.glClearColor
import org.lwjgl.system.MemoryUtil.NULL


class Window(
        title: String,
        width: Int,
        height: Int,
        windowHandle: Long,
        resized: Boolean,
        vSync: Boolean) {
    private val title: String
    private var width: Int
    private var height: Int
    private var windowHandle: Long
    private var resized: Boolean
    private var vSync: Boolean

    init {
        this.title = title
        this.width = width
        this.height = height
        this.windowHandle = windowHandle
        this.resized = resized
        this.vSync = vSync
    }

    public fun initialization(): Unit {
        GLFWErrorCallback.createPrint(System.err).set()
        if (!glfwInit())
            throw IllegalStateException("GLFWの初期化に失敗しました.")
        glfwDefaultWindowHints()
        glfwWindowHint(GLFW_VISIBLE, GL_FALSE)
        glfwWindowHint(GLFW_RESIZABLE, GL_TRUE)
        glfwWindowHint(GLFW_CONTEXT_VERSION_MAJOR, 3)
        glfwWindowHint(GLFW_CONTEXT_VERSION_MINOR, 2)
        glfwWindowHint(GLFW_OPENGL_PROFILE, GLFW_OPENGL_CORE_PROFILE)
        glfwWindowHint(GLFW_OPENGL_FORWARD_COMPAT, GL_TRUE)
        windowHandle = glfwCreateWindow(width, height, title, NULL, NULL)
        if (windowHandle == NULL) {
            throw RuntimeException("ウィンドウの作成に失敗しました.")
        }

        GLFW.glfwSetFramebufferSizeCallback(windowHandle) { window: Long, width: Int, height: Int ->
            this.width = width
            this.height = height
            this.setResized(true)
        }

        glfwSetKeyCallback(windowHandle) { window: Long, key: Int, scancode: Int, action: Int, mods: Int ->
            if (key == GLFW_KEY_ESCAPE && action == GLFW_RELEASE) {
                glfwSetWindowShouldClose(window, true)
            }
        }

        var vidMode: GLFWVidMode = glfwGetVideoMode(glfwGetPrimaryMonitor())
                ?: throw RuntimeException("err")

        glfwSetWindowPos(
                windowHandle,
                (vidMode.width() - width) / 2,
                (vidMode.height() - height) / 2
        )

        glfwMakeContextCurrent(windowHandle)

        if (isvSync()) {
            glfwSwapInterval(1)
        }

        glfwShowWindow(windowHandle)

        GL.createCapabilities()

        glClearColor(.0f, .0f, .0f, .0f)
    }

    fun setClearColor(r: Float, g: Float, b: Float, a: Float): Unit {
        glClearColor(r, g, b, a)
    }

    fun isKeyPressed(keyCode: Int): Boolean {
        return glfwGetKey(windowHandle, keyCode) == GLFW_PRESS
    }

    fun windowShouldClose(): Boolean {
        return glfwWindowShouldClose(windowHandle)
    }

    fun isResized(): Boolean {
        return resized
    }

    private fun setResized(resized: Boolean): Unit {
        this.resized = resized
    }

    fun isvSync(): Boolean {
        return vSync
    }

    fun update(): Unit {
        glfwSwapBuffers(windowHandle)
        glfwPollEvents()
    }
}
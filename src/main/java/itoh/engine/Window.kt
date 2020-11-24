package itoh.engine

import org.lwjgl.Version.getVersion
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
import org.lwjgl.glfw.GLFW.glfwDestroyWindow
import org.lwjgl.glfw.GLFW.glfwGetKey
import org.lwjgl.glfw.GLFW.glfwGetPrimaryMonitor
import org.lwjgl.glfw.GLFW.glfwGetVideoMode
import org.lwjgl.glfw.GLFW.glfwInit
import org.lwjgl.glfw.GLFW.glfwMakeContextCurrent
import org.lwjgl.glfw.GLFW.glfwPollEvents
import org.lwjgl.glfw.GLFW.glfwSetFramebufferSizeCallback
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
import org.lwjgl.opengl.GL.createCapabilities
import org.lwjgl.opengl.GL11.GL_DEPTH_TEST
import org.lwjgl.opengl.GL11.GL_FALSE
import org.lwjgl.opengl.GL11.GL_RENDERER
import org.lwjgl.opengl.GL11.GL_TRUE
import org.lwjgl.opengl.GL11.GL_VENDOR
import org.lwjgl.opengl.GL11.GL_VERSION
import org.lwjgl.opengl.GL11.glClearColor
import org.lwjgl.opengl.GL11.glEnable
import org.lwjgl.opengl.GL11.glGetString
import org.lwjgl.opengl.GL20.GL_SHADING_LANGUAGE_VERSION
import org.lwjgl.system.MemoryUtil.NULL


class Window(private val title: String, width: Int, height: Int, vSync: Boolean) {
    private var _windowHandle: Long = 0L
    val windowHandle: Long
        get() = _windowHandle

    private var _resized: Boolean = false
    var resized: Boolean
        get() = _resized
        set(value) {
            _resized = value
        }

    private var _width: Int
    val width: Int
        get() = _width

    private var _height: Int
    val height: Int
        get() = _height

    private var _vSync: Boolean
    val vSync: Boolean
        get() = _vSync

    init {
        _width = width
        _height = height
        _vSync = vSync
    }

    private val k3EngineVersion: String = "0.2.1 build 39"

    internal fun initialization() {
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

        _windowHandle = glfwCreateWindow(width, height, title, NULL, NULL)
        //windowHandle = glfwCreateWindow(2560, 1080, title, glfwGetPrimaryMonitor(), NULL)
        if (_windowHandle == NULL || _windowHandle == 0L) {
            throw RuntimeException("ウィンドウの作成に失敗しました.")
        }

        glfwSetFramebufferSizeCallback(_windowHandle) { _: Long, width: Int, height: Int ->
            _width = width
            _height = height
            this.resized = true
        }

        glfwSetKeyCallback(_windowHandle) { window: Long, key: Int, _: Int, action: Int, _: Int ->
            if (key == GLFW_KEY_ESCAPE && action == GLFW_RELEASE) {
                glfwSetWindowShouldClose(window, true)
            }
        }

        val vidMode: GLFWVidMode = glfwGetVideoMode(glfwGetPrimaryMonitor())
                ?: throw RuntimeException("err")

        glfwSetWindowPos(
                _windowHandle,
                (vidMode.width() - width) / 2,
                (vidMode.height() - height) / 2
        )

        glfwMakeContextCurrent(_windowHandle)

        if (_vSync) {
            glfwSwapInterval(1)
        }

        glfwShowWindow(_windowHandle)

        createCapabilities()

        versionPrint()

        glClearColor(.3f, .3f, .3f, .3f)
        glEnable(GL_DEPTH_TEST)
    }

    private fun versionPrint() {
        println("-------------------------------------------------")
        println("Engine Version  : %-32s ".format(k3EngineVersion))
        println("Kotlin Version  : %-32s".format(KotlinVersion.CURRENT))
        println("JVM Version     : %-32s".format(System.getProperty("java.version")))
        println("LWJGL Version   : %-32s".format(getVersion()))
        println("OpenGL Version  : %-32s".format(glGetString(GL_VERSION)))
        println("GLSL Version    : %-32s".format(glGetString(GL_SHADING_LANGUAGE_VERSION)))
        println("OpenGL Renderer : %-32s".format(glGetString(GL_RENDERER)))
        println("OpenGL Vendor   : %-32s".format(glGetString(GL_VENDOR)))
        println("-------------------------------------------------")
    }

    internal fun update() {
        glfwSwapBuffers(_windowHandle)
        glfwPollEvents()
    }

    fun getKeyPressed(keyCode: Int): Boolean {
        return glfwGetKey(_windowHandle, keyCode) == GLFW_PRESS
    }

    internal fun getWindowShouldClose(): Boolean {
        return glfwWindowShouldClose(_windowHandle)
    }

    fun setClearColor(color: ClearColor) {
        glClearColor(color.r, color.g, color.b, color.a)
    }

    internal fun cleanup() {
        glfwDestroyWindow(_windowHandle)
    }
}
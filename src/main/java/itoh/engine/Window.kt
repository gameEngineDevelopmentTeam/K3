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
import org.lwjgl.glfw.GLFW.glfwGetKey
import org.lwjgl.glfw.GLFW.glfwGetKeyScancode
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
import org.lwjgl.opengl.GL11.GL_FALSE
import org.lwjgl.opengl.GL11.GL_RENDERER
import org.lwjgl.opengl.GL11.GL_TRUE
import org.lwjgl.opengl.GL11.GL_VENDOR
import org.lwjgl.opengl.GL11.GL_VERSION
import org.lwjgl.opengl.GL11.glClearColor
import org.lwjgl.opengl.GL11.glGetString
import org.lwjgl.opengl.GL20.GL_SHADING_LANGUAGE_VERSION
import org.lwjgl.system.MemoryUtil.NULL


class Window(private val title: String, private var width: Int, private var height: Int, private var vSync: Boolean) {
    private var windowHandle: Long = 0L
    private var resized: Boolean = false
    private val k3EngineVersion: String = "0.1.9 build 33"

    internal fun initialization() {
        GLFWErrorCallback.createPrint(System.err).set()

        if (!glfwInit())
            throw IllegalStateException("GLFWの初期化に失敗しました.")

        glfwDefaultWindowHints()
        glfwWindowHint(GLFW_VISIBLE, GL_FALSE)
        glfwWindowHint(GLFW_RESIZABLE, GL_TRUE)
        glfwWindowHint(GLFW_CONTEXT_VERSION_MAJOR, 4)
        glfwWindowHint(GLFW_CONTEXT_VERSION_MINOR, 6)
        glfwWindowHint(GLFW_OPENGL_PROFILE, GLFW_OPENGL_CORE_PROFILE)
        glfwWindowHint(GLFW_OPENGL_FORWARD_COMPAT, GL_TRUE)

        windowHandle = glfwCreateWindow(width, height, title, NULL, NULL)
        if (windowHandle == NULL || windowHandle == 0L) {
            throw RuntimeException("ウィンドウの作成に失敗しました.")
        }

        glfwSetFramebufferSizeCallback(windowHandle) { _: Long, width: Int, height: Int ->
            this.width = width
            this.height = height
            this.setResized(true)
        }

        glfwSetKeyCallback(windowHandle) { window: Long, key: Int, _: Int, action: Int, _: Int ->
            if (key == GLFW_KEY_ESCAPE && action == GLFW_RELEASE) {
                glfwSetWindowShouldClose(window, true)
            }
        }

        val vidMode: GLFWVidMode = glfwGetVideoMode(glfwGetPrimaryMonitor())
                ?: throw RuntimeException("err")

        glfwSetWindowPos(
                windowHandle,
                (vidMode.width() - width) / 2,
                (vidMode.height() - height) / 2
        )

        glfwMakeContextCurrent(windowHandle)

        if (getVSync()) {
            glfwSwapInterval(1)
        }

        glfwShowWindow(windowHandle)

        createCapabilities()

        versionPrint()

        glClearColor(.0f, .0f, .0f, .0f)
    }

    private fun versionPrint(): Unit {
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

    fun update() {
        glfwSwapBuffers(windowHandle)
        glfwPollEvents()
    }

    fun setClearColor(r: Float, g: Float, b: Float, a: Float) {
        glClearColor(r, g, b, a)
    }

    fun setResized(resized: Boolean) {
        this.resized = resized
    }

    fun getKeyPressed(keyCode: Int): Boolean {
        return if (glfwGetKey(windowHandle, keyCode) == GLFW_PRESS) {
            println("pressed : ${glfwGetKeyScancode(keyCode)}")
            true
        } else {
            false
        }
    }

    internal fun getWindowHandle():Long{
        return windowHandle
    }


    fun getWindowShouldClose(): Boolean {
        return glfwWindowShouldClose(windowHandle)
    }

    fun getResized(): Boolean {
        return resized
    }

    fun getWidth(): Int {
        return width
    }

    fun getHeight(): Int {
        return height
    }

    fun getVSync(): Boolean {
        return vSync
    }
}
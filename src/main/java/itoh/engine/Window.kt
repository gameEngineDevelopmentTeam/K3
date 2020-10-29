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
import kotlin.system.exitProcess


class Window(private val title: String, private var width: Int, private var height: Int, private var vSync: Boolean) {
    private var windowHandle: Long = glfwCreateWindow(width, height, title, NULL, NULL)
    /* width -> ウィンドウの幅
     * height -> ウィンドウの高さ
     * title -> ウィンドウタイトル
     * monitor -> !フルスクリーン -> NULL
     * share -> !別ウィンドウとリソースを共有 -> NULL
     * ウィンドウ作成に失敗した場合NULLが返る
    */

    private var resized: Boolean = false // ウィンドウリサイズフラグ　現状未使用

    fun initialization() {
        if (!glfwInit()){  // GLFWの初期化を行う すべてのglfw関数を呼ぶ前に呼ぶ
            System.err.println("GLFWの初期化に失敗しました.")
            exitProcess(1)
        }

        glfwDefaultWindowHints()  // ウィンドウヒントをデフォルト値に設定する
        glfwWindowHint(GLFW_VISIBLE, GL_FALSE)
        glfwWindowHint(GLFW_RESIZABLE, GL_TRUE)
        glfwWindowHint(GLFW_CONTEXT_VERSION_MAJOR, 3)
        glfwWindowHint(GLFW_CONTEXT_VERSION_MINOR, 2)
        glfwWindowHint(GLFW_OPENGL_PROFILE, GLFW_OPENGL_CORE_PROFILE)
        glfwWindowHint(GLFW_OPENGL_FORWARD_COMPAT, GL_TRUE)

        if (windowHandle == NULL) {
            System.err.println("ウィンドウの作成に失敗しました.")
            exitProcess(1)
        }

        /*ウィンドウリサイズ*/
        GLFW.glfwSetFramebufferSizeCallback(windowHandle) { _: Long, width: Int, height: Int ->
            this.width = width
            this.height = height
            this.setResized(true)
        }

        /*ウィンドウクローズ*/
        glfwSetKeyCallback(windowHandle) { window: Long, key: Int, _: Int, action: Int, _: Int ->
            if (key == GLFW_KEY_ESCAPE && action == GLFW_RELEASE) {
                glfwSetWindowShouldClose(window, true)
            }
        }

        /*VidMode*/
        val vidMode: GLFWVidMode = glfwGetVideoMode(glfwGetPrimaryMonitor())
                ?: throw RuntimeException("err")

        glfwSetWindowPos(
                windowHandle,
                (vidMode.width() - width) / 2,
                (vidMode.height() - height) / 2
        )

        glfwMakeContextCurrent(windowHandle)  //引数に取ったウィンドウを処理対象にする

        if (isvSync()) {
            glfwSwapInterval(1)
        }

        glfwShowWindow(windowHandle)  // ウィンドウを表示する

        GL.createCapabilities()  // OpenGLの関数呼び出しの前に呼ぶ

        glClearColor(.0f, .0f, .0f, .0f)  // ウィンドウ背景色
    }

    fun setClearColor(r: Float, g: Float, b: Float, a: Float) {
        glClearColor(r, g, b, a)
    }

    fun isKeyPressed(keyCode: Int): Boolean {
        return glfwGetKey(windowHandle, keyCode) == GLFW_PRESS
    }

    fun windowShouldClose(): Boolean? {
        return glfwWindowShouldClose(windowHandle)
    }

    fun isResized(): Boolean {
        return resized
    }

    fun getWidth(): Int {
        return width
    }

    fun getHeight(): Int {
        return height
    }

    fun setResized(resized: Boolean) {
        this.resized = resized
    }

    fun isvSync(): Boolean {
        return vSync
    }

    fun update() {
        glfwSwapBuffers(windowHandle)
        glfwPollEvents()
    }
}
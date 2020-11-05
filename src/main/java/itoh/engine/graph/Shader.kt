package itoh.engine.graph

import org.lwjgl.opengl.GL20.GL_COMPILE_STATUS
import org.lwjgl.opengl.GL20.GL_FRAGMENT_SHADER
import org.lwjgl.opengl.GL20.GL_LINK_STATUS
import org.lwjgl.opengl.GL20.GL_VALIDATE_STATUS
import org.lwjgl.opengl.GL20.GL_VERTEX_SHADER
import org.lwjgl.opengl.GL20.glAttachShader
import org.lwjgl.opengl.GL20.glCompileShader
import org.lwjgl.opengl.GL20.glCreateProgram
import org.lwjgl.opengl.GL20.glCreateShader
import org.lwjgl.opengl.GL20.glDeleteProgram
import org.lwjgl.opengl.GL20.glDetachShader
import org.lwjgl.opengl.GL20.glGetProgramInfoLog
import org.lwjgl.opengl.GL20.glGetProgrami
import org.lwjgl.opengl.GL20.glGetShaderInfoLog
import org.lwjgl.opengl.GL20.glGetShaderi
import org.lwjgl.opengl.GL20.glLinkProgram
import org.lwjgl.opengl.GL20.glShaderSource
import org.lwjgl.opengl.GL20.glUseProgram
import org.lwjgl.opengl.GL20.glValidateProgram

class Shader {
    private var programId: Int
    private var vertexShaderId: Int
    private var fragmentShaderId: Int

    init {
        programId = 0
        vertexShaderId = 0
        fragmentShaderId = 0
    }

    private fun createShader(shaderCode: String, shaderType: Int): Int {
        programId = glCreateProgram()  // お前いい加減にしろよ!!!
        if (programId == 0) {
            throw Exception("シェーダプログラムの作成に失敗.")
        }

        val shaderId: Int = glCreateShader(shaderType)
        if (shaderId == 0) {
            throw Exception("シェーダの作成に失敗 Type: $shaderType")
        }

        glShaderSource(shaderId, shaderCode)
        glCompileShader(shaderId)
        if (glGetShaderi(shaderId, GL_COMPILE_STATUS) == 0) {
            throw Exception("シェーダのコンパイルに失敗 エラー: " + glGetShaderInfoLog(shaderId, 1024))
        }
        glAttachShader(programId, shaderId)
        return shaderId
    }

    public fun createVertexShader(shaderCode: String): Unit {
        vertexShaderId = createShader(shaderCode, GL_VERTEX_SHADER)
    }

    public fun createFragmentShader(shaderCode: String): Unit {
        fragmentShaderId = createShader(shaderCode, GL_FRAGMENT_SHADER)
    }

    public fun link(): Unit {
        glLinkProgram(programId)
        if (glGetProgrami(programId, GL_LINK_STATUS) == 0)
            throw java.lang.Exception("Error linking Shader code: " + glGetProgramInfoLog(programId, 1024))
        if (vertexShaderId != 0) glDetachShader(programId, vertexShaderId)
        if (fragmentShaderId != 0) glDetachShader(programId, fragmentShaderId)
        glValidateProgram(programId)
        if (glGetProgrami(programId, GL_VALIDATE_STATUS) == 0)
            println("Warning validating Shader code: " + glGetProgramInfoLog(programId, 1024))
    }

    public fun bind(): Unit = glUseProgram(programId)

    public fun unbind(): Unit = glUseProgram(0)

    public fun cleanup(): Unit {
        unbind()
        if (programId != 0) glDeleteProgram(programId)
    }
}
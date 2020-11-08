package itoh.engine.graph

import org.joml.Matrix4f
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
import org.lwjgl.opengl.GL20.glGetUniformLocation
import org.lwjgl.opengl.GL20.glLinkProgram
import org.lwjgl.opengl.GL20.glShaderSource
import org.lwjgl.opengl.GL20.glUniformMatrix4fv
import org.lwjgl.opengl.GL20.glUseProgram
import org.lwjgl.opengl.GL20.glValidateProgram
import org.lwjgl.system.MemoryStack


open class Shader {
    private var programId: Int = glCreateProgram()
    private var vertexShaderId: Int = 0
    private var fragmentShaderId: Int = 0
    private var uniforms: Map<String, Int> = mutableMapOf<String, Int>()

    init {
        if (programId == 0) {
            throw Exception("シェーダの作成に失敗")
        }
    }

    open fun createUniform(uniformName: String) {
        val uniformLocation: Int = glGetUniformLocation(programId, uniformName)
        if (uniformLocation < 0) {
            throw java.lang.Exception("Uniformの発見に失敗:$uniformName")
        }
        uniforms.getOrDefault(uniformName, uniformLocation)
    }

    open fun setUniform(uniformName: String?, value: Matrix4f) {
        MemoryStack.stackPush().use { stack ->
            uniforms[uniformName]?.let {
                glUniformMatrix4fv(it, false, value[stack.mallocFloat(16)])
            }
        }
    }

    fun createVertexShader(shaderCode: String) {
        vertexShaderId = createShader(shaderCode, GL_VERTEX_SHADER)
    }

    fun createFragmentShader(shaderCode: String) {
        fragmentShaderId = createShader(shaderCode, GL_FRAGMENT_SHADER)
    }

    private fun createShader(shaderCode: String, shaderType: Int): Int {
        val shaderId: Int = glCreateShader(shaderType)
        if (shaderId == 0) {
            throw java.lang.Exception("シェーダ作成に失敗 Type: $shaderType")
        }
        glShaderSource(shaderId, shaderCode)
        glCompileShader(shaderId)
        if (glGetShaderi(shaderId, GL_COMPILE_STATUS) == 0) {
            throw java.lang.Exception("シェーダのコンパイルに失敗: " + glGetShaderInfoLog(shaderId, 1024))
        }
        glAttachShader(programId, shaderId)
        return shaderId
    }

    open fun link() {
        glLinkProgram(programId)
        if (glGetProgrami(programId, GL_LINK_STATUS) == 0) {
            throw java.lang.Exception("シェーダのリンクに失敗: " + glGetProgramInfoLog(programId, 1024))
        }
        if (vertexShaderId != 0) {
            glDetachShader(programId, vertexShaderId)
        }
        if (fragmentShaderId != 0) {
            glDetachShader(programId, fragmentShaderId)
        }
        glValidateProgram(programId)
        if (glGetProgrami(programId, GL_VALIDATE_STATUS) == 0) {
            println(glGetProgramInfoLog(programId, 1024))
        }
    }

    open fun bind() {
        glUseProgram(programId)
    }

    open fun unbind() {
        glUseProgram(0)
    }

    open fun cleanup() {
        unbind()
        if (programId != 0) {
            glDeleteProgram(programId)
        }
    }

}
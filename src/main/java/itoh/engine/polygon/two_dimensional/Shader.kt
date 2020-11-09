package itoh.engine.polygon.two_dimensional

import org.joml.Matrix4f
import org.lwjgl.opengl.GL20
import org.lwjgl.system.MemoryStack

class Shader {
    private val programId: Int
    private var vertexShaderId = 0
    private var fragmentShaderId = 0
    private val uniforms: MutableMap<String, Int>
    fun createUniform(uniformName: String) {
        val uniformLocation = GL20.glGetUniformLocation(programId, uniformName)
        if (uniformLocation < 0) {
            throw Exception("uniformが見つかりません:$uniformName")
        }
        uniforms[uniformName] = uniformLocation
    }

    fun setUniform(uniformName: String, value: Matrix4f) {
        MemoryStack.stackPush().use { stack ->
            GL20.glUniformMatrix4fv(uniforms[uniformName]!!, false,
                    value[stack.mallocFloat(16)])
        }
    }

    fun createVertexShader(shaderCode: String) {
        vertexShaderId = createShader(shaderCode, GL20.GL_VERTEX_SHADER)
    }

    fun createFragmentShader(shaderCode: String) {
        fragmentShaderId = createShader(shaderCode, GL20.GL_FRAGMENT_SHADER)
    }

    protected fun createShader(shaderCode: String, shaderType: Int): Int {
        val shaderId = GL20.glCreateShader(shaderType)
        if (shaderId == 0) {
            throw Exception("シェーダの作成に失敗: $shaderType")
        }
        GL20.glShaderSource(shaderId, shaderCode)
        GL20.glCompileShader(shaderId)
        if (GL20.glGetShaderi(shaderId, GL20.GL_COMPILE_STATUS) == 0) {
            throw Exception("シェーダのコンパイルに失敗: " + GL20.glGetShaderInfoLog(shaderId, 1024))
        }
        GL20.glAttachShader(programId, shaderId)
        return shaderId
    }

    fun link() {
        GL20.glLinkProgram(programId)
        if (GL20.glGetProgrami(programId, GL20.GL_LINK_STATUS) == 0) {
            throw Exception("シェーダのリンクに失敗: " + GL20.glGetProgramInfoLog(programId, 1024))
        }
        if (vertexShaderId != 0) {
            GL20.glDetachShader(programId, vertexShaderId)
        }
        if (fragmentShaderId != 0) {
            GL20.glDetachShader(programId, fragmentShaderId)
        }
        GL20.glValidateProgram(programId)
        if (GL20.glGetProgrami(programId, GL20.GL_VALIDATE_STATUS) == 0) {
            System.err.println("Warning validating Shader code: " + GL20.glGetProgramInfoLog(programId, 1024))
        }
    }

    fun bind() {
        GL20.glUseProgram(programId)
    }

    fun unbind() {
        GL20.glUseProgram(0)
    }

    fun cleanup() {
        unbind()
        if (programId != 0) {
            GL20.glDeleteProgram(programId)
        }
    }

    init {
        programId = GL20.glCreateProgram()
        if (programId == 0) {
            throw Exception("Could not create Shader")
        }
        uniforms = HashMap()
    }
}
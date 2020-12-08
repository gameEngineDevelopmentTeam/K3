package itoh.engine.polygon

import itoh.engine.polygon.light.PointLight
import org.joml.Matrix4f
import org.joml.Vector3f
import org.joml.Vector4f
import org.lwjgl.opengl.GL20.*
import org.lwjgl.system.MemoryStack


class Shader {
    private val programId: Int = glCreateProgram()
    private var vertexShaderId = 0
    private var fragmentShaderId = 0
    private val uniforms: MutableMap<String, Int>
    fun createUniform(uniformName: String) {
        val uniformLocation = glGetUniformLocation(programId, uniformName)
        if (uniformLocation < 0) {
            throw Exception("uniformが見つかりません:$uniformName")
        }
        uniforms[uniformName] = uniformLocation
    }

    fun createPointLightUniform(uniformName: String) {
        createUniform("$uniformName.colour")
        createUniform("$uniformName.position")
        createUniform("$uniformName.intensity")
        createUniform("$uniformName.att.constant")
        createUniform("$uniformName.att.linear")
        createUniform("$uniformName.att.exponent")
    }

    fun createMaterialUniform(uniformName: String) {
        createUniform("$uniformName.ambient")
        createUniform("$uniformName.diffuse")
        createUniform("$uniformName.specular")
        createUniform("$uniformName.hasTexture")
        createUniform("$uniformName.reflectance")
    }

    fun setUniform(uniformName: String, value: Matrix4f) {
        MemoryStack.stackPush().use { stack ->
            glUniformMatrix4fv(uniforms[uniformName]!!, false,
                    value[stack.mallocFloat(16)])
        }
    }

    fun setUniform(uniformName: String, value: Int) {
        glUniform1i(uniforms[uniformName]!!, value)
    }

    fun setUniform(uniformName: String, value: Vector3f) {
        glUniform3f(uniforms[uniformName]!!, value.x, value.y, value.z)
    }

    fun setUniform(uniformName: String?, value: Float) {
        glUniform1f(uniforms[uniformName]!!, value)
    }


    fun createVertexShader(shaderCode: String) {
        vertexShaderId = createShader(shaderCode, GL_VERTEX_SHADER)
    }

    fun createFragmentShader(shaderCode: String) {
        fragmentShaderId = createShader(shaderCode, GL_FRAGMENT_SHADER)
    }

    fun setUniform(uniformName: String?, value: Vector4f) {
        glUniform4f(uniforms[uniformName]!!, value.x, value.y, value.z, value.w)
    }

    fun setUniform(uniformName: String, pointLight: PointLight) {
        setUniform("$uniformName.colour", pointLight.color!!)
        setUniform("$uniformName.position", pointLight.position!!)
        setUniform("$uniformName.intensity", pointLight.intensity)
        val att = pointLight.attenuation
        setUniform("$uniformName.att.constant", att.constant)
        setUniform("$uniformName.att.linear", att.linear)
        setUniform("$uniformName.att.exponent", att.exponent)
    }

    fun setUniform(uniformName: String, material: Material) {
        setUniform("$uniformName.ambient", material.ambientColour)
        setUniform("$uniformName.diffuse", material.diffuseColour)
        setUniform("$uniformName.specular", material.specularColour)
        setUniform("$uniformName.hasTexture", if (material.isTextured) 1 else 0)
        setUniform("$uniformName.reflectance", material.reflectance)
    }


    private fun createShader(shaderCode: String, shaderType: Int): Int {
        val shaderId = glCreateShader(shaderType)
        if (shaderId == 0) {
            throw Exception("シェーダの作成に失敗: $shaderType")
        } else {
            println("シェーダの作成に成功 shaderType:$shaderType")
        }
        glShaderSource(shaderId, shaderCode)
        glCompileShader(shaderId)
        if (glGetShaderi(shaderId, GL_COMPILE_STATUS) == 0) {
            throw Exception("シェーダのコンパイルに失敗: ${glGetShaderInfoLog(shaderId, 1024)}")
        } else {
            println("シェーダのコンパイルに成功")
        }
        glAttachShader(programId, shaderId)
        return shaderId
    }

    fun link() {
        glLinkProgram(programId)
        if (glGetProgrami(programId, GL_LINK_STATUS) == 0) {
            throw Exception("シェーダのリンクに失敗: " + glGetProgramInfoLog(programId, 1024))
        } else {
            println("シェーダのリンクに成功")
        }
        if (vertexShaderId != 0) {
            glDetachShader(programId, vertexShaderId)
        }
        if (fragmentShaderId != 0) {
            glDetachShader(programId, fragmentShaderId)
        }
        glValidateProgram(programId)
        if (glGetProgrami(programId, GL_VALIDATE_STATUS) == 0) {
            System.err.println("Warning validating Shader code: " + glGetProgramInfoLog(programId, 1024))
        }
    }

    fun bind() {
        glUseProgram(programId)
    }

    fun unbind() {
        glUseProgram(0)
    }

    fun cleanup() {
        unbind()
        if (programId != 0) {
            glDeleteProgram(programId)
        }
    }

    init {
        if (programId == 0) {
            throw Exception("シェーダプログラムの作成に失敗")
        } else {
            println("シェーダプログラムの作成に成功 programID:$programId")
        }
        uniforms = HashMap()
    }
}
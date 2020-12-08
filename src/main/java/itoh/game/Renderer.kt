package itoh.game

import itoh.engine.Resources
import itoh.engine.Window
import itoh.engine.polygon.Camera
import itoh.engine.polygon.Obj3D
import itoh.engine.polygon.Shader
import itoh.engine.polygon.Transformation
import itoh.engine.polygon.light.PointLight
import org.joml.Matrix4f
import org.joml.Vector3f
import org.joml.Vector4f
import org.lwjgl.opengl.GL11.*


open class Renderer {
    companion object {
        private val fov: Float = Math.toRadians(60.0).toFloat()
        private const val zNear: Float = 0.01f
        private const val zFar: Float = 1000.0f
    }

    private val transformation: Transformation = Transformation()
    private lateinit var shader: Shader
    private var specularPower:Float=10f

    open fun clear() {
        glClear(GL_COLOR_BUFFER_BIT or GL_DEPTH_BUFFER_BIT)
    }

    open fun render(window: Window, camera: Camera, objects: Array<Obj3D>, ambientLight: Vector3f,pointLight: PointLight) {
        clear()
        if (window.resized) {
            glViewport(0, 0, window.width, window.height)
            window.resized = true
        }
        shader.bind()

        val projectionMatrix: Matrix4f = transformation.getProjectionMatrix(
                fov = fov,
                width = window.width,
                height = window.height,
                zNear = zNear,
                zFar = zFar
        )
        shader.setUniform("projectionMatrix", projectionMatrix)
        val viewMatrix = transformation.getViewMatrix(camera)
        shader.setUniform("ambientLight", ambientLight)
        shader.setUniform("specularPower", specularPower)
        val currPointLight = PointLight(pointLight)
        val lightPos = currPointLight.position
        val aux = Vector4f(lightPos, 1f)
        aux.mul(viewMatrix)
        lightPos!!.x = aux.x
        lightPos.y = aux.y
        lightPos.z = aux.z
        shader.setUniform("pointLight", currPointLight)

        shader.setUniform("Texture", 0)

        for (i in objects) {
            val mesh = i.mesh
            val modelViewMatrix = transformation.getModelViewMatrix(i, viewMatrix) ?: throw Exception()
            shader.setUniform("modelViewMatrix", modelViewMatrix)
            shader.setUniform("material", mesh.material)
            mesh.render()
        }
        shader.unbind()
    }

    open fun cleanup() {
        shader.cleanup()
    }

    fun initialization() {
        shader = Shader()
        shader.createVertexShader(Resources.loadResource("vertex.glsl"))
        shader.createFragmentShader(Resources.loadResource("fragment.glsl"))
        shader.link()
        shader.createUniform("projectionMatrix")
        shader.createUniform("modelViewMatrix")
        shader.createUniform("Texture")
        shader.createMaterialUniform("material")
        shader.createUniform("specularPower")
        shader.createUniform("ambientLight")
        shader.createPointLightUniform("pointLight")
    }
}
package itoh.engine.polygon

import org.joml.Matrix4f
import org.joml.Vector3f

class Transformation {
    private val projectionMatrix: Matrix4f = Matrix4f()
    fun getProjectionMatrix(fov: Float, width: Int, height: Int, zNear: Float, zFar: Float): Matrix4f =
            projectionMatrix.setPerspective(fov, width.toFloat() / height.toFloat(), zNear, zFar)

    private val viewMatrix: Matrix4f = Matrix4f()
    fun getViewMatrix(camera: Camera): Matrix4f {
        val cameraPos = camera.position
        val rotation = camera.rotation
        viewMatrix.identity()
        viewMatrix.rotate(Math.toRadians(rotation.x.toDouble()).toFloat(), Vector3f(1f, 0f, 0f))
                .rotate(Math.toRadians(rotation.y.toDouble()).toFloat(), Vector3f(0f, 1f, 0f))
        viewMatrix.translate(-cameraPos.x, -cameraPos.y, -cameraPos.z)
        return viewMatrix
    }

    private val modelViewMatrix: Matrix4f = Matrix4f()
    fun getModelViewMatrix(objects: Obj3D, viewMatrix: Matrix4f?): Matrix4f? {
        val rotation: Vector3f = objects.rotation
        modelViewMatrix.identity().translate(objects.position)
                .rotateX(Math.toRadians(-rotation.x.toDouble()).toFloat())
                .rotateY(Math.toRadians(-rotation.y.toDouble()).toFloat())
                .rotateZ(Math.toRadians(-rotation.z.toDouble()).toFloat())
                .scale(objects.scale)
        val viewCurr = Matrix4f(viewMatrix)
        return viewCurr.mul(modelViewMatrix)
    }
}
package itoh.engine.polygon

import org.joml.Matrix4f
import org.joml.Vector3f


class Transformation {
    private val projectionMatrix: Matrix4f
    private val worldMatrix: Matrix4f

    public fun getProjectionMatrix(fov: Float, width: Int, height: Int, zNear: Float, zFar: Float): Matrix4f =
            projectionMatrix.setPerspective(fov, width.toFloat() / height.toFloat(), zNear, zFar)

    public fun getWorldMatrix(offset: Vector3f, rotation: Vector3f, scale: Float): Matrix4f =
            worldMatrix.identity().translation(offset).
            rotateX(Math.toRadians(rotation.x.toDouble()).toFloat()).
            rotateY(Math.toRadians(rotation.y.toDouble()).toFloat()).
            rotateZ(Math.toRadians(rotation.z.toDouble()).toFloat()).scale(scale)

    init {
        projectionMatrix = Matrix4f()
        worldMatrix = Matrix4f()
    }
}
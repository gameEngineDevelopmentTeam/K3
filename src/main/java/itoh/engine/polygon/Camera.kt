package itoh.engine.polygon

import org.joml.Vector3f
import kotlin.math.cos
import kotlin.math.sin

class Camera {
    private val position: Vector3f
    private val _rotation: Vector3f



    constructor() {
        this.position = Vector3f()
        this._rotation = Vector3f()
    }

    constructor(position: Vector3f, rotation: Vector3f) {
        this.position = position
        this._rotation = rotation
    }

    fun getPosition(): Vector3f {
        return position
    }

    fun setPosition(x: Float, y: Float, z: Float) {
        position.x = x
        position.y = y
        position.z = z
    }

    fun movePosition(offsetX: Float, offsetY: Float, offsetZ: Float) {
        if (offsetZ != 0f) {
            position.x += sin(Math.toRadians(_rotation.y.toDouble())).toFloat() * -1.0f * offsetZ
            position.z += cos(Math.toRadians(_rotation.y.toDouble())).toFloat() * offsetZ
        }
        if (offsetX != 0f) {
            position.x += sin(Math.toRadians(_rotation.y - 90.toDouble())).toFloat() * -1.0f * offsetX
            position.z += cos(Math.toRadians(_rotation.y - 90.toDouble())).toFloat() * offsetX
        }
        position.y += offsetY
    }

    fun getRotation(): Vector3f {
        return _rotation
    }

    fun setRotation(x: Float, y: Float, z: Float) {
        _rotation.x = x
        _rotation.y = y
        _rotation.z = z
    }

    fun moveRotation(offsetX: Float, offsetY: Float, offsetZ: Float) {
        _rotation.x += offsetX
        _rotation.y += offsetY
        _rotation.z += offsetZ
    }
}
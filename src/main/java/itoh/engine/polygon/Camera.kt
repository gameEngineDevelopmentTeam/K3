package itoh.engine.polygon

import org.joml.Vector3f
import kotlin.math.cos
import kotlin.math.sin

class Camera {
    private val _position: Vector3f
    var position: Vector3f
        get() = _position
        set(value) {
            _position.x = value.x
            _position.y = value.y
            _position.z = value.z
        }

    private val _rotation: Vector3f
    var rotation: Vector3f
        get() = _rotation
        set(value) {
            _rotation.x = value.x
            _rotation.y = value.y
            _rotation.z = value.z
        }


    constructor() {
        this._position = Vector3f()
        this._rotation = Vector3f()
    }

    constructor(position: Vector3f, rotation: Vector3f) {
        this._position = position
        this._rotation = rotation
    }

    fun movePosition(offsetX: Float, offsetY: Float, offsetZ: Float) {
        if (offsetZ != 0f) {
            _position.x += sin(Math.toRadians(_rotation.y.toDouble())).toFloat() * -1.0f * offsetZ
            _position.z += cos(Math.toRadians(_rotation.y.toDouble())).toFloat() * offsetZ
        }
        if (offsetX != 0f) {
            _position.x += sin(Math.toRadians(_rotation.y - 90.toDouble())).toFloat() * -1.0f * offsetX
            _position.z += cos(Math.toRadians(_rotation.y - 90.toDouble())).toFloat() * offsetX
        }
        _position.y += offsetY
    }

    fun moveRotation(offsetX: Float, offsetY: Float, offsetZ: Float) {
        _rotation.x += offsetX
        _rotation.y += offsetY
        _rotation.z += offsetZ
    }
}
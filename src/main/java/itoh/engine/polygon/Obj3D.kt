package itoh.engine.polygon

import org.joml.Vector3f

class Obj3D constructor(val mesh: Mesh) {
    var scale: Float = 1f
    private val _position: Vector3f = Vector3f()
    var position: Vector3f
        get() = _position
        set(value) {
            _position.x = value.x
            _position.y = value.y
            _position.z = value.z
        }

    private val _rotation: Vector3f = Vector3f()
    var rotation: Vector3f
        get() = _rotation
        set(value) {
            _rotation.x = value.x
            _rotation.y = value.y
            _rotation.z = value.z
        }
}
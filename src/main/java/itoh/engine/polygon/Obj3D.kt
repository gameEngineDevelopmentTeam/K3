package itoh.engine.polygon

import org.joml.Vector3f

class Obj3D constructor(private val mesh: Mesh) {
    private val position: Vector3f = Vector3f()
    fun getPosition(): Vector3f {
        return position
    }

    fun setPosition(x: Float, y: Float, z: Float) {
        position.x = x
        position.y = y
        position.z = z
    }

    private var scale: Float
    fun getScale(): Float {
        return scale
    }

    fun setScale(scale: Float) {
        this.scale = scale
    }

    private val rotation: Vector3f
    fun getRotation(): Vector3f {
        return rotation
    }

    fun setRotation(x: Float, y: Float, z: Float) {
        rotation.x = x
        rotation.y = y
        rotation.z = z
    }

    fun getMesh(): Mesh {
        return mesh
    }

    init {
        scale = 1f
        rotation = Vector3f()
    }
}
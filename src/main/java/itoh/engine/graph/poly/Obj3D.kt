package itoh.engine.graph.poly

import itoh.engine.graph.Mesh
import org.joml.Vector3f

class Obj3D(private val mesh: Mesh) {
     private val position: Vector3f = Vector3f()
     private var scale: Float = 1f
    private val rotation: Vector3f = Vector3f()

    fun setPosition(x: Float, y: Float, z: Float) {
        position.x = x
        position.y = y
        position.z = z
    }

    fun setScale(scale: Float) {
        this.scale = scale
    }

    fun setRotation(x: Float, y: Float, z: Float) {
        rotation.x = x
        rotation.y = y
        rotation.z = z
    }

    fun getPosition():Vector3f{
        return position
    }

    fun getScale():Float{
        return scale
    }

    fun getRotation():Vector3f{
        return rotation
    }

    fun getMesh(): Mesh {
        return mesh
    }
}
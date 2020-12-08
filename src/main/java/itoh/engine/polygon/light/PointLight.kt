package itoh.engine.polygon.light

import org.joml.Vector3f

class PointLight(color: Vector3f?, position: Vector3f?, intensity: Float) {
    var color: Vector3f?
    var position: Vector3f?
    var intensity: Float
    var attenuation: Attenuation

    constructor(color: Vector3f?, position: Vector3f?, intensity: Float, attenuation: Attenuation) : this(color, position, intensity) {
        this.attenuation = attenuation
    }

    constructor(pointLight: PointLight) : this(Vector3f(pointLight.color), Vector3f(pointLight.position),
            pointLight.intensity, pointLight.attenuation) {
    }

    init {
        attenuation = Attenuation(1f, 0f, 0f)
        this.color = color
        this.position = position
        this.intensity = intensity
    }
}
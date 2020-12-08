package itoh.engine.polygon

import org.joml.Vector4f

class Material {
    var ambientColor: Vector4f
    var diffuseColor: Vector4f
    var specularColor: Vector4f
    var reflectance: Float
    var texture: Texture?

    constructor() {
        ambientColor = DEFAULT_COLOR
        diffuseColor = DEFAULT_COLOR
        specularColor = DEFAULT_COLOR
        texture = null
        reflectance = 0f
    }

    constructor(color: Vector4f, reflectance: Float) : this(color, color, color, null, reflectance) {}
    constructor(texture: Texture?) : this(DEFAULT_COLOR, DEFAULT_COLOR, DEFAULT_COLOR, texture, 0f) {}
    constructor(texture: Texture?, reflectance: Float) : this(DEFAULT_COLOR, DEFAULT_COLOR, DEFAULT_COLOR, texture, reflectance) {}
    constructor(ambientColor: Vector4f, diffuseColor: Vector4f, specularColor: Vector4f, texture: Texture?, reflectance: Float) {
        this.ambientColor = ambientColor
        this.diffuseColor = diffuseColor
        this.specularColor = specularColor
        this.texture = texture
        this.reflectance = reflectance
    }

    val isTextured: Boolean
        get() = texture != null

    companion object {
        private val DEFAULT_COLOR = Vector4f(1.0f, 1.0f, 1.0f, 1.0f)
    }
}
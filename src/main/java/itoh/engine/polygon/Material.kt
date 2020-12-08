package itoh.engine.polygon

import org.joml.Vector4f

class Material {
    var ambientColour: Vector4f
    var diffuseColour: Vector4f
    var specularColour: Vector4f
    var reflectance: Float
    var texture: Texture?

    constructor() {
        ambientColour = DEFAULT_COLOUR
        diffuseColour = DEFAULT_COLOUR
        specularColour = DEFAULT_COLOUR
        texture = null
        reflectance = 0f
    }

    constructor(colour: Vector4f, reflectance: Float) : this(colour, colour, colour, null, reflectance) {}
    constructor(texture: Texture?) : this(DEFAULT_COLOUR, DEFAULT_COLOUR, DEFAULT_COLOUR, texture, 0f) {}
    constructor(texture: Texture?, reflectance: Float) : this(DEFAULT_COLOUR, DEFAULT_COLOUR, DEFAULT_COLOUR, texture, reflectance) {}
    constructor(ambientColour: Vector4f, diffuseColour: Vector4f, specularColour: Vector4f, texture: Texture?, reflectance: Float) {
        this.ambientColour = ambientColour
        this.diffuseColour = diffuseColour
        this.specularColour = specularColour
        this.texture = texture
        this.reflectance = reflectance
    }

    val isTextured: Boolean
        get() = texture != null

    companion object {
        private val DEFAULT_COLOUR = Vector4f(1.0f, 1.0f, 1.0f, 1.0f)
    }
}
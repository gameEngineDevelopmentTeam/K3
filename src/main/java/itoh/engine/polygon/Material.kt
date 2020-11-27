package itoh.engine.polygon

import org.joml.Vector4f

class Material {
    private val defaultColor:Vector4f= Vector4f(1.0f,1.0f,1.0f,1.0f)
    private var ambientColor:Vector4f
    private var diffuseColor:Vector4f
    private var specularColor:Vector4f
    private var ref:Float = 0f
    private lateinit var texture:Texture

    init {
        ambientColor = defaultColor
        diffuseColor=defaultColor
        specularColor = defaultColor
    }


}
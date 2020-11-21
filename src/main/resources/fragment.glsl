#version 330

in  vec2 outTexPos;
out vec4 fragColor;

uniform sampler2D Texture;

void main()
{
    fragColor = texture(Texture, outTexPos);
}
#version 330

in  vec2 outTexPos;
out vec4 fragColor;

uniform sampler2D Texture;
uniform vec3 color;
uniform int useColor;

void main()
{
    if (useColor == 1) {
        fragColor = vec4(color, 1);
    }
    else {
        fragColor = texture(Texture, outTexPos);
    }
}
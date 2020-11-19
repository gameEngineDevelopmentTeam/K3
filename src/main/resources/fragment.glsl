#version 330

in  vec2 outTexPos;
out vec4 fragColor;

uniform sampler2D Texture;
uniform vec3 color;
uniform int useColor;

void main()
{
    if (useColour == 1) {
        fragColor = vec4(colour, 1);
    }
    else {
        fragColor = texture(texture_sampler, outTexCoord);
    }
}
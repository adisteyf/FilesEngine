#version 430 core
out vec4 color;
uniform sampler2D sampler;

in vec2 tex_coords;

void main()
{
    color = texture(sampler, tex_coords);
}
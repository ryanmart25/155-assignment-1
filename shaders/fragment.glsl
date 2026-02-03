#version 430
out vec4 color;
uniform vec4 currentcolor;
void main(void)
{
    color = currentcolor;
}
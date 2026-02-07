#version 430
out vec4 color;
in vec4 varyingcolor;

uniform vec4 invariantColor;
uniform bool useGradient;

void main(void)
{
    if(useGradient){
        color = varyingcolor;
    }else{
        color = invariantcolor;
    }

}
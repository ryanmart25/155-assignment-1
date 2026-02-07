#version 430
uniform float offset;
uniform float angle;
uniform bool moveincircle;
uniform float sizeIncrement;
uniform bool useGradient;
uniform vec4 invariantColor;

out vec4 varyingcolor;

void main(void)
{
    if(moveincircle){
        if(gl_VertexID == 0){
            gl_Position = vec4( (0.1 + sizeIncrement) + cos(angle), -0.25 + sin(angle), 0.0, 1.0);
            varyingcolor = vec4( 1.0, 0.0, 0.0, 1.0);
        }else if(gl_VertexID == 1){
            gl_Position = vec4((-0.1 - sizeIncrement) + cos(angle), -0.25 + sin(angle), 0.0, 1.0);
            varyingcolor = vec4(0.0, 0.5, 0.0, 1.0);
        }else{
             gl_Position = vec4( 0.0 + cos(angle), (0.85 + sizeIncrement) + sin(angle), 0.0, 1.0);
             varyingcolor = vec4( 0.0, 0.0, 1.0, 1.0);
        }
    }else{
        if (gl_VertexID == 0){
            gl_Position = vec4( (0.1 + sizeIncrement) + offset, -0.25, 0.0, 1.0);
            varyingcolor = vec4( 1.0, 0.0, 0.0, 1.0);
        }
        else if (gl_VertexID == 1){
            gl_Position = vec4((-0.1 - sizeIncrement) + offset, -0.25, 0.0, 1.0);
            varyingcolor = vec4(0.0, 0.5, 0.0, 1.0);
        }
        else{
            gl_Position = vec4( 0.0 + offset, (0.85 + sizeIncrement), 0.0, 1.0);
            varyingcolor = vec4( 0.0, 0.0, 1.0, 1.0);
        }
    }

}
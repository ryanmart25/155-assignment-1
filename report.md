# Report

# Interactions with LLMs and other Generative AI
I performed the following google search and read the following summarization from google's "AI summarization".

Search Query: glsl uniform vs in

In GLSL,  and  (input) are both qualifiers used to pass data from the CPU application to the GPU shaders, but they differ fundamentally in how frequently the data changes and how they are accessed. [1, 2]  
Key Differences at a Glance

| Feature [1, 3, 4, 5, 6, 7] |  | (Input / Attribute)  |
| --- | --- | --- |
| Frequency | Same value for all vertices/fragments in a draw call. | Different value for each vertex or fragment.  |
| Data Flow | CPU $\rightarrow$ Vertex & Fragment Shaders. | CPU $\rightarrow$ Vertex Shader (Attributes) or Vertex $\rightarrow$ Frag (Varyings).  |
| Scope | Global (accessible in all stages). | Local to the current stage.  |
| Modification | Read-only (set by CPU). | Read-only within the shader.  |
| Typical Use | Matrices, Light Positions, Time. | Position, Color, Texture Coords.  |

Uniforms are variables that remain constant for the entire duration of a single draw call (, ). They are used to pass data that doesn't change per vertex, such as transformation matrices (MVP), camera positions, or color parameters.

• Accessibility: Accessible in both vertex and fragment shaders.
• Performance: Changing a uniform between draw calls is relatively efficient.
• Example:  [3, 8, 11]

(Input / Attribute)
In modern GLSL (OpenGL 3.0+),  is used to define input data that varies per vertex (in the vertex shader) or is interpolated between vertices (in the fragment shader).

• Vertex Shader: Inputs () are attributes (position, normals, UVs) coming from Vertex Buffer Objects (VBOs).
• Fragment Shader: Inputs () are usually interpolation results ( from the vertex shader).
• Example (Vertex Shader):  [1, 14, 15, 16, 17]

Key Takeaways

1. Uniforms are "Global Knobs": Imagine them as knobs on a machine; you turn them once to change how all incoming parts are processed.
2. Inputs are "Conveyor Belts": Inputs are the data flowing in for each individual item being processed.
3. If it changes per vertex, use  (attribute).
4. If it stays the same for the whole shape, use . [3, 18, 19]

AI responses may include mistakes.

[1] https://people.eecs.ku.edu/~jrmiller/Courses/OpenGL/DeveloperNotes/GLSL_Overview.html
[2] https://www.youtube.com/watch?v=mat3uvF33O0
[3] https://stackoverflow.com/questions/41574034/difference-between-glsl-shader-variable-types
[4] https://stackoverflow.com/questions/17537879/in-webgl-what-are-the-differences-between-an-attribute-a-uniform-and-a-varying
[5] https://wikis.khronos.org/opengl/Uniform_(GLSL)
[6] https://math.hws.edu/eck/cs424/notes2013/17_WebGL_Intro.html
[7] https://www.delasign.com/blog/what-are-uniform-variables-in-shaders/
[8] https://gamedev.stackexchange.com/questions/65857/what-is-the-difference-between-shader-uniforms-and-attributes-in-opengl
[9] https://webkit.org/blog/7380/next-generation-3d-graphics-on-the-web/
[10] http://learnwebgl.brown37.net/rendering/introduction.html
[11] https://learnopengl.com/Advanced-OpenGL/Advanced-GLSL
[12] https://www.reddit.com/r/learnprogramming/comments/3b2uvn/cgshaders_what_are_in_out_uniform_and_inout/
[13] https://cables.gl/docs/5_writing_ops/shader/shader
[14] https://www.reddit.com/r/opengl/comments/4bu5fc/on_glsl_in_out_vs_varying/
[15] https://www.reddit.com/r/opengl/comments/6mvods/uniforms_vs_generic_vertex_attribute_values/
[16] https://stanford.edu/class/ee267/lectures/lecture4.pdf
[17] https://derivative.ca/UserGuide/Write_a_GLSL_Material
[18] https://www.reddit.com/r/opengl/comments/61gs69/understanding_uniform/
[19] https://excaliburjs.com/docs/materials/


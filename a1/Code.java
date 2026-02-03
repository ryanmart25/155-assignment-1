package a1;

import javax.swing.*;
import static com.jogamp.opengl.GL4.*;
import com.jogamp.opengl.*;
import com.jogamp.opengl.awt.GLCanvas;
import com.jogamp.opengl.util.*;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.InvalidPathException;
import java.nio.file.Path;
import java.util.List;

public class Code extends JFrame implements GLEventListener, KeyListener {
  @Override
  public void keyTyped(KeyEvent e) {

  }

  @Override
  public void keyPressed(KeyEvent e) {
    switch (e.getKeyCode()){
      case KeyEvent.VK_1 -> {
        System.out.println("'1' was pressed");
      }
      case KeyEvent.VK_2 -> {
        System.out.println("2 was pressed!");
        currentColor = currentColor.next();
        color = computeColor(currentColor);
      }
      case KeyEvent.VK_3 -> {
        System.out.println("'3' was pressed");
      }
      case KeyEvent.VK_4 -> {
        System.out.println("'4' was pressed");
      }
      case KeyEvent.VK_5 -> {
        System.out.println("'5' was pressed");
      }
      default -> {
      }
    }
  }

  @Override
  public void keyReleased(KeyEvent e) {

  }

  private enum DEBUG { // enum so i can have a bunch of levels to this and extend as needed.
    DEBUG_ON,
    DEBUG_OFF
  }
  private enum Direction {
    UP,
    DOWN,
    LEFT,
    RIGHT
  }
  private enum Color {
    YELLOW,
    PURPLE,
    GRADIENT;
    public Color next(){
      Color[] values = Color.values();
      int nextOrdinal = (this.ordinal() + 1) % values.length;
      return values[nextOrdinal];
    }
  }

  private GLCanvas myCanvas;
  private int renderingProgram;
  private int vao[] = new int[1];
  private float x = 0.0f;
  private float inc = 0.01f;
  private long startTime = System.currentTimeMillis();
  Color currentColor = Color.YELLOW;
  float[] color = new float[]{1.0f, 1.0f, 0.0f, 1.0f};
  public Code() {
    setTitle("Assignment 1 - OpenGL and JOGL");
    setSize(600, 400);
    setLocation(200, 200);
    myCanvas = new GLCanvas();
    myCanvas.addGLEventListener(this);
    myCanvas.addKeyListener(this);
    this.add(myCanvas);
    this.setVisible(true);
    Animator animator = new Animator(myCanvas);
    animator.start();
  }
  private float[] computeColor(Color c){
    switch (c){
      case Color.YELLOW -> {
        return new float[]{1.0f, 1.0f, 0.0f, 1.0f};
      }
      case Color.PURPLE -> {
        return new float[]{0.5f, 0.0f, 0.5f, 1.0f};
      }
      case Color.GRADIENT -> {
        return new float[]{1.0f, 1.0f, 1.0f, 1.0f};
      }
      default -> {
        return new float[]{0.5f, 0.4f, 0.5f, 1.0f};
      }
    }
  }
  // if (x > 1.0f) inc = -0.01f; // switch to moving the triangle to the left
  // if (x < -1.0f) inc = 0.01f; // switch to moving the triangle to the right
  public void display(GLAutoDrawable drawable) {
    GL4 gl = (GL4) GLContext.getCurrentGL();
    gl.glClear(GL_DEPTH_BUFFER_BIT);
    gl.glClear(GL_COLOR_BUFFER_BIT); // clear the background to black, each time
    gl.glUseProgram(renderingProgram);
    long now = System.currentTimeMillis();

    if (x > 1.0f) {// flip the direction
      // adjust increment by min of elapsed time or window width
      inc = Math.min((now - startTime), 1.0f) * -0.01f;
    }
    if (x < -1.0f)// flip the direction
      inc = Math.min((now - startTime), 1.0f) * 0.01f;
    startTime = now;
    x += inc;// / (System.nanoTime() - startTime); // move the triangle along x axis
    int offsetLoc = gl.glGetUniformLocation(renderingProgram, "offset");
    gl.glProgramUniform1f(renderingProgram, offsetLoc, x); // send value in "x" to "offset"
    int colorLoc = gl.glGetUniformLocation(renderingProgram, "currentcolor");
    gl.glProgramUniform4f(renderingProgram, colorLoc, color[0], color[1], color[2], color[3]);
    gl.glDrawArrays(GL_TRIANGLES, 0, 3);
  }

  public static void main(String[] args) {
    new Code();
  }

  public void init(GLAutoDrawable drawable) {
    GL4 gl = (GL4) GLContext.getCurrentGL();
    System.out.println("JOGL Version: " + Package.getPackage("com.jogamp.opengl").getImplementationVersion());
    System.out.println("OpenGL Version: " + gl.glGetString(GL_VERSION));
    renderingProgram = createShaderProgram();
    gl.glGenVertexArrays(vao.length, vao, 0);
    gl.glBindVertexArray(vao[0]);
  }

  private int createShaderProgram() {
    GL4 gl = (GL4) GLContext.getCurrentGL();

    String[] vshaderSource = readShader("C:\\Users\\timef\\Documents\\Workspaces\\csc155-assignment-1\\shaders\\vertex.glsl");
    String[] fshaderSource = readShader("C:\\Users\\timef\\Documents\\Workspaces\\csc155-assignment-1\\shaders\\fragment.glsl");
    if (vshaderSource[0].equals("INVALID") || fshaderSource[0].equals("INVALID")){
      System.exit(-1);
    }
    int vShader = gl.glCreateShader(GL_VERTEX_SHADER);
    gl.glShaderSource(vShader, vshaderSource.length, vshaderSource, null, 0); // 3 is the count of lines of source code.
    gl.glCompileShader(vShader);

    int fShader = gl.glCreateShader(GL_FRAGMENT_SHADER);
    gl.glShaderSource(fShader, fshaderSource.length, fshaderSource, null, 0); // 4 is the count of lines of source
                                                                              // codegl.glCompileShader(fshader);
    gl.glCompileShader(fShader);
    int vfProgram = gl.glCreateProgram();
    gl.glAttachShader(vfProgram, vShader);
    gl.glAttachShader(vfProgram, fShader);
    gl.glLinkProgram(vfProgram);

    gl.glDeleteShader(vShader);
    gl.glDeleteShader(fShader);
    return vfProgram;
  }

  public void reshape(GLAutoDrawable drawable, int x, int y, int width, int height) {
  }

  public void dispose(GLAutoDrawable drawable) {
  }

  private static String[] readShader(String filename) {
    String[] err = new String[1];

    try {
      Path filepath = Path.of(filename);
      if (Files.notExists(filepath)) {
        System.err.println("Shader file at: " + filename + " does not exist.");
        err[0] = "INVALID";
        return err;
      }
      List<String> lines = Files.readAllLines(filepath);
      String[] out = new String[lines.size()];
      for (int iter = 0; iter < out.length; iter++) {
        out[iter] = lines.get(iter);
      }
      return out;
    } catch (SecurityException e) {
      System.err.println("Failed to access the file due to a security exception.");
      System.err.println(e.getMessage());
      return err;
    } catch (InvalidPathException e) {
      System.err.println("The path" + filename + " is invalid");
      throw new RuntimeException(e);
    } catch (IOException e) {
      System.err.println("An IO exception occurred while reading the shader at path: " + filename);
      System.err.println(e.getMessage());
      return err;
    }

  }
}

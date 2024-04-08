package com.fe.jade;
/*
----------------------------------------
-  Credits: Adisteyf (adk.)            -
-  GitHub: https://github.com/adisteyf -
-  For: Shawarma Team (link below)     -
- https://discord.gg/invite/8PrbHxjwxv -
----------------------------------------
*/
import com.fe.components.SpriteRender;
import com.fe.render.Shader;
import com.fe.render.Texture;
import com.fe.util.Time;
import org.joml.Vector2f;
import org.lwjgl.BufferUtils;

import java.nio.FloatBuffer;
import java.nio.IntBuffer;

import static org.lwjgl.glfw.GLFW.*;
import static org.lwjgl.glfw.GLFW.GLFW_GAMEPAD_AXIS_RIGHT_X;
import static org.lwjgl.opengl.GL20.*;
import static org.lwjgl.opengl.GL30.*;

public class SceneEditor extends Scene {
    private float[] vertexArray = {
            //pos                      color                       UV Coordinates
             100f, 0f, 0.0f,      1.0f, 0.0f, 0.0f, 1.0f,     1, 1, // Bottom right 0
            0f,  100f, 0.0f,      0.0f, 1.0f, 0.0f, 1.0f,     0, 0, // Top left     1
             100f,  100f, 0.0f,    0.0f, 0.0f, 1.0f, 1.0f,     1, 0, // Top right    2
            0f, 0f, 0.0f,        1.0f, 1.0f, 0.0f, 1.0f,     0, 1  // Bottom left  3
    };

    //IMPORTANT
    private int[] elementArray = {
            2, 1, 0, // Top right triangle
            0, 1, 3, // Bottom left triangle
    };
    private int vaoID, vboID, eboID;
    private Shader defaultShader;
    private Texture testTexture;

    gObject testobj;
    private boolean firstTime = true;
    public static boolean TryConnectJoystick = true;
    public SceneEditor() {
        Shader testShader = new Shader("assets/shaders/default.glsl");
    }

    @Override
    public void init() {
        System.out.println( "Creating test obj" );
        this.testobj = new gObject("test obj");
        this.testobj.addComponents(new SpriteRender());
        this.addGObjectToScene(this.testobj);

        this.camera = new Camera(new Vector2f());
        defaultShader = new Shader( "assets/shaders/default.glsl" );
        defaultShader.compile();
        this.testTexture = new Texture("assets/textures/based_tex.jpg");

        vaoID = glGenVertexArrays();
        glBindVertexArray(vaoID);

        // Create a float buffer of vertices
        FloatBuffer vertexBuffer = BufferUtils.createFloatBuffer(vertexArray.length);
        vertexBuffer.put(vertexArray).flip();

        // Create VBO upload the vertex buffer
        vboID = glGenBuffers();
        glBindBuffer(GL_ARRAY_BUFFER, vboID);
        glBufferData(GL_ARRAY_BUFFER, vertexBuffer, GL_STATIC_DRAW);

        // Create the indices and upload
        IntBuffer elBuffer = BufferUtils.createIntBuffer(elementArray.length);
        elBuffer.put(elementArray).flip();

        eboID = glGenBuffers();
        glBindBuffer(GL_ELEMENT_ARRAY_BUFFER, eboID);
        glBufferData(GL_ELEMENT_ARRAY_BUFFER, elBuffer, GL_STATIC_DRAW);

        // Add the vertex attribute pointers
        int posSizes = 3;
        int colorSize = 4;
        int uvSize = 2;
        int vertexSizeBytes = (posSizes + colorSize + uvSize) * Float.BYTES;
        glVertexAttribPointer(0, posSizes, GL_FLOAT, false, vertexSizeBytes, 0);
        glEnableVertexAttribArray(0);

        glVertexAttribPointer(1, colorSize, GL_FLOAT, false, vertexSizeBytes, posSizes * Float.BYTES);
        glEnableVertexAttribArray(1);

        glVertexAttribPointer(2, uvSize, GL_FLOAT, false, vertexSizeBytes, (posSizes + colorSize)*Float.BYTES);
        glEnableVertexAttribArray(2);
    }

    @Override
    public void update(float dt) {
        if (TryConnectJoystick) {
            try {
                if (glfwGetGamepadState(GLFW_JOYSTICK_1, JoystickListener.GetGamepad())) {
                    if (JoystickListener.GetGamepad().axes(GLFW_GAMEPAD_AXIS_RIGHT_X) > .2f || JoystickListener.GetGamepad().axes(GLFW_GAMEPAD_AXIS_RIGHT_X) < -.2f)
                        camera.pos.x -= 100 * dt * JoystickListener.GetGamepad().axes(GLFW_GAMEPAD_AXIS_RIGHT_X);
                    if (JoystickListener.GetGamepad().axes(GLFW_GAMEPAD_AXIS_RIGHT_Y) > .2f || JoystickListener.GetGamepad().axes(GLFW_GAMEPAD_AXIS_RIGHT_Y) < -.2f)
                        camera.pos.y += 100 * dt * JoystickListener.GetGamepad().axes(GLFW_GAMEPAD_AXIS_RIGHT_Y);
                }
            } catch ( NullPointerException e ) {
                System.out.println(e);
                TryConnectJoystick = false;
            }
        }

        defaultShader.use();
        // upload tex to shader
        defaultShader.uploadTexture("TEX_SAMPLER", 0);
        glActiveTexture(GL_TEXTURE0);
        testTexture.bind();

        defaultShader.uploadMat4f("uProjection", camera.getProjectionMatrix());
        defaultShader.uploadMat4f("uView", camera.getViewMatrix());
        defaultShader.uploadFloat("uTime", Time.getTime());
        glBindVertexArray(vaoID);
        // Enable the vertex attrib pointers
        glEnableVertexAttribArray(0);
        glEnableVertexAttribArray(1);

        glDrawElements(GL_TRIANGLES, elementArray.length, GL_UNSIGNED_INT, 0);

        // Unbind everything
        glDisableVertexAttribArray(0);
        glDisableVertexAttribArray(1);

        glBindVertexArray(0);

        defaultShader.detach();

        if (firstTime) {
            System.out.println( "Creating g obj" );
            gObject go = new gObject("game test 2");
            go.addComponents(new SpriteRender());
            this.addGObjectToScene(go);
            firstTime = false;
        }

        for (gObject go : this.gObjects) {
            go.update(dt);
        }
    }
}

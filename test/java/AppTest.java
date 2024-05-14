import com.fe.physic.components.RectCollider;
import com.fe.physic.components.Texture;
import com.fe.physic.components.Transform;
import org.joml.Matrix4f;
import org.joml.Vector3f;
import org.lwjgl.opengl.*;

import static org.lwjgl.glfw.Callbacks.*;
import static org.lwjgl.glfw.GLFW.*;
import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.system.MemoryStack.*;
import static org.lwjgl.system.MemoryUtil.*;

public class AppTest {
    private static Camera cam;
    private static long window = Window.getWindow();
    public void run() {

        // This line is critical for LWJGL's interoperation with GLFW's
        // OpenGL context, or any context that is managed externally.
        // LWJGL detects the context that is current in the current thread,
        // creates the GLCapabilities instance and makes the OpenGL
        // bindings available for use.
        GL.createCapabilities();
        cam = new Camera(new Transform(0,0,600,600,1000,1000));
        glEnable(GL_TEXTURE_2D);
        float width = 1f;
        float height = 1f;
        float x = 0f;
        float y = 0f;
//        float[] vertices = new float[] {
//                -.5f, .5f, 0, // TOP LEFT
//                .5f, .5f, 0, // TOP RIGHT
//                .5f, -.5f, 0, //BOTTOM RIGHT
//
//                .5f, -.5f, 0, //BOTTOM RIGHT
//                -.5f, -.5f, 0, // BOTTOM LEFT
//                -.5f, .5f, 0, // TOP LEFT
//        };
        float[] vertices = new float[] {
                x,       y,        0, // TOP LEFT     0
                x+width, y,        0, // TOP RIGHT    1
                x+width, y-height, 0, // BOTTOM RIGHT 2
                x,       y-height, 0, // BOTTOM LEFT  3
        };
        float[] texture = new float[] {
                0,0,
                1,0,
                1,1,
                0,1
        };
        int[] indices = new int[] {
                0,1,2,
                2,3,0
        };

        Model model = new Model(vertices, texture, indices);
        Shader shader = new Shader("shader");
        RenderTexture test = new RenderTexture("./assets/textures/based_tex.jpg");
        Matrix4f projection = new Matrix4f()
                .ortho2D(-600/2, 600/2, -600/2, 600/2);
        Matrix4f scale = new Matrix4f()
                .translate(new Vector3f(0, 0, 0))
                .scale(80);
        Matrix4f target = new Matrix4f();
        projection.mul(scale, target);

        // Set the clear color
        glClearColor(0.0f, 0.0f, 0.0f, 0.0f);

        // Run the rendering loop until the user has attempted to close
        // the window or has pressed the ESCAPE key.
        glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT); // clear the frame buffer

//            test.bind();
        target = scale;
        shader.bind();
        shader.setUniform("sampler", 0);
        shader.setUniform("projection", cam.getProjection().mul(target));
        test.bind(0);
        model.render();
    }
    public void loop() {
        // Poll for window events. The key callback above will only be
        // invoked during this call.
        if (glfwGetKey(window, GLFW_KEY_F) == GL_TRUE) {
            cam.transform.addX(-50);
            cam.init();
        }
        else if (glfwGetKey(window, GLFW_KEY_G) == GL_TRUE) {
            cam.transform.addX(50);
            cam.init();
        }
        else if (glfwGetKey(window, GLFW_KEY_T) == GL_TRUE) {
            cam.transform.addY(50);
            cam.init();
        }
        else if (glfwGetKey(window, GLFW_KEY_V) == GL_TRUE) {
            cam.transform.addY(-50);
            cam.init();
        }
        else if (glfwGetKey(window, GLFW_KEY_K) == GL_TRUE) {
            System.out.println(cam.transform.getX()+" "+cam.transform.getY());
        }
    }

    public static void main(String[] args) {
        new Window().run();
    }
}
import com.fe.physic.components.Transform;
import org.joml.Matrix4f;
import org.joml.Vector3f;
import org.lwjgl.opengl.*;
import static org.lwjgl.glfw.GLFW.*;
import static org.lwjgl.opengl.GL11.*;

public class AppTest {
    private static Camera cam;
    private static long window;
    public void run() {
        window = Window.getWindow();
        GL.createCapabilities();
        cam = new Camera(new Transform(0,0,600,600,1000,1000));
        glEnable(GL_TEXTURE_2D);
    }
    public void loop() {
        int x=0;
        int y=0;
        Shader shader = new Shader("shader");
        RenderTexture test = new RenderTexture("./assets/textures/based_tex.jpg");
        Matrix4f projection = new Matrix4f()
                .ortho2D(-600/2, 600/2, -600/2, 600/2);
        Matrix4f scale = new Matrix4f()
                .translate(new Vector3f(0, 0, 0))
                .scale(80);
        Matrix4f target = new Matrix4f();
        projection.mul(scale, target);
        glClearColor(0.0f, 0.0f, 0.0f, 0.0f);
        PosTexture texture = new PosTexture();
        glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT);

        while (!glfwWindowShouldClose(window)) {
            glfwSwapBuffers(window); // swap the color buffers
            glfwPollEvents();

            glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT);

            if (glfwGetKey(window, GLFW_KEY_F) == GL_TRUE) {
                x-=1;
            } else if (glfwGetKey(window, GLFW_KEY_G) == GL_TRUE) {
                x+=1;
            } else if (glfwGetKey(window, GLFW_KEY_T) == GL_TRUE) {
                y+=1;
            } else if (glfwGetKey(window, GLFW_KEY_V) == GL_TRUE) {
                y-=1;
            } else if (glfwGetKey(window, GLFW_KEY_K) == GL_TRUE) {
                System.out.println(x + " " + y);
            }

            texture.renderTexture(test, x, y, shader, scale, cam);
        }
    }

    public static void main(String[] args) {
        new Window().run();
    }
}
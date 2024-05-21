import physic.Entity;
import com.fe.physic.components.Transform;
import org.joml.Matrix4f;
import org.joml.Vector3f;
import org.lwjgl.opengl.*;
import physic.Timer;
import render.Camera;
import render.PosTexture;
import render.RenderTexture;
import render.Shader;
import scripts.ScriptsReader;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;

import static org.lwjgl.glfw.GLFW.*;
import static org.lwjgl.opengl.GL11.*;

public class AppTest {
    public static ArrayList<Entity> ents = new ArrayList<>();
    private static Camera cam;
    private static long window;
    private static int FPS = 120;
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
        ScriptsReader scrReader = new ScriptsReader();
        double sec_per_frame = 1.0/FPS;
        double time = Timer.getTime();
        double timer = 0;
        double frame_time=0;
        int frames=0;

        while (!glfwWindowShouldClose(window)) {
            boolean can_render = false;
            double time_2 = Timer.getTime();
            double passed = time_2-time;
            timer+=passed;
            frame_time+=passed;

            time = time_2;

            while (timer >= sec_per_frame) {
                timer-=sec_per_frame;
                can_render = true;

                if (frame_time >= 1) {
                    frame_time=0;
                    System.out.println("FPS: "+frames);
                    frames=0;
                }
            }

            if (can_render) {
                glfwSwapBuffers(window); // swap the color buffers
                glfwPollEvents();
                try {
                    scrReader.runUpdateInSCRs(0f);
                } catch (ClassNotFoundException | NoSuchMethodException | InvocationTargetException |
                         IllegalAccessException | InstantiationException e) {
                    throw new RuntimeException(e);
                }

                glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT);
//                texture.renderTexture(testent.texture, testent.transform.getX(), testent.transform.getY(), shader, scale, cam);
                for (Entity ent : ents) {
                    texture.renderTexture(ent.texture, ent.transform.getX(), ent.transform.getY(), shader, scale, cam);
                }
                frames++;
            }
        }
    }

    public static boolean isPressed(int key) {
        return glfwGetKey(window, key) == GL_TRUE;
    }

    public static void main(String[] args) {
        new Window().run();
    }
}
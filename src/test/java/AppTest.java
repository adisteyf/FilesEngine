import com.fe.physic.components.Transform;
import org.joml.Matrix4f;
import org.joml.Vector3f;
import org.lwjgl.opengl.*;
import physic.Timer;
import render.Camera;
import render.PosTexture;
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
    private static int FPS = 60;
    public static boolean canRender = true;
    public double sec_per_frame = 1.0/FPS;
    public void run() {
        window = Window.getWindow();
        GL.createCapabilities();
        cam = new Camera(new Transform(0,0,600,600,1000,1000));
        glEnable(GL_TEXTURE_2D);
    }
    public void loop() {
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
        double start = Timer.getTime();
        double end = Timer.getTime();
        double dt = 0;

        while (!glfwWindowShouldClose(window)) {
            glfwPollEvents();
            if (canRender) {
                start = end;
                glfwSwapBuffers(window); // swap the color buffer
                try {
                    scrReader.runUpdateInSCRs((float) dt);
                } catch (ClassNotFoundException | NoSuchMethodException | InvocationTargetException |
                         IllegalAccessException | InstantiationException e) {
                    throw new RuntimeException(e);
                }

                glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT);
                //                texture.renderTexture(testent.texture, testent.transform.getX(), testent.transform.getY(), shader, scale, cam);
                for (Entity ent : ents) {
                    texture.renderTexture(ent.texture, ent.transform.getX(), ent.transform.getY(), shader, scale, cam);
                }
                end = Timer.getTime();
                dt = (end - start)/sec_per_frame;
                canRender=false;
            }
            if (Timer.getTime() > start+sec_per_frame) {
                canRender=true;
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
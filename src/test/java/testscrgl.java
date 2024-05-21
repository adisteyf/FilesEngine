import com.fe.physic.components.FilesScripts;
import com.fe.physic.components.Transform;
import physic.Entity;
import render.RenderTexture;

import static org.lwjgl.glfw.GLFW.*;
import static org.lwjgl.opengl.GL11.GL_TRUE;

public class testscrgl extends FilesScripts {

    @Override
    public void start() {
        System.out.println("starting...");
        RenderTexture test = new RenderTexture("./assets/textures/based_tex.jpg");
        Entity testent = new Entity(test, new Transform(0,0,100,100,1000,1000), "testent");
        EntityScripts.entityCreate(testent);
    }

    @Override
    public void update(float dt) {
        //System.out.println("cycle!");
        if (AppTest.isPressed(GLFW_KEY_F)) {
            EntityScripts.getEntityByName("testent").transform.addX(-1);
        } else if (AppTest.isPressed(GLFW_KEY_G)) {
            EntityScripts.getEntityByName("testent").transform.addX(1);
        } else if (AppTest.isPressed(GLFW_KEY_T)) {
            EntityScripts.getEntityByName("testent").transform.addY(1);
        } else if (AppTest.isPressed(GLFW_KEY_V)) {
            EntityScripts.getEntityByName("testent").transform.addY(-1);
        } else if (AppTest.isPressed(GLFW_KEY_K)) {
            System.out.println(EntityScripts.getEntityByName("testent").transform.getX() + " " + EntityScripts.getEntityByName("testent").transform.getY());
        }
    }
}

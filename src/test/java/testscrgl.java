import com.fe.physic.components.FilesScripts;
import render.RenderTexture;

import java.io.File;

import static org.lwjgl.glfw.GLFW.*;

public class testscrgl extends FilesScripts {

    @Override
    public void start() {
        System.out.println("starting...");
        RenderTexture test = new RenderTexture("./assets/textures/based_tex.jpg");

        // Creating entities
        Entity testent = new Entity(test, new Transform(0,0,100,100,1000,1000), "testent");
        EntityScripts.entityCreate(testent);
        Entity testwall = new Entity(test, new Transform(1.5f,1.5f,100,100,1000,1000), "testwall");
        EntityScripts.entityCreate(testwall);

        // adding components
        RectCollider testcol = new RectCollider(0,0,1,1);
        RectCollider colwall = new RectCollider(0,0,1,1);
//        testwall.transform.rectCollider.ent = testwall;
//        testent.transform.rectCollider.ent = testent;
        SceneLoader.readScene(new File("assets/sample.lvl"));

        EntityScripts.getEntityByName("test").transform.setX(0);
        EntityScripts.getEntityByName("test").transform.setY(0);
        EntityScripts.getEntityByName("wall_test").transform.setX(1.5f);
        EntityScripts.getEntityByName("wall_test").transform.setY(1.5f);

        EntityScripts.getEntityByName("test").addComponent(testcol);
        EntityScripts.getEntityByName("wall_test").addComponent(colwall);
        EntityScripts.getEntityByName("test").transform.rectCollider = testcol;
        EntityScripts.getEntityByName("wall_test").transform.rectCollider = colwall;
        EntityScripts.initAll();
//        System.out.println(AppTest.ents);
//        System.out.println(EntityScripts.getEntityByName("wall_test").transform.getX());
//        System.out.println(EntityScripts.getEntityByName("test").transform.getX());
    }

    @Override
    public void update(float dt) {
        System.out.println(EntityScripts.getEntityByName("wall_test").transform.rectCollider.posX);
        System.out.println(EntityScripts.getEntityByName("wall_test").transform.rectCollider.posY);
        System.out.println(EntityScripts.getEntityByName("test").transform.rectCollider.posX);
        System.out.println(EntityScripts.getEntityByName("test").transform.rectCollider.posY);
        EntityScripts.getEntityByName("test").transform.rectCollider.update();
        EntityScripts.getEntityByName("wall_test").transform.rectCollider.update();
        if (AppTest.isPressed(GLFW_KEY_F)) {
            EntityScripts.getEntityByName("test").transform.addX(-.100f*dt);
        } else if (AppTest.isPressed(GLFW_KEY_G)) {
            EntityScripts.getEntityByName("test").transform.addX(.100f*dt);
        } else if (AppTest.isPressed(GLFW_KEY_T)) {
            EntityScripts.getEntityByName("test").transform.addY(.100f*dt);
        } else if (AppTest.isPressed(GLFW_KEY_V)) {
            EntityScripts.getEntityByName("test").transform.addY(-.100f*dt);
        } else if (AppTest.isPressed(GLFW_KEY_K)) {
            System.out.println(EntityScripts.getEntityByName("test").transform.getX() + " " + EntityScripts.getEntityByName("test").transform.getY());
        }
    }
}

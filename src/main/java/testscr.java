import com.fe.graphics.GamePanel;
import com.fe.listener.KeyHandler;
import com.fe.physic.ColliderScripts;
import com.fe.physic.Entity;
import com.fe.physic.EntityScripts;
import com.fe.physic.components.FilesScripts;
import com.fe.physic.components.RectCollider;
import com.fe.physic.components.Texture;
import com.fe.physic.components.Transform;
import com.fe.scene.SceneLoader;
import com.fe.scene.SceneSaver;

import java.io.File;
import static java.awt.event.KeyEvent.*;

public class testscr extends FilesScripts {
//    Entity test_ent;
    File file = new File("./assets", "sample.lvl");
    @Override
    public void start() {
        System.out.println("Starting testscr...");
//        EntityScripts.entityCreate(new Entity(new Texture(null, new Transform(10.f, 10.f, 20.f ,20.f)),
//                new Transform(10.f, 10.f, 20.f ,20.f), "test"));
//        EntityScripts.getEntityByName("test").texture.setImg("assets/textures/based_tex.jpg");
//        File file2 = new File("./assets", "sample2.lvl");
        SceneLoader.readScene(file);
        GamePanel.saveVar.add(true);

        EntityScripts.entityCreate(new Entity(new Texture(null, new Transform(20.f, 20.f, 100.f ,100.f, 500, 500, null),
                "assets/textures/based_tex.jpg"),
                new Transform(20.f, 20.f, 20.f ,20.f, 500, 500, null), "wall_test"));
        EntityScripts.getEntityByName("wall_test").transform.ent = EntityScripts.getEntityByName("wall_test");
        EntityScripts.getEntityByName("test").transform.ent = EntityScripts.getEntityByName("test");

        RectCollider col_for_test = new RectCollider(0,0,100f,100f);
        RectCollider col_for_test_wall = new RectCollider(0,0,100f,100f);

        EntityScripts.getEntityByName("wall_test").texture.setImg("assets/textures/based_tex.jpg");

        EntityScripts.getEntityByName("test").addComponent(col_for_test);
        EntityScripts.getEntityByName("wall_test").addComponent(col_for_test_wall);
        EntityScripts.getEntityByName("test").transform.rectCollider = col_for_test;
        EntityScripts.getEntityByName("wall_test").transform.rectCollider = col_for_test_wall;
        EntityScripts.initAll();
        Runnable update = () -> {
            while (true) {
                col_for_test.update();
                col_for_test_wall.update();
            }
        };

        Thread thread = new Thread(update);
        thread.start();
    }

    @Override
    public void update(float dt) {
        if (KeyHandler.getKey(0)) {
            EntityScripts.getEntityByName("test").transform.addY( -200*dt );
        }
        if (KeyHandler.getKey(1)) {
            EntityScripts.getEntityByName("test").transform.addY( 200*dt );
        }
        if (KeyHandler.getKey(2)) {
            EntityScripts.getEntityByName("test").transform.addX( -200*dt );
        }
        if (KeyHandler.getKey(3)) {
            EntityScripts.getEntityByName("test").transform.addX( 200*dt );
        }

        if (KeyHandler.getKey(4)) {
            if (GamePanel.saveVar.get(0) == (Object) true) {
                GamePanel.saveVar.set(0, false);
                System.out.println("J!");
                SceneSaver.saveScene(file, 500, 500);
            }
        }

//        if (ColliderScripts.isCollide(EntityScripts.getEntityByName("test").getComponent(RectCollider.class),
//                EntityScripts.getEntityByName("wall_test").getComponent(RectCollider.class))) {
//            System.out.println("this work!");
//        }
    }
}

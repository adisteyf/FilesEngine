import com.fe.listener.KeyHandler;
import com.fe.physic.Entity;
import com.fe.physic.EntityScripts;
import com.fe.physic.components.FilesScripts;
import com.fe.scene.SceneLoader;
import com.fe.scene.SceneSaver;

import java.io.File;

import static java.awt.event.KeyEvent.*;

public class testscr extends FilesScripts {
    Entity test_ent;
    @Override
    public void start() {
        System.out.println("Starting testscr");
//        EntityScripts.entityCreate(new Entity(new Texture(null, new Transform(10.f, 10.f, 20.f ,20.f)),
//                new Transform(10.f, 10.f, 20.f ,20.f), "test"));
//        EntityScripts.getEntityByName("test").texture.setImg("assets/textures/based_tex.jpg");
        File file = new File("./assets", "sample.lvl");
        SceneLoader.readScene(file);
        File file2 = new File("./assets", "sample2.lvl");
        SceneSaver.saveScene(file2, 500, 500);
    }

    @Override
    public void update(float dt) {
        if (KeyHandler.getKey(0)) {
            EntityScripts.getEntityByName("test").transform.addY( -100*dt );
        }
        if (KeyHandler.getKey(1)) {
            EntityScripts.getEntityByName("test").transform.addY( 100*dt );
        }
        if (KeyHandler.getKey(2)) {
            EntityScripts.getEntityByName("test").transform.addX( -100*dt );
        }
        if (KeyHandler.getKey(3)) {
            EntityScripts.getEntityByName("test").transform.addX( 100*dt );
        }

        if (KeyHandler.getKey(4)) {
            System.out.println("J!");
        }
    }
}

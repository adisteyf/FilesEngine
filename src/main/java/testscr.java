import com.fe.graphics.GamePanel;
import com.fe.listener.KeyHandler;
import com.fe.physic.Entity;
import com.fe.physic.EntityScripts;
import com.fe.physic.components.FilesScripts;
import com.fe.scene.SceneLoader;
import com.fe.scene.SceneSaver;

import java.io.File;

import static java.awt.event.KeyEvent.*;

public class testscr extends FilesScripts {
//    Entity test_ent;
    File file = new File("./assets", "sample.lvl");
    @Override
    public void start() {
        System.out.println("Starting testscr");
//        EntityScripts.entityCreate(new Entity(new Texture(null, new Transform(10.f, 10.f, 20.f ,20.f)),
//                new Transform(10.f, 10.f, 20.f ,20.f), "test"));
//        EntityScripts.getEntityByName("test").texture.setImg("assets/textures/based_tex.jpg");
//        File file2 = new File("./assets", "sample2.lvl");
        SceneLoader.readScene(file);
        GamePanel.saveVar.add(true);
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
    }
}

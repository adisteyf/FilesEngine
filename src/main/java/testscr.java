import graphics.GamePanel;
import listener.KeyHandler;
import physic.Entity;
import physic.EntityScripts;
import physic.components.FilesScripts;
import physic.components.Texture;
import physic.components.Transform;
import scene.TileSceneLoader;

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
        TileSceneLoader.readScene(file);
    }

    @Override
    public void update(float dt) {
        if (KeyHandler.getKey(0)) {
            EntityScripts.getEntityByName("test").transform.y -= 100*dt;
        }
        if (KeyHandler.getKey(1)) {
            EntityScripts.getEntityByName("test").transform.y += 100*dt;
        }
        if (KeyHandler.getKey(2)) {
            EntityScripts.getEntityByName("test").transform.x -= 100*dt;
        }
        if (KeyHandler.getKey(3)) {
            EntityScripts.getEntityByName("test").transform.x += 100*dt;
        }
    }
}

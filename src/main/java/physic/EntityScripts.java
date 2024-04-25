package physic;

import graphics.GamePanel;

import java.util.ArrayList;

public class EntityScripts {
    public static Entity getEntityByName(String name) {
        ArrayList<Entity> entities = GamePanel.entities;
        for (Entity entity : entities) {
            if (entity.name.equals(name)) {
                return entity;
            }
        }
        return null;
    }
    public static void entityCreate(Entity entity) {
        GamePanel.entities.add(entity);
    }
    public static void entityRemove(Entity entity) {
        GamePanel.entities.remove(entity);
    }
}

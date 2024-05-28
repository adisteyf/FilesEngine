import java.util.ArrayList;

public class EntityScripts {
    public static Entity getEntityByName(String name) {
        ArrayList<Entity> entities = App.ents;
        for (Entity entity : entities) {
            if (entity.name.equals(name)) {
                return entity;
            }
        }
        return null;
    }
    public static void entityCreate(Entity entity) {
        App.ents.add(entity);
        initAll();
    }
    public static void entityRemove(Entity entity) {
        App.ents.remove(entity);
        initAll();
    }

    public static void initAll() {
        for (Entity ent : App.ents) {
            ent.transform.init();
        }
    }
}

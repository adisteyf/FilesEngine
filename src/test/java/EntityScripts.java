import java.util.ArrayList;

public class EntityScripts {
    public static Entity getEntityByName(String name) {
        ArrayList<Entity> entities = AppTest.ents;
        for (Entity entity : entities) {
            if (entity.name.equals(name)) {
                return entity;
            }
        }
        return null;
    }
    public static void entityCreate(Entity entity) {
        AppTest.ents.add(entity);
        initAll();
    }
    public static void entityRemove(Entity entity) {
        AppTest.ents.remove(entity);
        initAll();
    }

    public static void initAll() {
        for (Entity ent : AppTest.ents) {
            ent.transform.init();
        }
    }
}

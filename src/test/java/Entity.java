import render.RenderTexture;
import java.util.ArrayList;

public class Entity {
    public Transform transform;
    public RenderTexture texture;
    public String name;
    ArrayList<ComponentStruct> comps = new ArrayList<>();

    public Entity(RenderTexture texture, Transform transform, String name) {
        this.transform = transform;
        this.texture = texture;
        this.name = name;
    }

    public <T extends ComponentStruct> T getComponent(Class<T> compClass) {
        for (ComponentStruct c : comps) {
            if (compClass.isAssignableFrom(c.getClass())) {
                try {
                    return compClass.cast(c);
                } catch (ClassCastException e) {
                    e.printStackTrace();
                    assert false : "Error from Entity.getComponent()!";
                }
            }
        }
        return null;
    }

    public void addComponent(ComponentStruct c) {
        this.comps.add(c);
        c.ent = this;
    }

    public <T extends ComponentStruct> void rmComponent(Class<T> compClass) {
        for (int i = 0; i < comps.size(); i++) {
            ComponentStruct c = comps.get(i);
            if (compClass.isAssignableFrom(c.getClass())) {
                comps.remove(i);
                return;
            }
        }
    }
}

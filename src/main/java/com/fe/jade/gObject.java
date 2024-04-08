package com.fe.jade;

import java.util.ArrayList;
import java.util.List;

public class gObject {

    private String name;
    private List<Component> components;
    public gObject(String name) {
        this.name = name;
        this.components = new ArrayList<>();
    }

    public <T extends Component> T getComponent(Class<T> componentCls) {
        for (Component c : components) {
            if (componentCls.isAssignableFrom(c.getClass())) {
                try {
                    return componentCls.cast(c);
                }
                catch (ClassCastException e) {
                    e.printStackTrace();
                    assert false : "ERR: CASTING COMPONENT!";
                }
            }
        }

        return null;
    }

    public <T extends Component> void remComponent(Class<T> componentCls) {
        for (int i=0; i < components.size(); i++) {
            Component c = components.get(i);
            if (componentCls.isAssignableFrom(components.getClass())) {
                components.remove(i);
                return;
            }
        }
    }

    public void addComponents(Component c) {
        this.components.add(c);
        c.gameObj = this;
    }

    public void update(float dt) {
        for (int i=0; i < components.size(); i++) {
            components.get(i).update(dt);
        }
    }

    public void start() {
        for (int i=0; i < components.size(); i++) {
            components.get(i).start();
        }
    }
}

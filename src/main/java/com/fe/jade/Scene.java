package com.fe.jade;

import java.util.ArrayList;
import java.util.List;

public abstract class Scene {
    protected Camera camera;
    private boolean isRunning = false;
    protected List<gObject> gObjects = new ArrayList<>();
    public Scene() {

    }

    public void init() {

    }
    public void start() {
        for (gObject go : gObjects) {
            go.start();
        }
        isRunning = true;
    }
    public void addGObjectToScene(gObject go) {
        if (!isRunning) {
            gObjects.add(go);
        } else {
            gObjects.add(go);
            go.start();
        }
    }
    public abstract void update(float dt);
}

package com.fe.jade;

import com.fe.render.Render;

import java.util.ArrayList;
import java.util.List;

public abstract class Scene {
    protected Render render = new Render();
    protected Camera cam;
    private boolean isRunning = false;
    protected List<gObject> gObjects = new ArrayList<>();
    public Scene() {

    }

    public void init() {

    }
    public void start() {
        for (gObject go : gObjects) {
            go.start();
            this.render.add(go);
        }
        isRunning = true;
    }
    public void addGObjectToScene(gObject go) {
        if (!isRunning) {
            gObjects.add(go);
        } else {
            gObjects.add(go);
            go.start();
            this.render.add(go);
        }
    }
    public abstract void update(float dt);

    public Camera cam() {
        return this.cam;
    }
}

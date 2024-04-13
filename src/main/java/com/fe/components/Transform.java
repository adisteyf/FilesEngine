package com.fe.components;

import org.joml.Vector2f;

public class Transform {
    public Vector2f pos;
    public Vector2f scale;

    public Transform() {
        init(new Vector2f(), new Vector2f());
    }

    public Transform(Vector2f pos) {
        init(pos, new Vector2f());
    }

    public Transform(Vector2f pos, Vector2f scale) {
        init(pos, scale);
    }

    public void init(Vector2f pos, Vector2f scale) {
        this.pos = pos;
        this.scale = scale;
    }
}

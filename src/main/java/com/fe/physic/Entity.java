package com.fe.physic;

import com.fe.physic.components.Texture;
import com.fe.physic.components.Transform;

import java.awt.*;
import java.awt.image.BufferedImage;

public class Entity {
    public Transform transform;
    public Texture texture;
    public String name;

    public Entity(Texture texture, Transform transform, String name) {
        this.transform = transform;
        this.texture = texture;
        this.name = name;
    }
}

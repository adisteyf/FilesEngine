package com.fe.physic.components;

import java.util.ArrayList;

public class Animation {
    public int frames;
    public ArrayList<Texture> textures = new ArrayList<>();
    public Animation(ArrayList<Texture> textures) {
        this.textures = textures;
    }
    public Texture getFrame(int frames) {
        return textures.get(frames);
    }
}

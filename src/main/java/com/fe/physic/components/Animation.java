package com.fe.physic.components;

import com.fe.physic.ComponentStruct;
import com.fe.physic.Entity;

import java.util.ArrayList;

public class Animation extends ComponentStruct {
    public ArrayList<Texture> frames;
    public int framesPerSecond;
    int timeForFrame;
    int currentFrame = 0;

    public Animation(ArrayList<Texture> textures, int framesPerSecond) {
        this.frames = textures;
        this.framesPerSecond = framesPerSecond;
        timeForFrame = (int) (1000f / framesPerSecond);
    }
    public Texture getFrame(int index) {
        if (index < frames.size())
            return frames.get(index);
        return null;
    }
    public void removeFrame(int index) {
        if (index < frames.size())
            frames.remove(index);
    }
    public void removeAllFrames() {
        frames.clear();
    }
    public void addFrame(Texture frame) {
        frames.add(frame);
    }
    public void addFrame(Texture frame, int index) {
        if (index < frames.size())
            frames.add(index, frame);
    }
    public void replaceFrame(Texture frame, int index) {
        if (index < frames.size()) {
            removeFrame(index);
            addFrame(frame, index);
        }
    }

    public void update(float dt) {
        // RUN THIS IN CYCLE!
        if (currentFrame >= frames.size())
            currentFrame = 0;

        ent.texture = frames.get(currentFrame);

        try {
            Thread.sleep(timeForFrame);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        currentFrame++;
        // TODO: Протестить этот компонент.
    }
}

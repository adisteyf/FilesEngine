package com.fe.physic.components;

import com.fe.physic.ComponentStruct;
import com.fe.physic.Entity;

import java.util.ArrayList;

public class Trigger extends ComponentStruct {
    float posXoffset, posYoffset;
    float posX, posY;
    float sizeX, sizeY;
    public float[] TLdot, TRdot, DLdot, DRdot;
    public float[][] dots;

    public Trigger(float posXoffset, float posYoffset, float sizeX, float sizeY) {
        this.posXoffset = posXoffset;
        this.posYoffset = posYoffset;
        this.sizeX = sizeX;
        this.sizeY = sizeY;
        TLdot = new float[] {posX, posY};
        TRdot = new float[] {posX+sizeX, posY};
        DLdot = new float[] {posX, posY+sizeY};
        DRdot = new float[] {posX+sizeX, posY+sizeY};
        dots = new float[][] {TLdot, TRdot, DLdot, DRdot};
    }

    public void update() {
        posX = ent.transform.getX() + posXoffset;
        posY = ent.transform.getY() + posYoffset;

        TLdot = new float[] {posX, posY};
        TRdot = new float[] {posX+sizeX, posY};
        DLdot = new float[] {posX, posY+sizeY};
        DRdot = new float[] {posX+sizeX, posY+sizeY};
        dots = new float[][] {TLdot, TRdot, DLdot, DRdot};
    }
}

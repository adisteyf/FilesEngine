package com.fe.physic.components;

public class RectCollider {
    float posX, posY;
    float sizeX, sizeY;
    public float[] TLdot, TRdot, DLdot, DRdot;
    public float[][] dots;

    public RectCollider(float posX, float posY, float sizeX, float sizeY) {
        this.posX = posX;
        this.posY = posY;
        this.sizeX = sizeX;
        this.sizeY = sizeY;
        TLdot = new float[] {posX, posY};
        TRdot = new float[] {posX+sizeX, posY};
        DLdot = new float[] {posX, posY+sizeY};
        DRdot = new float[] {posX+sizeX, posY+sizeY};
        dots = new float[][] {TLdot, TRdot, DLdot, DRdot};
    }
}

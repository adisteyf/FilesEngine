package com.fe.physic.components;

public class Transform {
    private float x, y;
    private float x_limit, y_limit;
    public float sizeX, sizeY;
    public Transform(float x, float y, float sizeX, float sizeY, float x_limit, float y_limit) {
        this.x = x;
        this.y = y;
        this.sizeX = sizeX;
        this.sizeY = sizeY;
        this.x_limit = x_limit;
        this.y_limit = y_limit;
    }
    public float getX() {
        return x;
    }
    public float getY() {
        return y;
    }
    public void setX(float x) {
        if (x < x_limit)
            this.x = x;
    }
    public void setY(float y) {
        if (y < y_limit)
            this.y = y;
    }
    public void addX(float x) {
        if (this.x+x < x_limit)
            this.x += x;
    }
    public void addY(float y) {
        if (this.y+y < y_limit)
            this.y += y;
    }
}

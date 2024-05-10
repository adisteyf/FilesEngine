package com.fe.physic.components;

import com.fe.graphics.GamePanel;
import com.fe.physic.ColliderScripts;
import com.fe.physic.ComponentStruct;
import com.fe.physic.Entity;
import java.lang.Math;
import java.util.ArrayList;

public class Transform {
    private float x, y;
    private float x_limit, y_limit;
    public float sizeX, sizeY;
    public RectCollider rectCollider = null;
    ArrayList<RectCollider> rc = new ArrayList<>();
    public Transform(float x, float y, float sizeX, float sizeY, float x_limit, float y_limit) {
        this.x = x;
        this.y = y;
        this.sizeX = sizeX;
        this.sizeY = sizeY;
        this.x_limit = x_limit;
        this.y_limit = y_limit;

        for (Entity ent_in_gp : GamePanel.entities) {
            if (ent_in_gp.getComponent(RectCollider.class) != null) {
                rc.add(ent_in_gp.getComponent(RectCollider.class));
            }
        }
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
        if (this.x+x < x_limit && rectCollider == null)
            this.x += x;
        else if (rectCollider != null) {
            boolean HorOrVer = false; // true = horizontal, false = vertical
            boolean blockSide = true; // for x: true = right, false = left for y: true = top, false = down
            boolean isCollide = false;
            float sub_x;
            float sub_y;
            for (RectCollider rect_col : rc) {
                if (ColliderScripts.isCollide(rect_col, rectCollider) && !rect_col.equals(rectCollider)) {
                    isCollide=true;
                    sub_x = rect_col.posX - rectCollider.posX;
                    sub_y = rect_col.posY - rectCollider.posY;
                    if (Math.abs(sub_x) >= Math.abs(sub_y)) {
                        HorOrVer = true;
                        if (sub_x > 0)
                            blockSide = true;
                        if (sub_x < 0)
                            blockSide = false;
                    }
                    if (Math.abs(sub_x) < Math.abs(sub_y)) {
                        HorOrVer = false;
                    }
                }
            }
            if (!isCollide || !HorOrVer) {
                this.x += x;
            } else if (!blockSide && x > 0 || blockSide && x < 0) {
                this.x += x;
            }
        }
    }
    public void addY(float y) {
        if (this.y + y < y_limit && rectCollider == null)
            this.y += y;
        else if (rectCollider != null) {
            boolean HorOrVer = true;
            boolean blockSide = false;
            boolean isCollide = false;
            float sub_x;
            float sub_y;
            for (RectCollider rect_col : rc) {
                if (ColliderScripts.isCollide(rect_col, rectCollider) && !rect_col.equals(rectCollider)) {
                    isCollide = true;
                    sub_x = rect_col.posX - rectCollider.posX;
                    sub_y = rect_col.posY - rectCollider.posY;
                    if (Math.abs(sub_x) > Math.abs(sub_y)) {
                        HorOrVer = true;
                    }
                    if (Math.abs(sub_x) <= Math.abs(sub_y)) {
                        HorOrVer = false;
                        if (sub_y > 0)
                            blockSide = true;
                        if (sub_y < 0)
                            blockSide = false;
                    }
                }
            }
            if (!isCollide || HorOrVer) {
                this.y += y;
            } else if (!blockSide && y > 0 || blockSide && y < 0) {
                this.y += y;
            }
        }
    }
    public void init() {
        rc.clear();
        for (Entity ent : GamePanel.entities) {
            if (ent.getComponent(RectCollider.class) != null) {
                rc.add(ent.getComponent(RectCollider.class));
            }
        }
    }
}

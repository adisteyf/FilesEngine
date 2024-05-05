package com.fe.physic.components;

import com.fe.graphics.GamePanel;
import com.fe.physic.ColliderScripts;
import com.fe.physic.Entity;

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

        for (Entity ent : GamePanel.entities) {
            if (ent.getComponent(RectCollider.class) != null) {
                rc.add(ent.getComponent(RectCollider.class));
            }
        }
    }
    public Transform(float x, float y, float sizeX, float sizeY, float x_limit, float y_limit, RectCollider rectCollider) {
        this.x = x;
        this.y = y;
        this.sizeX = sizeX;
        this.sizeY = sizeY;
        this.x_limit = x_limit;
        this.y_limit = y_limit;
        this.rectCollider = rectCollider;

        for (Entity ent : GamePanel.entities) {
            if (ent.getComponent(RectCollider.class) != null) {
                rc.add(ent.getComponent(RectCollider.class));
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
//            boolean skipOne = true;
            boolean addCord = true;
            RectCollider rectCollider_check = rectCollider;
            rectCollider_check.posX -= 1;
//            System.out.println(rc);
            for (RectCollider rect_col : rc) {
//                System.out.println(rc);
                if (ColliderScripts.isCollide(rect_col, rectCollider_check)) {
//                    if (skipOne) {
//                        skipOne=false;
//                        System.out.println("test!!!2");} else {
                    addCord=false;
//                    }
                } // TODO: Пофиксить коллайдер добавив ему 4 стороны для определения с какой стороны движение и разрешить противоположное.
            }
            if (addCord) {
                this.x += x;
            }
        }
    }
    public void addY(float y) {
        if (this.y+y < y_limit && rectCollider == null)
            this.y += y;
        else if (rectCollider != null) {
            boolean skipOne = true;
            boolean addCord = true;
            for (RectCollider rect_col : rc) {
                if (ColliderScripts.isCollide(rect_col, rectCollider)) {
                    if (skipOne) {
                        skipOne=false;
                    } else {
                        addCord=false;
                    }
                }
            }
            if (addCord) {
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

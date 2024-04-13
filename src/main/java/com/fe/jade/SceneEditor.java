package com.fe.jade;
/*
----------------------------------------
-  Credits: Adisteyf (adk.)            -
-  GitHub: https://github.com/adisteyf -
-  For: Shawarma Team (link below)     -
- https://discord.gg/invite/8PrbHxjwxv -
----------------------------------------
*/

import com.fe.components.SpriteRender;
import com.fe.components.Transform;
import com.fe.render.Render;
import org.joml.Vector2f;
import org.joml.Vector4f;

public class SceneEditor extends Scene {
    public SceneEditor() {

    }

    @Override
    public void init() {
        this.cam = new Camera(new Vector2f(-250, 0));

        int xOffset = 10;
        int yOffset = 10;

        float totalWidth = (float) (600-xOffset*2);
        float totalHeight = (float) (300-yOffset*2);
        float sizeX = totalWidth / 100.0f;
        float sizeY = totalHeight / 100.0f;
        float padding = 3;

        for (int x = 0; x < 100; x++) {
            for (int y = 0; y < 100; y++) {
                float xPos = xOffset + (x*sizeX) + (padding*x);
                float yPos = yOffset + (y*sizeY) + (padding*y);

                gObject go = new gObject( "Obj"+x + y, new Transform( new Vector2f(xPos, yPos), new Vector2f(sizeX, sizeY) ) );
                go.addComponents(new SpriteRender(new Vector4f(xPos/totalWidth, yPos/totalHeight,1,1)));
                this.addGObjectToScene(go);
            }
        }
    }

    @Override
    public void update(float dt) {
        for (gObject go : this.gObjects) {
            go.update(dt);
        }

        System.out.println( "from SceneEditor" );
        this.render.render();
    }
}

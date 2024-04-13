package com.fe.render;

import com.fe.components.SpriteRender;
import com.fe.jade.gObject;

import java.util.ArrayList;
import java.util.List;

public class Render {
    private final int MAX_BATCH_SIZE = 1000;
    private List<RenderBatch> batches;

    public Render() {
        this.batches = new ArrayList<>();
    }

    public void add(gObject go) {
        SpriteRender spr = go.getComponent(SpriteRender.class);
        if (spr != null) {
            add(spr);
        }
    }

    private void add(SpriteRender spr) {
        boolean added  = false;
        for (RenderBatch batch: batches) {
            if (batch.hasRoom()) {
                batch.addSprites(spr);
                added = true;
                break;
            }
        }
        if (!added) {
            RenderBatch newBatch = new RenderBatch(MAX_BATCH_SIZE);
            newBatch.start();
            batches.add(newBatch);
            newBatch.addSprites(spr);
        }
    }

    public void render() {
        System.out.println( "From Render" );
        for (RenderBatch batch : batches) {
            System.out.println( "rendering..." );
            batch.render();
        }
    }
}

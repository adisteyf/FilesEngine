package com.fe.render;

import com.fe.components.SpriteRender;
import com.fe.jade.Window;
import org.joml.Vector4f;

import static org.lwjgl.opengl.GL15.*;
import static org.lwjgl.opengl.GL20.glDisableVertexAttribArray;
import static org.lwjgl.opengl.GL20.glEnableVertexAttribArray;
import static org.lwjgl.opengl.GL20C.glVertexAttribPointer;
import static org.lwjgl.opengl.GL30.glBindVertexArray;
import static org.lwjgl.opengl.GL30.glGenVertexArrays;

public class RenderBatch {
    private final int POS_SIZE = 2;
    private final int  COLOR_SIZE = 4;
    private final int POS_OFFSET = 0;
    private final int COLOR_OFFSET = POS_OFFSET + POS_SIZE * Float.BYTES;
    private final int VERTEX_SIZE = 6;
    private final int VERTEX_SIZE_BYTES = VERTEX_SIZE * Float.BYTES;

    private SpriteRender[] sprites;
    private int numSprites;
    private boolean hasRoom;
    private float[] vertices;
    private int vaoID, vboID;
    private int maxBatchSize;
    private Shader shader;
    public RenderBatch(int maxBatchSize) {
        shader = new Shader( "assets/shaders/default.glsl" );
        shader.compile();
        this.sprites = new SpriteRender[maxBatchSize];
        this.maxBatchSize = maxBatchSize;

        // RGBA (4) vertices
        vertices = new float[maxBatchSize*4*VERTEX_SIZE];

        this.numSprites = 0;
        this.hasRoom = true;
    }

    public void start() {
        // gen Vertex Array obj
        vaoID = glGenVertexArrays();
        glBindVertexArray(vaoID);

        vboID = glGenBuffers();
        glBindBuffer(GL_ARRAY_BUFFER, vboID);
        glBufferData(GL_ARRAY_BUFFER, (long) vertices.length*Float.BYTES, GL_DYNAMIC_DRAW);

        // create & upload buffer
        int eboID = glGenBuffers();
        int[] indices = genIndices();
        glBindBuffer(GL_ELEMENT_ARRAY_BUFFER, eboID);
        glBufferData(GL_ELEMENT_ARRAY_BUFFER, indices, GL_STATIC_DRAW);

        // Enable buffer attrib pointers
        glVertexAttribPointer(0, POS_SIZE, GL_FLOAT, false, VERTEX_SIZE_BYTES, POS_OFFSET);
        glEnableVertexAttribArray(0);
        glVertexAttribPointer(1, COLOR_SIZE, GL_FLOAT, false, VERTEX_SIZE_BYTES, COLOR_OFFSET);
        glEnableVertexAttribArray(1);
    }

    public void addSprites(SpriteRender spr) {
        System.out.println(this.numSprites + ": 1");
        int index = this.numSprites;
        this.sprites[index] = spr;
        this.numSprites++;
        System.out.println(this.numSprites + ": 2");

        loadVertexProperties(index);

        if (numSprites >= this.maxBatchSize) {
            System.out.println(this.numSprites + ": 3");
            this.hasRoom = false;
        }
    }

    public void render() {
        // переделываем всю дату каждый кадр
        glBindBuffer(GL_ARRAY_BUFFER, vboID);
        glBufferSubData(GL_ARRAY_BUFFER, 0, vertices);
        // юзаем шейдер
        shader.use();
        shader.uploadMat4f("uProjection", Window.getScene().cam().getProjectionMatrix());
        shader.uploadMat4f("uView", Window.getScene().cam().getViewMatrix());
        glBindVertexArray(vaoID);
        glEnableVertexAttribArray(0);
        glEnableVertexAttribArray(1);

        System.out.println(this.numSprites + ": render");
        glDrawElements(GL_TRIANGLES, this.numSprites*6, GL_UNSIGNED_INT, 0);

        glDisableVertexAttribArray(0);
        glDisableVertexAttribArray(1);
        glBindVertexArray(0);
        shader.detach();
    }

    private void loadVertexProperties(int index) {
        SpriteRender spr = this.sprites[index];
        int offset = index * 4 * VERTEX_SIZE;
        Vector4f color = spr.getColor();

        float xAdd = 1.0f;
        float yAdd = 1.0f;
        for (int i = 0; i < 4; i++) {
            if (i==1)
                yAdd = 0.0f;
            else if (i==2)
                xAdd = 0.0f;
            else if (i==3)
                yAdd = 1.0f;
        }
        vertices[offset] = spr.gameObj.transform.pos.x + (xAdd*spr.gameObj.transform.scale.x);
        vertices[offset+1] = spr.gameObj.transform.pos.y + (yAdd*spr.gameObj.transform.scale.y);

        vertices[offset+2] = color.x;
        vertices[offset+3] = color.y;
        vertices[offset+4] = color.z;
        vertices[offset+5] = color.w;

        offset += VERTEX_SIZE;
    }

    private int[] genIndices() {
        // 6 per quad = 3 per triangle
        int[] elements = new int[6*maxBatchSize];
        for (int i = 0; i < maxBatchSize; i++) {
            loadElementIndices(elements, i);
        }

        return elements;
    }

    private void loadElementIndices(int[] elements, int index) {
        int offsetArrayIndex = 6 * index;
        int offset = 4 * index;
        // Triangle 1 (3,2,0  0,2,1)
        elements[offsetArrayIndex] = offset + 3;
        elements[offsetArrayIndex+1] = offset + 2;
        elements[offsetArrayIndex+2] = offset;
        // Triangle 2 (7,6,4  4,6,5)
        elements[offsetArrayIndex+3] = offset;
        elements[offsetArrayIndex+4] = offset + 2;
        elements[offsetArrayIndex+5] = offset + 1;
    }

    public boolean hasRoom() {
        return this.hasRoom;
    }
}

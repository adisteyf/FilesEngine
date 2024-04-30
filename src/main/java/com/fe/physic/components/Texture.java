package com.fe.physic.components;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class Texture {
    public BufferedImage image;
    public Transform transform;
    public String texture_path;

    public Texture(BufferedImage image, Transform transform, String texture_path) {
        this.transform = transform;
        this.image = image;
        this.texture_path = texture_path;
    }

    public void setImg(String path) {
        try {
            image = ImageIO.read(new File(path));
            texture_path = path;
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}

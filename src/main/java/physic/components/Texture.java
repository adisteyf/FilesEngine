package physic.components;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class Texture {
    public BufferedImage image;
    public Transform transform;

    public Texture(BufferedImage image, Transform transform) {
        this.image = image;
        this.transform = transform;
    }

    public void setImg(String path) {
        try {
            image = ImageIO.read(new File(path));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}

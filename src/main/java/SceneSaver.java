import java.io.*;
import java.util.ArrayList;

public class SceneSaver {
    public static void saveScene(File scene, int limit_x, int limit_y) {
        try {
            if (scene.createNewFile()) {
                System.out.println("Scene file was created!");
            }
            OutputStream os = new FileOutputStream(scene);
            writeIntArr(os, new int[] {70, 101, 76, 86, 76});
            writeIntSector(os, limit_y);
            writeIntSector(os, limit_x);
            writeEntities(os, App.ents);
            os.close();

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
    public static void writeIntArr(OutputStream os, int[] arr) throws IOException {
        for (int i = 0; i < arr.length; i++) {
            os.write(arr[i]);
        }
    }
    public static void writeIntSector(OutputStream os, int num) throws IOException {
        if (num <= 127) {
            if (num != 170) {
                os.write(num);
            }
            else {
                os.write(num-1);
                os.write(1);
            }
        } else {
            int pos = 0;
            for (int i = 0; i < num; i++) {
                pos++;
                if (pos==127) {
                    os.write(pos);pos=0;
                }
            }
            if (pos!=0) {
                if (pos!=170) {
                    os.write(pos);
                }
                else {
                    os.write(pos-1);
                    os.write(1);
                }
            }
        }
        os.write(170);
    }
    public static void writeStringSector(OutputStream os, String str) throws IOException {
        for (int i = 0; i < str.length(); i++) {
            os.write(str.charAt(i));
        }
        os.write(170);
    }
    public static void writeEntities(OutputStream os, ArrayList<Entity> entities) throws IOException {
        for (Entity entity : entities) {
            // Write pos_x int sector
            writeIntSector(os, (int) entity.transform.getX());
            // Write pos_y int sector
            writeIntSector(os, (int) entity.transform.getY());
            // Write size_x int sector
            writeIntSector(os, (int) entity.transform.sizeX);
            // Write size_y int sector
            writeIntSector(os, (int) entity.transform.sizeY);
            // Write texture_path string sector
            writeStringSector(os, entity.texture.texture_path);
            // Write name string sector
            writeStringSector(os, entity.name);
        }
        os.write(221);
    }
}

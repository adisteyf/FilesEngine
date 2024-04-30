package com.fe.scene;
import com.fe.graphics.GamePanel;
import com.fe.physic.Entity;
import com.fe.physic.components.Texture;
import com.fe.physic.components.Transform;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Objects;

public class SceneLoader {
    public static void readScene(File scene) {
        try {
            InputStream is = new FileInputStream(scene);

            byte[] data = new byte[(int)scene.length()];
            ArrayList<String> data_hex = new ArrayList<>();

            is.read(data);
            for (int i = 0; i < data.length; i++) {
                data_hex.add(Integer.toHexString(data[i]));
            }
            System.out.println(data_hex);
            System.out.println(Arrays.toString(data));
            is.close();
            String[] pref = {"46", "65", "4c", "56", "4c"};
            if (isNotCorrupted(data_hex, pref)) {
                System.out.println("this scene isn't corrupted!");
                int[] y_limit = readIntSector(data, data_hex, 5);
                int[] x_limit = readIntSector(data, data_hex, y_limit[1]);
                System.out.println(y_limit[0]+" "+x_limit[0]);
                ArrayList<Entity> entities_from_scene = readEntities(data, data_hex, x_limit[1], x_limit[0], y_limit[0]);
                GamePanel.entities.addAll(entities_from_scene);
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public static boolean isNotCorrupted(ArrayList<String> data_hex, String[] pref) {
        for (int i = 0; i < 5; i++) {
            if (!Objects.equals(data_hex.get(i), pref[i])) {
                return false;
            }
        }
        return true;
    }

    public static int[] readIntSector(byte[] data, ArrayList<String> data_hex, int posInFile) {
        boolean notChecked = true;
        int result = 0;
        while (notChecked) {
            result += data[posInFile];
            posInFile++;
            if (Objects.equals(data_hex.get(posInFile), "ffffffaa")) {
                notChecked = false;
            }
        }
        posInFile++;
        return new int[]{result, posInFile};
    }
    public static ArrayList<String> readToHexArr(ArrayList<String> data_hex, int posInFile) {
        boolean notChecked = true;
        ArrayList<String> result = new ArrayList<>();
        while (notChecked) {
            result.add(data_hex.get(posInFile));
            posInFile++;
            if (Objects.equals(data_hex.get(posInFile), "ffffffaa")) {
                notChecked = false;
            }
        }
        return result;
    }
    public static int readPosInFileForStrArr(ArrayList<String> data_hex, int posInFile) {
        boolean notChecked = true;
        while (notChecked) {
            posInFile++;
            if (Objects.equals(data_hex.get(posInFile), "ffffffaa")) {
                notChecked = false;
            }
        }
        posInFile++;
        return posInFile;
    }
    public static String readStringSector(ArrayList<String> data_hex, int posInFile) {
        boolean notChecked = true;
        StringBuilder result = new StringBuilder();
        while (notChecked) {
            result.append( (char) Integer.parseInt( data_hex.get(posInFile), 16 ) );
            posInFile++;
            if (Objects.equals(data_hex.get(posInFile), "ffffffaa")) {
                notChecked = false;
            }
        }
        return result.toString();
    }
    public static ArrayList<Entity> readEntities(byte[] data, ArrayList<String> data_hex, int posInFile, float x_limit, float y_limit) {
        boolean added = false;
        ArrayList<Entity> result = new ArrayList<>();

        while (!added) {
            int[] posX = readIntSector(data, data_hex, posInFile);
            int[] posY = readIntSector(data, data_hex, posX[1]);
            int[] sizeX = readIntSector(data, data_hex, posY[1]);
            int[] sizeY = readIntSector(data, data_hex, sizeX[1]);
            String texture_path = readStringSector(data_hex, sizeY[1]);
            posInFile = readPosInFileForStrArr(data_hex, sizeY[1]);
            String name = readStringSector( data_hex, posInFile );
            BufferedImage image;
            try {
                image = ImageIO.read(new File(texture_path));
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
            result.add(
                    new Entity(
                            new Texture(
                                    image, new Transform(
                                    (float) posX[0], (float) posY[0], (float) sizeX[0], (float) sizeY[0], 500, 500), texture_path),
                            new Transform(
                                    (float) posX[0], (float) posX[0], (float) sizeX[0], (float) sizeY[0], 500, 500), name));

            System.out.println(posX[0]+" "+posY[0]+" "+sizeX[0]+" "+sizeY[0]+" "+texture_path+" "+name+" "+readPosInFileForStrArr(data_hex, posInFile));
            if (Objects.equals(data_hex.get(readPosInFileForStrArr(data_hex, posInFile)), "ffffffdd")) {
                added = true;
            }
        }
        return result;
    }
}

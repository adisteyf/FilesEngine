package scene;
import graphics.GamePanel;
import physic.Entity;
import physic.components.Texture;
import physic.components.Transform;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Objects;

public class TileSceneLoader {
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
                int[] sizeHeightInTiles = readIntSector(data, data_hex, 5);
                int[] sizeWidthInTiles = readIntSector(data, data_hex, sizeHeightInTiles[1]);
                System.out.println(sizeHeightInTiles[0]+" "+sizeWidthInTiles[0]);
                ArrayList<Entity> entities_from_scene = readEntities(data, data_hex, sizeWidthInTiles[1]);
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
//            result.append( (char) Integer.parseInt( data_hex.get(posInFile) ) );
            switch (data_hex.get(posInFile)) {
                case "61":
                    result.append("a");
                    break;
                case "62":
                    result.append("b");
                    break;
                case "63":
                    result.append("c");
                    break;
                case "64":
                    result.append("d");
                    break;
                case "65":
                    result.append("e");
                    break;
                case "66":
                    result.append("f");
                    break;
                case "67":
                    result.append("g");
                    break;
                case "68":
                    result.append("h");
                    break;
                case "69":
                    result.append("i");
                    break;
                case "6a":
                    result.append("j");
                    break;
                case "6b":
                    result.append("k");
                    break;
                case "6c":
                    result.append("l");
                    break;
                case "6d":
                    result.append("m");
                    break;
                case "6e":
                    result.append("n");
                    break;
                case "6f":
                    result.append("o");
                    break;
                case "70":
                    result.append("p");
                    break;
                case "71":
                    result.append("q");
                    break;
                case "72":
                    result.append("r");
                    break;
                case "73":
                    result.append("s");
                    break;
                case "74":
                    result.append("t");
                    break;
                case "75":
                    result.append("u");
                    break;
                case "76":
                    result.append("v");
                    break;
                case "77":
                    result.append("w");
                    break;
                case "78":
                    result.append("x");
                    break;
                case "79":
                    result.append("y");
                    break;
                case "7a":
                    result.append("z");
                    break;
                case "2e":
                    result.append(".");
                    break;
                case "2f":
                    result.append("/");
                    break;
                case "5f":
                    result.append("_");
                    break;
            }
            posInFile++;
            if (Objects.equals(data_hex.get(posInFile), "ffffffaa")) {
                notChecked = false;
            }
        }
        return result.toString();
    }
    public static ArrayList<Entity> readEntities(byte[] data, ArrayList<String> data_hex, int posInFile) {
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
                                    (float) posX[0], (float) posY[0], (float) sizeX[0], (float) sizeY[0])),
                            new Transform(
                                    (float) posX[0], (float) posX[0], (float) sizeX[0], (float) sizeY[0]), name));

            System.out.println(posX[0]+" "+posY[0]+" "+sizeX[0]+" "+sizeY[0]+" "+texture_path+" "+name+" "+readPosInFileForStrArr(data_hex, posInFile));
            if (Objects.equals(data_hex.get(readPosInFileForStrArr(data_hex, posInFile)), "ffffffdd")) {
                added = true;
            }
        }
        return result;
    }
}

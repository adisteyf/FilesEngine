package graphics;

import javax.swing.*;
import java.awt.*;
import java.awt.image.ImageObserver;
import java.io.FileReader;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import org.json.simple.*;
import org.json.simple.parser.*;
import listener.*;
import physic.Entity;
import physic.components.FilesScripts;

public class GamePanel extends JPanel implements Runnable {
    final int origTileSize = 32;
    final int scale = 2;
    final int tileSize = origTileSize * scale;
    final int scrWidth = 900;
    final int scrHeight = 700;
    final int FPS = 60;
    public static ArrayList<Entity> entities = new ArrayList<>();
    long dt = 1000000000/2;
    public ArrayList<String> SCRs = new ArrayList<>();
    KeyHandler keyH = new KeyHandler();
    Thread gTh;

    public GamePanel() {
        this.setPreferredSize(new Dimension(scrWidth, scrHeight));
        this.setBackground(Color.BLACK);
        this.setDoubleBuffered(true);
        this.addKeyListener(keyH);
        this.setFocusable(true);
    }

    public void startGameThread() {
        gTh = new Thread(this);
        gTh.start();
    }

    @Override
    public void run() {
        long TimeForFrame = 1000000000 / (long)FPS;
        long nextTimeForFrame;
        int timer = 0;
        SCRs = readScripts();

        try {
            runStartInSCRs(SCRs);
        } catch (ClassNotFoundException | NoSuchMethodException | InvocationTargetException | IllegalAccessException |
                 InstantiationException e) {
            throw new RuntimeException(e);
        }

        while (gTh != null) {
            nextTimeForFrame = System.nanoTime() + TimeForFrame;

            update((float) dt /1000000000);
            repaint();
            timer++;
            System.out.println("DT: " + (float)dt/1000000000);

            if ((dt*timer) >= 1000000000) {
                System.out.println("FPS: " + timer);
                timer = 0;
            }

            dt = nextTimeForFrame - System.nanoTime();
            if (dt < 0) dt = 0;
            try {
                Thread.sleep(dt/1000000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    public void update(float dt) {
        try {
            runUpdateInSCRs(dt);
        } catch (ClassNotFoundException | NoSuchMethodException | InvocationTargetException | IllegalAccessException
                 | InstantiationException e) {
            e.printStackTrace();
        }
    }

    public void paintComponent(Graphics g) {
        super.paintComponent(g);

        Graphics2D g2d = (Graphics2D) g;
        g2d.setColor(Color.WHITE);

        try {
            Thread.sleep(dt/1000000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        for (int i = 0; i < entities.toArray().length; i++) {
            if (entities.get(i).texture.image != null) {
                g2d.drawImage(entities.get(i).texture.image, (int)entities.get(i).transform.x, (int)entities.get(i).transform.y, null);
            }
        }
        g2d.dispose();
    }

    private static ArrayList<String> readScripts() {
        JSONParser parser = new JSONParser();
        try (FileReader r = new FileReader("assets/scripts.json")) {
            Object obj = parser.parse(r);
            JSONObject list = (JSONObject) obj;
            return (ArrayList<String>) list.get("SCRs");
        } catch (IOException | ParseException e) {
            throw new RuntimeException(e);
        }
    }

    private void runStartInSCRs(ArrayList<String> SCRs) throws ClassNotFoundException, NoSuchMethodException, InvocationTargetException, IllegalAccessException, InstantiationException {
        for (int i = 0; i < SCRs.size(); i++) {
            Class<FilesScripts> FileClass = (Class<FilesScripts>) Class.forName(SCRs.get(i));
            Method start = FileClass.getMethod("start");
            Object t = FileClass.newInstance();
            start.invoke(t);
        }
    }
    private void runUpdateInSCRs(float dt) throws ClassNotFoundException, NoSuchMethodException, InvocationTargetException, IllegalAccessException, InstantiationException {
        ArrayList<String> SCRs = readScripts();
        for (int i = 0; i < SCRs.size(); i++) {
            Class<FilesScripts> FileClass = (Class<FilesScripts>) Class.forName(SCRs.get(i));
            Method update = FileClass.getMethod("update", float.class);
            Object t = FileClass.newInstance();
            update.invoke(t, dt);
        }
    }
}

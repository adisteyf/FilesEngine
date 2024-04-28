package com.fe.listener;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.ArrayList;

public class KeyHandler implements KeyListener {
//    private static KeyEvent keyEventPressed;
//    private static KeyEvent keyEventReleased;
//    private static KeyEvent keyEventTyped;
    static ArrayList<Boolean> keys = new ArrayList<>();
    public KeyHandler() {
        for (int i = 0; i < 4; i++) {
            keys.add(false);
        }
    }
    @Override
    public void keyTyped(KeyEvent e) {

    }

    @Override
    public void keyPressed(KeyEvent e) {
        switch (e.getKeyCode()) {
            case KeyEvent.VK_W:
                keys.set(0, true);
                break;
            case KeyEvent.VK_S:
                keys.set(1, true);
                break;
            case KeyEvent.VK_A:
                keys.set(2, true);
                break;
            case KeyEvent.VK_D:
                keys.set(3, true);
                break;
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {
        switch (e.getKeyCode()) {
            case KeyEvent.VK_W:
                keys.set(0, false);
                break;
            case KeyEvent.VK_S:
                keys.set(1, false);
                break;
            case KeyEvent.VK_A:
                keys.set(2, false);
                break;
            case KeyEvent.VK_D:
                keys.set(3, false);
                break;
        }
    }

    public static boolean getKey(int key) {
        if (key > keys.size()) return false;
        return keys.get(key);
    }
}

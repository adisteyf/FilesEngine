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
        for (int i = 0; i < 5; i++) {
            keys.add(false);
        }
    }
    @Override
    public void keyTyped(KeyEvent e) {

    }

    @Override
    public void keyPressed(KeyEvent e) {
        switch (e.getKeyChar()) {
            case 'w':
                keys.set(0, true);break;
            case 's':
                keys.set(1, true);break;
            case 'a':
                keys.set(2, true);break;
            case 'd':
                keys.set(3, true);break;
            case 'j':
                keys.set(4, true);break;
            case 'ц':
                keys.set(0, true);break;
            case 'ы':
                keys.set(1, true);break;
            case 'ф':
                keys.set(2, true);break;
            case 'в':
                keys.set(3, true);break;
            case 'о':
                keys.set(4, true);break;
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {
        switch (e.getKeyChar()) {
            case 'w':
                keys.set(0, false);break;
            case 's':
                keys.set(1, false);break;
            case 'a':
                keys.set(2, false);break;
            case 'd':
                keys.set(3, false);break;
            case 'j':
                keys.set(4, false);break;
            case 'ц':
                keys.set(0, false);break;
            case 'ы':
                keys.set(1, false);break;
            case 'ф':
                keys.set(2, false);break;
            case 'в':
                keys.set(3, false);break;
            case 'о':
                keys.set(4, false);break;
        }
    }

    public static boolean getKey(int key) {
        if (key > keys.size()) return false;
        return keys.get(key);
    }
}

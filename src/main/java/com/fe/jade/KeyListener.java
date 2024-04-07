package com.fe.jade;

import static org.lwjgl.glfw.GLFW.GLFW_PRESS;
import static org.lwjgl.glfw.GLFW.GLFW_RELEASE;

public class KeyListener {
    private static KeyListener inst;
    private boolean keyPressed[] = new boolean[350];

    private KeyListener() {

    }

    public static KeyListener get() {
        if ( KeyListener.inst == null ) {
            KeyListener.inst = new KeyListener();
        }
        return KeyListener.inst;
    }

    public static void keyCallback(long window, int key, int scancode, int action, int mods) {
        if ( action == GLFW_PRESS ) {
            get().keyPressed[key] = true;
        } else if ( action == GLFW_RELEASE ) {
            get().keyPressed[key] = false;
        }
    }
    public static boolean isKeyPressed(int keyCode) {
        return get().keyPressed[keyCode];
    }
}

package com.fe.jade;

import org.lwjgl.BufferUtils;
import org.lwjgl.glfw.GLFWGamepadState;

import static org.lwjgl.glfw.GLFW.*;

public class JoystickListener {
    public static GLFWGamepadState state = new GLFWGamepadState(BufferUtils.createByteBuffer(45));
    private JoystickListener() {}
    public static void JoystickCallback(int jid, int event) {
        if (event == GLFW_CONNECTED) {
            // connect
            System.out.println( "JOYSTICK CONNECTED!" );
        } else if (event == GLFW_DISCONNECTED) {
            // disconnect
            System.out.println( "JOYSTICK DISCONNECTED!" );
        }
    }

    public static GLFWGamepadState GetGamepad() {
        return state;
    }
}

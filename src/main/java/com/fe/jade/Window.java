package com.fe.jade;

import com.fe.util.Time;
import org.lwjgl.BufferUtils;
import org.lwjgl.Version;
import org.lwjgl.glfw.GLFWErrorCallback;
import org.lwjgl.glfw.GLFWGamepadState;
import org.lwjgl.opengl.GL;

import static org.lwjgl.glfw.Callbacks.glfwFreeCallbacks;
import static org.lwjgl.glfw.GLFW.*;
import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.system.MemoryUtil.NULL;

public class Window {
    static int width;
    static int height;
    static String title;
    private long glfwWindow;
    public float r, g, b, a;
    static Window window = null;
    private static Scene currentScene = null;
    public static boolean TryConnectJoystick = true;
    public Window(int w, int h, String t) {
        this.width = w;
        this.height = h;
        this.title = t;
        r=1;
        g=1;
        b=1;
        a=1;
    }

    public static void changeScene(int newScene) {
        switch (newScene) {
            case 0:
                currentScene = new SceneEditor();
                currentScene.init();
                break;
            case 1:
                currentScene = new ClassicScene();
                break;
            default:
                assert false: "Unknown scene! (" + newScene + ")";
                break;
        }
    }

    public static Window get() {
        if ( Window.window == null ) {
            Window.window = new Window(Window.width, Window.height, Window.title);
        }

        return Window.window;
    }

    public void run() {
        System.out.println( "Hello LWJGL! " + Version.getVersion() );

        init();
        loop();

        // Free memory
        glfwFreeCallbacks(glfwWindow);
        glfwDestroyWindow(glfwWindow);

        // Terminate GLFW and free the err callback
        glfwTerminate();
        glfwSetErrorCallback(null).free();
    }

    public void init() {
        // err callback setup
//        System.err.println("ERROR IN FILES ENGINE.");
        GLFWErrorCallback.createPrint(System.err).set();
        // Initialize GLFW
        if ( !glfwInit() ) {
            throw new IllegalStateException( "Unable to initialize GLFW." );
        }

        // Configure GLFW
        glfwDefaultWindowHints();
        glfwWindowHint(GLFW_VISIBLE, GLFW_FALSE);
        glfwWindowHint(GLFW_RESIZABLE, GLFW_TRUE);
        glfwWindowHint(GLFW_MAXIMIZED, GLFW_TRUE);

        // Create the window
        glfwWindow = glfwCreateWindow(this.width, this.height, this.title, NULL, NULL);

        if ( glfwWindow == NULL ) {
            throw new IllegalStateException( "Failed to create the Files Engine window." );
        }

        glfwSetCursorPosCallback(glfwWindow, MouseListener::mousePosCallback);
        glfwSetMouseButtonCallback(glfwWindow, MouseListener::mouseButtonCallback);
        glfwSetScrollCallback(glfwWindow, MouseListener::mouseScrollCallback);
        glfwSetKeyCallback(glfwWindow, KeyListener::keyCallback);
        glfwSetJoystickCallback(JoystickListener::JoystickCallback);

        // Make OpenGL context current
        glfwMakeContextCurrent(glfwWindow);
        // Enable V-Sync
        glfwSwapInterval(1);

        // Make the window visible
        glfwShowWindow(glfwWindow);

        GL.createCapabilities();

        Window.changeScene(0);
    }
    public void loop() {
        float beginTime = Time.getTime();
        float endTime;
        float dt = -1.0f;

        while ( !glfwWindowShouldClose(glfwWindow) ) {
            // events
            glfwPollEvents();
            glClearColor(r,g,b,a);
            glClear(GL_COLOR_BUFFER_BIT);

            if ( dt >= 0 )
                currentScene.update(dt);

            glfwSwapBuffers(glfwWindow);

            endTime = Time.getTime();
            dt = endTime - beginTime;
            beginTime = endTime;
        }
    }
}

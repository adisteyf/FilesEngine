package com.fe.util;

public class Time {
    public static float timeStarted = System.nanoTime();
    public static double D_multiplier = 1E-9;
    public static float getTime() {return(float)((System.nanoTime()-timeStarted)*D_multiplier);}
}

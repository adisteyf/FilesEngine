package physic;

public class Timer {
    public static double getTime() {
        return (double)System.nanoTime()/(double)10000000000L;
    }
}

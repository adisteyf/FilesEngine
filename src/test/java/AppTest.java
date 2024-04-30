import java.util.ArrayList;

/**
 * Unit test for simple App.
 */
public class AppTest
{
    public static void main(String[] args) {
        convertInt();
    }
    public static void convertInt() {
        String test = "test";
        for (int i = 0; i < test.length(); i++) {
            System.out.println((int) test.charAt(i));
        }
    }
}

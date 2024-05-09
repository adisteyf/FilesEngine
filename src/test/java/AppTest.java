import com.fe.physic.components.RectCollider;

public class AppTest {
    public static void main(String[] args) {
        System.out.println("test of java");
        RectCollider test = new RectCollider(0,0,100f,100f);
        RectCollider test_2 = new RectCollider(0,0, 100f, 100f);
        if (!test.equals(test_2)) {
            System.out.println("SSSADasfa");
        }
    }
}
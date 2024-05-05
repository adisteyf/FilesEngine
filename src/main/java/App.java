import javax.swing.JFrame;
import com.fe.graphics.GamePanel;

public class App
{
    public static void main( String[] args )
    {
        JFrame window = new JFrame();
        window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        window.setResizable(false);
        window.setTitle("Files Engine");

        GamePanel gp = new GamePanel();
        window.add(gp);

        window.pack();

        window.setLocationRelativeTo(null);
        window.setVisible(true);

        gp.startGameThread();
    }
}

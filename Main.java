import javax.swing.*;
import java.awt.*;

class Main extends JFrame {
    private Main() {
        setTitle("Maze Animation - AlbertBrook");
        setSize(Settings.SCREEN_SIZE);
        setResizable(false);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        getContentPane().setBackground(Color.BLACK);

        add(Draw.getDraw().getCanvas());
        BFS.getBfs().animation();
    }

    public static void main(String[] args) {
        new Main().setVisible(true);
    }
}

package mazeanimation;

import javax.swing.*;
import java.awt.*;

class Main extends JFrame {
    private Main() {
        setTitle("Maze - AlbertBrook");
        setSize(Settings.SCREEN_SIZE);
        setResizable(false);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        getContentPane().setBackground(Color.BLACK);

        Functions.getFunctions(this);
    }

    public static void main(String[] args) {
        new Main().setVisible(true);
    }
}

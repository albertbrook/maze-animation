package mazeanimation;

import java.awt.*;

class Functions {
    private static Functions functions;

    private Functions(Main main) {
        main.add(setCanvas());
    }

    static Functions getFunctions(Main main) {
        if (functions == null)
            functions = new Functions(main);
        return functions;
    }

    private Canvas setCanvas() {
        return new Canvas() {
            @Override
            public void paint(Graphics g) {
                Graphics2D g2 = (Graphics2D) g;
                g2.setStroke(new BasicStroke(Settings.LINE_SIZE));
                for (int i = 0; i < Settings.MAP.length; i++)
                    for (int j = 0; j < Settings.MAP[0].length; j++) {
                        drawPlane(i, j, g2);
                        if (Settings.MAP[i][j] == 1)
                            drawBlock(i, j, Color.WHITE, g2);
                        else if (Settings.MAP[i][j] == 2)
                            drawBlock(i, j, Color.RED, g2);
                        else if (Settings.MAP[i][j] == 3)
                            drawBlock(i, j, Color.GREEN, g2);
                    }
            }

            @Override
            public void update(Graphics g) {
                paint(g);
            }
        };
    }

    private void drawPlane(int i, int j, Graphics2D g2) {
        int location = Settings.LINE_SIZE / 2 + Settings.BLOCK_SIZE;
        int size = Settings.LINE_SIZE + Settings.BLOCK_SIZE;
        g2.setColor(Color.WHITE);
        g2.drawRect(location + i * size, location + j * size, size, size);
    }

    void drawBlock(int x, int y, Color color, Graphics2D g2) {
        int size = Settings.LINE_SIZE + Settings.BLOCK_SIZE;
        g2.setColor(color);
        g2.fillRect((x + 1) * size, (y + 1) * size, Settings.BLOCK_SIZE, Settings.BLOCK_SIZE);
    }
}

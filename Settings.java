import java.awt.*;
import java.util.Random;

class Settings {
    static final byte[][] MAP = setMap();

    static final int LINE_SIZE = 3;
    static final int BLOCK_SIZE = 60;
    static final Dimension SCREEN_SIZE = setScreenSize();

    private Settings() {}

    private static byte[][] setMap() {
        byte[][] map = new byte[10][10];
        map[4][4] = 2;
        map[7][8] = 3;
        Random r = new Random();
        for (int i = 0; i < 20; i++) {
            int x = r.nextInt(10);
            int y = r.nextInt(10);
            while (x == 4 && y == 4 || x == 7 && y ==8) {
                x = r.nextInt(10);
                y = r.nextInt(10);
            }
            map[x][y] = 1;
        }
        return map;
    }

    private static Dimension setScreenSize() {
        int width = BLOCK_SIZE * (MAP.length + 2) + LINE_SIZE * (MAP.length + 1) + 6;
        int height = BLOCK_SIZE * (MAP[0].length + 2) + LINE_SIZE * (MAP[0].length + 1) + 29;
        return new Dimension(width, height);
    }
}

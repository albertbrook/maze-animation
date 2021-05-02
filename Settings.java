import java.awt.*;
import java.util.Random;

class Settings {
    static final byte[][] MAP = setMap();

    static final int LINE_SIZE = 3;
    static final int BLOCK_SIZE = 60;
    static final Dimension SCREEN_SIZE = setScreenSize();

    static final int SEARCH_SPEED = 500;
    static final int ROUTE_SPEED = 300;

    private Settings() {}

    private static byte[][] setMap() {
        byte[][] map = new byte[10][10];
        map[4][4] = 2;
        map[7][8] = 3;
        Random r = new Random();
        for (int i = 0; i < 20; i++) {
            int x = r.nextInt(10);
            int y = r.nextInt(10);
            while (map[x][y] == 2 || map[x][y] == 3) {
                x = r.nextInt(10);
                y = r.nextInt(10);
            }
            map[x][y] = 1;
        }
//        map[0][0] = 2;
//        map[9][9] = 3;
//        for (int i = 0; i < 3; i++) {
//            map[i][3] = 1;
//            map[4][i] = 1;
//            map[4][3 + i] = 1;
//            map[6][9 - i] = 1;
//            map[6][6 - i] = 1;
//            map[1][9 - i] = 1;
//            map[2 + i][7] = 1;
//            map[9 - i][1] = 1;
//            if (i < 2) {
//                map[1][i] = 1;
//                map[9 - i][7] = 1;
//            }
//        }
        return map;
    }

    private static Dimension setScreenSize() {
        int width = BLOCK_SIZE * (MAP.length + 2) + LINE_SIZE * (MAP.length + 1) + 6;
        int height = BLOCK_SIZE * (MAP[0].length + 2) + LINE_SIZE * (MAP[0].length + 1) + 29;
        return new Dimension(width, height);
    }
}

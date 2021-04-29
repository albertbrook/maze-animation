package mazeanimation;

import java.awt.*;

class Settings {
    static final byte[][] MAP = setMap();

    static final int LINE_SIZE = 3;
    static final int BLOCK_SIZE = 60;
    static final Dimension SCREEN_SIZE = setScreenSize();

    private Settings() {}

    private static byte[][] setMap() {
        byte[][] map = new byte[9][6];
        map[1][1] = 2;
        map[8][4] = 3;
        for (int i = 3; i < 6; i++)
            map[2][i] = 1;
        return map;
    }

    private static Dimension setScreenSize() {
        int width = BLOCK_SIZE * (MAP.length + 2) + LINE_SIZE * (MAP.length + 1) + 6;
        int height = BLOCK_SIZE * (MAP[0].length + 2) + LINE_SIZE * (MAP[0].length + 1) + 29;
        return new Dimension(width, height);
    }
}

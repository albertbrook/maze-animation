package mazeanimation;

import java.awt.*;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Queue;

class BFS {
    private static BFS bfs;

    private Canvas canvas;

    private BFS() {
        canvas = Draw.getDraw().getCanvas();
    }

    static BFS getBfs() {
        if (bfs == null)
            bfs = new BFS();
        return bfs;
    }

    private int[] getStart() {
        for (int i = 0; i < Settings.MAP.length; i++)
            for (int j = 0; j < Settings.MAP[i].length; j++)
                if (Settings.MAP[i][j] == 2)
                    return new int[]{i, j};
        return null;
    }

    private ArrayList<int[]> getNeighbors(int[] coordinate) {
        ArrayList<int[]> neighbor = new ArrayList<>();
        neighbor.add(coordinate);
        if (coordinate[1] - 1 >= 0)
            neighbor.add(new int[]{coordinate[0], coordinate[1] - 1});
        if (coordinate[0] + 1 <= Settings.MAP.length - 1)
            neighbor.add(new int[]{coordinate[0] + 1, coordinate[1]});
        if (coordinate[1] + 1 <= Settings.MAP[0].length - 1)
            neighbor.add(new int[]{coordinate[0], coordinate[1] + 1});
        if (coordinate[0] - 1 >= 0)
            neighbor.add(new int[]{coordinate[0] - 1, coordinate[1]});
        return neighbor;
    }

    void animation() {
        if (getStart() != null) {
            Queue<int[]> queue = new LinkedList<>();
            queue.offer(getStart());
            boolean[][] visited = new boolean[Settings.MAP.length][Settings.MAP[0].length];
            for (int i = 0; i < Settings.MAP.length; i++)
                for (int j = 0; j < Settings.MAP[0].length; j++)
                    if (Settings.MAP[i][j] == 1)
                        visited[i][j] = true;
            ArrayList<ArrayList<int[]>> link = new ArrayList<>();
            while (!queue.isEmpty()) {
                int[] pop = queue.poll();
                if (!visited[pop[0]][pop[1]]) {
                    visited[pop[0]][pop[1]] = true;
                    ArrayList<int[]> neighbors = getNeighbors(pop);
                    link.add(neighbors);
                    for (int i = 1; i < neighbors.size(); i++)
                        queue.offer(neighbors.get(i));
                    if (Settings.MAP[pop[0]][pop[1]] == 3) {
                        ArrayList<int[]> router = new ArrayList<>();
                        int[] son = link.get(link.size() - 1).get(0);
                        for (int i = link.size() - 2; i >= 1; i--) {
                            for (int j = 1; j < link.get(i).size(); j++) {
                                if (son[0] == link.get(i).get(j)[0] && son[1] == link.get(i).get(j)[1]) {
                                    router.add(link.get(i).get(0));
                                    son = link.get(i).get(0);
                                }
                            }
                        }
                        new Thread() {
                            @Override
                            public void run() {
                                super.run();
                                for (int[] i: router) {
                                    Settings.MAP[i[0]][i[1]] = 4;
                                    canvas.repaint();
                                    try {
                                        Thread.sleep(200);
                                    } catch (InterruptedException e) {
                                        e.printStackTrace();
                                    }
                                }
                            }
                        }.start();
                        return;
                    }
                }
            }
        }
    }
}

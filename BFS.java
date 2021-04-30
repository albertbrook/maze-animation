package mazeanimation;

import java.awt.*;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
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

    private boolean[][] getVisited() {
        boolean[][] visited = new boolean[Settings.MAP.length][Settings.MAP[0].length];
        for (int i = 0; i < visited.length; i++)
            for (int j = 0; j < visited[0].length; j++)
                if (Settings.MAP[i][j] == 1)
                    visited[i][j] = true;
        return visited;

    }

    private ArrayList<ArrayList<int[]>> getNeighbors(int[] coordinate, int[] index) {
        ArrayList<ArrayList<int[]>> neighbors = new ArrayList<>();
        if (coordinate[1] - 1 >= 0)
            neighbors.add(new ArrayList<int[]>(){{
                add(new int[]{coordinate[0], coordinate[1] - 1});
            }});
        if (coordinate[0] + 1 <= Settings.MAP.length - 1)
            neighbors.add(new ArrayList<int[]>(){{
                add(new int[]{coordinate[0] + 1, coordinate[1]});
            }});
        if (coordinate[1] + 1 <= Settings.MAP[0].length - 1)
            neighbors.add(new ArrayList<int[]>(){{
                add(new int[]{coordinate[0], coordinate[1] + 1});
            }});
        if (coordinate[0] - 1 >= 0)
            neighbors.add(new ArrayList<int[]>(){{
                add(new int[]{coordinate[0] - 1, coordinate[1]});
            }});
        for (ArrayList<int[]> neighbor: neighbors) {
            neighbor.add(coordinate);
            neighbor.add(new int[]{index[0] + 1});
        }
        return neighbors;
    }

    private ArrayList<ArrayList<int[]>> calculate() {
        if (getStart() == null)
            return null;
        boolean[][] visited = getVisited();
        Queue<ArrayList<int[]>> queues = new LinkedList<>();
        ArrayList<ArrayList<int[]>> arrayLists = new ArrayList<>();
        queues.offer(new ArrayList<int[]>(){{
            add(getStart());
            add(getStart());
            add(new int[]{0});
        }});
        while (!queues.isEmpty()) {
            ArrayList<int[]> arrayList = queues.poll();
            if (!visited[arrayList.get(0)[0]][arrayList.get(0)[1]]) {
                visited[arrayList.get(0)[0]][arrayList.get(0)[1]] = true;
                ArrayList<ArrayList<int[]>> neighbors = getNeighbors(arrayList.get(0), arrayList.get(2));
                for (ArrayList<int[]> neighbor: neighbors)
                    queues.offer(neighbor);
                arrayLists.add(arrayList);
                if (Settings.MAP[arrayList.get(0)[0]][arrayList.get(0)[1]] == 3) {
                    for (ArrayList<int[]> i: arrayLists) {
                        for (int[] j: i) {
                            System.out.print("[");
                            for (int k: j)
                                System.out.print(k + ",");
                            System.out.print("\b] ");
                        }
                        System.out.println("\b");
                    }
                    return arrayLists;
                }
            }
        }
        return null;
    }

    void animation() {
        new Thread() {
            @Override
            public void run() {
                super.run();
                ArrayList<ArrayList<int[]>> arrayLists = calculate();
                if (arrayLists == null)
                    return;
                for (int i = 1; i < arrayLists.size() - 1; i++) {
                    Settings.MAP[arrayLists.get(i).get(0)[0]][arrayLists.get(i).get(0)[1]] = 4;
                    try {
                        Thread.sleep(200);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    canvas.repaint();
                }
            }
        }.start();
    }
}

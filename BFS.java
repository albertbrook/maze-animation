import java.awt.*;
import java.util.*;

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

    private ArrayList<ArrayList<ArrayList<int[]>>> calculate() {
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
                    ArrayList<ArrayList<ArrayList<int[]>>> arrayLists1 = new ArrayList<>();
                    int index = arrayLists.get(0).get(2)[0];
                    arrayLists1.add(new ArrayList<ArrayList<int[]>>(){});
                    for (ArrayList<int[]> i: arrayLists)
                        if (i.get(2)[0] == index)
                            arrayLists1.get(index).add(i);
                        else {
                            index += 1;
                            arrayLists1.add(new ArrayList<ArrayList<int[]>>(){{
                                add(i);
                            }});
                        }
                    return arrayLists1;
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
                ArrayList<ArrayList<ArrayList<int[]>>> arrayLists = calculate();
                if (arrayLists == null)
                    return;
                arrayLists.get(0).remove(0);
                ArrayList<int[]> terminal = arrayLists.get(arrayLists.size() - 1).get(arrayLists.get(arrayLists.size() - 1).size() - 1);
                arrayLists.get(arrayLists.size() - 1).remove(arrayLists.get(arrayLists.size() - 1).size() - 1);
                ArrayList<int[]> route = new ArrayList<int[]>(){{
                    add(terminal.get(1));
                }};
                for (ArrayList<ArrayList<int[]>> arrayList : arrayLists) {
                    try {
                        Thread.sleep(Settings.SEARCH_SPEED);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    for (ArrayList<int[]> ints : arrayList)
                        Settings.MAP[ints.get(0)[0]][ints.get(0)[1]] = 4;
                    canvas.repaint();
                }
                for (int i = arrayLists.size() - 1; i >= 2; i--) {
                    for (int j = arrayLists.get(i).size() - 1; j >= 0; j--) {
                        if (route.get(route.size() - 1)[0] == arrayLists.get(i).get(j).get(0)[0] &&
                                route.get(route.size() - 1)[1] == arrayLists.get(i).get(j).get(0)[1])
                            route.add(arrayLists.get(i).get(j).get(1));
                    }
                }
                for (int[] ints: route) {
                    try {
                        Thread.sleep(Settings.ROUTE_SPEED);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    Settings.MAP[ints[0]][ints[1]] = 5;
                    canvas.repaint();
                }
            }
        }.start();
    }
}

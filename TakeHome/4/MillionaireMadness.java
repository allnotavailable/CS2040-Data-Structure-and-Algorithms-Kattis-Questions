import java.util.Comparator;
import java.util.PriorityQueue;

class MillionaireMadness {
    public static void main(String args[]) {
        FastIO fio = new FastIO();
        int rows = fio.nextInt();
        int columns = fio.nextInt();
        int[][] grid = new int[rows][columns];
        int[][] visited = new int[rows][columns];
        int[][] directions = {{1,0}, {-1,0}, {0,-1}, {0,1}};
        int output = 0;

        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < columns; j++) {
                grid[i][j] = fio.nextInt();
            }
        }

        PriorityQueue<Pile> pq = new PriorityQueue<Pile>(new PileComp());
        pq.add(new Pile(0, 0, grid[0][0], 0));
        visited[0][0] = 1;

        while(!pq.isEmpty() && visited[rows - 1][columns - 1] == 0) {
            Pile currPile = pq.poll();
            visited[currPile.x][currPile.y] = 1;
            output = Math.max(currPile.difference, output);
            for (int i = 0; i < 4; i++) {
                int nextX = currPile.x + directions[i][0];
                int nextY = currPile.y + directions[i][1];
                if (nextX < rows && nextY < columns && nextX > -1 && nextY > -1 && visited[nextX][nextY] == 0) {
                    int nextHeight = grid[nextX][nextY];
                    int nextDifference = nextHeight - currPile.height;
                    Pile nextPile = new Pile(nextX, nextY, nextHeight, nextDifference);
                    pq.add(nextPile);
                }
            }
        }
        System.out.println(output);
        fio.close();
    }
}

class Pile {
    public int x;
    public int y;
    public int height;
    public int difference;

    Pile(int x, int y, int height, int difference) {
        this.x = x;
        this.y = y;
        this.height = height;
        this.difference = difference;
    }
}

class PileComp implements Comparator<Pile> {
    public int compare(Pile p1, Pile p2) {
        return p1.difference - p2.difference;
    }
}

import java.util.*;

class LostMap {
    public static void main (String args[]) {
        FastIO fio = new FastIO();
        int n = fio.nextInt();
        int[][] grid = new int[n][n];

        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                grid[i][j] = fio.nextInt();
            }
        }

        ArrayList<Edge> edgeList = new ArrayList<Edge>();
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                edgeList.add(new Edge(i, j, grid[i][j]));
            }
        }

        Collections.sort(edgeList, new EdgeComp());
        UFDS ufds = new UFDS(n);
        for (Edge edge : edgeList) {
            int i = edge.u;
            int j = edge.v;
            if (!ufds.isSameSet(i, j)) {
                ufds.unionSet(i, j);
                i++;
                j++;
                System.out.println(i + " " + j);
            }
        }
        fio.close();
    }
}

class Edge {
    public int u;
    public int v;
    public int w;

    Edge(int u, int v, int w) {
        this.u = u;
        this.v = v;
        this.w = w;
    }
}

class EdgeComp implements Comparator<Edge> {
    public int compare(Edge e1, Edge e2) {
        if (e1.w < e2.w) {
            return -1;
        } else if (e1.w > e2.w) {
            return 1;
        } else {
            return 0;
        }
    }
}

class UFDS {
    public int[] root;

    UFDS(int n) {
        this.root = new int[n];
        for (int i = 0; i < n; i++) {
            this.root[i] = i;
        }
    }

    public int findSet(int i) {
        if (root[i] == i) {
            return i;
        } else {
            root[i] = findSet(root[i]);
            return root[i];
        }
    }
    
    public boolean isSameSet(int i, int j) {
        return findSet(i) == findSet(j);
    }

    public void unionSet(int i , int j) {
        if (!isSameSet(i, j)) {
            root[findSet(j)] = findSet(i);
        }
    }
}
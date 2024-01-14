import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Stack;

class Dominos {
    public static void main (String args[]) {
        FastIO fio = new FastIO();
        int testcases = fio.nextInt();
        for (int i = 0; i < testcases; i++) {
            int n = fio.nextInt();
            int m = fio.nextInt();

            ArrayList<LinkedList<Integer>> adjencyList = new ArrayList<LinkedList<Integer>>();
            for (int j = 0; j < n; j++) {
                adjencyList.add(new LinkedList<Integer>());
            }

            for (int k = 0; k < m; k++) {
                int a = fio.nextInt() - 1;
                int b = fio.nextInt() - 1;  
                adjencyList.get(a).add(b);
            }

            DirectedGraph graph = new DirectedGraph(adjencyList);
            Pair<Integer, int[]> kosaPair = graph.kosaAlgo();
            int sccCount = kosaPair.first;
            int[] parent = kosaPair.second;
            int[] check = new int[sccCount + 1];
            int output = 0;
            for (int j = 0; j < adjencyList.size(); j++) {
                LinkedList<Integer> jNeighbour = adjencyList.get(j);
                for (int k : jNeighbour) {
                    if (parent[j] != parent[k]) {
                        if (check[parent[k]] == 0) {
                            check[parent[k]] = 1;
                            output++;
                        }
                    }
                }
            }
            System.out.println(sccCount - output);
        }
        fio.close();
    }
}

class DirectedGraph {
    public ArrayList<LinkedList<Integer>> adjencyList;

    DirectedGraph(ArrayList<LinkedList<Integer>> adjencyList) {
        this.adjencyList = adjencyList;
    }

    public int[] dfs(int x, int[] visited, int[] parent, int next) {
        LinkedList<Integer> xNeighbour = this.adjencyList.get(x);
        parent[x] = next;
        visited[x] = 1;
        for (int y : xNeighbour) {
            if (visited[y] == 0) {
                dfs(y, visited, parent, next);
            }
        }
        return parent;
    }

    public void stackUpdate(int x, int[] visited, Stack<Integer> stack) {
        LinkedList<Integer> xNeighbour = this.adjencyList.get(x);
        visited[x] = 1;
        for (int y : xNeighbour) {
            if (visited[y] == 0) {
                stackUpdate(y, visited, stack);
            }
        }
        stack.push(x);
    }

    public DirectedGraph transposeDirectedGraph() {
        ArrayList<LinkedList<Integer>> transAdjencyList = new ArrayList<LinkedList<Integer>>();
        for (int i = 0; i < this.adjencyList.size(); i++) {
            transAdjencyList.add(new LinkedList<Integer>());
        }
        for (int i = 0; i < this.adjencyList.size(); i++) {
            for (int j : this.adjencyList.get(i)) {
                transAdjencyList.get(j).add(i);
            }
        }
        return new DirectedGraph(transAdjencyList);
    }

    public Pair<Integer, int[]> kosaAlgo() {
        Stack<Integer> stack = new Stack<Integer>();
        int[] parent = new int[this.adjencyList.size()];
        int[] visited = new int[this.adjencyList.size()];

        for (int i = 0; i < this.adjencyList.size(); i++) {
            if (visited[i] == 0) {
                stackUpdate(i, visited, stack);
            }
        }
        int next = 0;
        int[] newVisited = new int[this.adjencyList.size()];
        DirectedGraph transAdjencyList = this.transposeDirectedGraph();
        while(!stack.isEmpty()) {
            int x = stack.pop();
            if (newVisited[x] == 0) {
                next++;
                transAdjencyList.dfs(x, newVisited, parent, next);
            }
        }
        return new Pair<Integer, int[]>(next, parent);
    }
}

class Pair<U, V> {
    public U first;
    public V second;

    Pair(U first, V second) {
        this.first = first;
        this.second = second;
    }
}
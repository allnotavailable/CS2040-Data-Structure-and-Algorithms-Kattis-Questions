class WeakVertices {
    public static void main (String args[]) {
        FastIO fio = new FastIO();
        
        int vertices = fio.nextInt();
        while(vertices != -1) {
            int[][] adjacencyMatrix = new int[vertices][vertices];

            for (int i = 0; i < vertices; i++) {
                for (int j = 0; j < vertices; j++) {
                    int input = fio.nextInt();
                    adjacencyMatrix[i][j] = input;
                }
            }

            for (int i = 0; i < vertices; i++) {
                boolean isWeak = true;
                for (int j = 0; j < vertices; j++) {
                    for (int k = 0; k < vertices; k++) {
                        if (adjacencyMatrix[i][j] == 1 && adjacencyMatrix[i][k] == 1 && adjacencyMatrix[j][k] == 1) {
                            isWeak = false;
                        }
                    }
                }
                if (isWeak == true) {
                    System.out.println(i + " ");
                }
            }
            vertices = fio.nextInt();
        }
        fio.close();
    }
}

        


class Islands {
    public static void main (String args[]) {
        FastIO fio = new FastIO();

        int rows = fio.nextInt();
        int columns = fio.nextInt();
        int[][] visited = new int [rows][columns];
        char[][] grid = new char[rows][columns];

        for (int i = 0; i < rows; i++) {
            String input = fio.next();
            for (int j = 0; j < columns; j++) {
                grid[i][j] = input.charAt(j);
            }
        }

        int output = 0;
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < columns; j++) {
                if (grid[i][j] == 'L' && visited[i][j] == 0) {
                    output++;
                    dfs(grid, visited, i, j);
                }
            }
        }

        System.out.println(output);
        fio.close();
    }

    public static void dfs(char[][] grid, int[][] visited, int i, int j) {
        if (visited[i][j] == 1) {
            return;
        }
        int[] row = {0, 0, -1, 1};
        int[] column = {1, -1, 0, 0};
        visited[i][j] = 1;
        for (int k = 0; k < 4; k++) {
            int checkRow = i + row[k]; 
            int checkColumn = j + column[k];
            if (checkRow < 0 || checkColumn < 0 || checkRow >= grid.length || 
                checkColumn >= grid[0].length) {
                continue; 
            } 
            if (grid[checkRow][checkColumn] == 'C') {
                dfs(grid, visited, checkRow, checkColumn); 
            } 
            if (grid[checkRow][checkColumn] == 'L') {
                dfs(grid, visited, checkRow, checkColumn); 
            }
        }
    }
}

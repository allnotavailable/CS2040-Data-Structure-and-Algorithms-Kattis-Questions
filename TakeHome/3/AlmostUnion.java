public class AlmostUnion {
    public static void main(String[] args) {
        FastIO fio = new FastIO();
        String s = fio.nextLine();
        
        while (s != null) {
            String[] arr = s.split(" ");
            int[] intArray = new int[arr.length];
            for (int i = 0; i < arr.length; i++) {
                intArray[i] = Integer.parseInt(arr[i]);
            }
            
            int nums = intArray[0];
            int operations = intArray[1];
            UFDS ufds = new UFDS(nums);
    
            for (int i = 0; i < operations; i++) {
                int command = fio.nextInt();
    
                if (command == 1) {
                    int p = fio.nextInt();
                    int q = fio.nextInt();
                    ufds.unionSet(p, q);
                } else if (command == 2) {
                    int p = fio.nextInt();
                    int q = fio.nextInt();
                    ufds.moveSet(p, q);
                } else {
                    int p = fio.nextInt();
                    int numElements = ufds.numOfElements(p);
                    long sumElements = ufds.sumOfElements(p);
                    System.out.println(numElements + " " + sumElements);
                }
            }
            s = fio.nextLine();
        }
        fio.close();
    }
}

class UFDS {
    public int[] root;
    public int[] next;
    public int[] numOfElements;
    public long[] sumOfElements;

    UFDS(int n) {
        this.root = new int[n + 1];
        this.next = new int[n + 1];
        this.numOfElements = new int[n + 1];
        this.sumOfElements = new long[n + 1];
        for (int i = 1; i <= n; i++) {
            root[i] = i;
            next[i] = i;
            numOfElements[i] = 1;
            sumOfElements[i] = i;
        }
    }

    public void unionSet(int p, int q) {
        if (!isSameSet(p, q)) {
            int x = findSet(p);
            int y = findSet(q);
            root[x] = y;
            next[p] = y;
            numOfElements[y] += numOfElements[x];
            sumOfElements[y] += sumOfElements[x];
        }
    }

    public int findSet(int p) {
        int rootNum = next[p];
        while (rootNum != root[rootNum]) {
            rootNum = root[rootNum];
        }
        next[p] = rootNum;
        return rootNum;
    }

    public Boolean isSameSet(int p, int q) { return findSet(p) == findSet(q); }

    public void moveSet(int p, int q) {
        if (!isSameSet(p, q)) {
            int x = findSet(p);
            int y = findSet(q);
            next[p] = y;
            numOfElements[x] -= 1;
            numOfElements[y] += 1;
            sumOfElements[x] -= p;
            sumOfElements[y] += p;
        }
    }

    public int numOfElements(int p) {
        int x = findSet(p);
        return numOfElements[x];
    }

    public long sumOfElements(int p) {
        int x = findSet(p);
        return sumOfElements[x];
    }
}
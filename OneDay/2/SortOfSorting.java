/*  While input is not 0, read testcases and string inputs
 *  Add strings into array list
 *  Sort array list by its element first letter ascii value, if first letters are the same, compare the second letter
 *  if second letters are also the same, do not sort.
 */

import java.util.*;
import java.io.*;

class SortOfSorting {
    public static void main (String args[]) {
        FastIO fio = new FastIO();
        StringBuilder output = new StringBuilder("");

        while (true) {
            
            int tests = fio.nextInt();
            if (tests == 0) {
                break;
            }
            ArrayList<String> nameList = new ArrayList<String>();

            for (int i = 0; i < tests; i++) {
                String name = fio.next();
                nameList.add(name);
            }
            Collections.sort(nameList, new Comp());
            for (int j = 0; j < nameList.size(); j++) {
                output.append(nameList.get(j)).append("\n");
            }
            System.out.println("\n");
        }
        System.out.println(output);
    }
}

class Comp implements Comparator<String> {

    public int compare(String s1, String s2) {

        if (s1.charAt(0) < s2.charAt(0)) {
            return -1;
        } else if (s1.charAt(0) == s2.charAt(0)) {
            if (s1.charAt(1) < s2.charAt(1)) {
                return -1;
            } else if (s1.charAt(1) == s2.charAt(1)) {
                return 0;
            } else {
                return 1;
            }
        } else {
            return 1;
        }
    }
}

class FastIO extends PrintWriter {
    BufferedReader br;
    StringTokenizer st;

    public FastIO() {
        super(new BufferedOutputStream(System.out));
        br = new BufferedReader(new InputStreamReader(System.in));
    }

    String next() {
        while (st == null || !st.hasMoreElements()) {
            try {
                st = new StringTokenizer(br.readLine());
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return st.nextToken();
    }

    int nextInt() {
        return Integer.parseInt(next());
    }

    long nextLong() {
        return Long.parseLong(next());
    }

    double nextDouble() {
        return Double.parseDouble(next());
    }

    String nextLine() {
        String str = "";
        try {
            str = br.readLine();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return str;
    }
}

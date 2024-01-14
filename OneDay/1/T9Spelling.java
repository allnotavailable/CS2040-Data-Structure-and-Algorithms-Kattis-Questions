// Timothy Hanny Fusanto A0265878E
import java.io.*;
import java.util.*;
class T9Spelling {
    public static void main (String[] args) {
        FastIO fio = new FastIO();
        StringBuilder sb = new StringBuilder();
        String[] arr = new String[256];
        arr[' '] = "0";
        arr['a'] = "2";
        arr['b'] = "22";
        arr['c'] = "222";
        arr['d'] = "3";
        arr['e'] = "33";
        arr['f'] = "333";
        arr['g'] = "4";
        arr['h'] = "44";
        arr['i'] = "444";
        arr['j'] = "5";
        arr['k'] = "55";
        arr['l'] = "555";
        arr['m'] = "6";
        arr['n'] = "66";
        arr['o'] = "666";
        arr['p'] = "7";
        arr['q'] = "77";
        arr['r'] = "777";
        arr['s'] = "7777";
        arr['t'] = "8";
        arr['u'] = "88";
        arr['v'] = "888";
        arr['w'] = "9";
        arr['x'] = "99";
        arr['y'] = "999";
        arr['z'] = "9999";

        int inputSize = fio.nextInt();
        for (int i = 1; i <= inputSize; i++) {
            String str = fio.nextLine();
            sb.append("Case #" + i + ": " );
            for (int j = 0; j < str.length(); j++) {
                char currChar = str.charAt(j);
                if (currChar >= ' ' && currChar <= 'z') {
                    String code = arr[currChar];
                    if (sb.charAt(sb.length()-1) == code.charAt(0)) {
                        sb.append(" ").append(code);
                    } else {
                        sb.append(code);
                    }
                }
            }
            sb.append("\n");
        }
        System.out.println(sb.toString());

        fio.close();

    }
}


/**
 * Fast I/O
 * 
 * @source https://www.geeksforgeeks.org/fast-io-in-java-in-competitive-programming/
 */
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


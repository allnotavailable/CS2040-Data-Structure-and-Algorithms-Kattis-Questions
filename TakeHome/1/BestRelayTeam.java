import java.util.*;
import java.io.*;

class BestRelayTeam {
    public static void main(String[] args){
        FastIO fio = new FastIO();

        int numOfRunners = fio.nextInt();
        
        ArrayList<Runner> runnerList = new ArrayList<Runner>();
        double time = 0;
        ArrayList<Integer> newRunnerList = new ArrayList<Integer>();
        double outputTime = Double.POSITIVE_INFINITY;

        for (int i = 0; i< numOfRunners; i++) {  
            String name = fio.next();
            double firstLeg = fio.nextDouble();
            double secondLeg = fio.nextDouble();
            runnerList.add(new Runner(name, firstLeg, secondLeg));
        }

        Collections.sort(runnerList, new SecondLegComp());

        for (int i = 0; i < numOfRunners; i++) {
            ArrayList<Integer> runnerIdx = new ArrayList<Integer>();
            runnerIdx.add(i);
            time = runnerList.get(i).firstLeg;
            int count = 0;
            for (int j = 0; j < 4; j++) {
                if (i != j) {
                    runnerIdx.add(j);
                    time += runnerList.get(j).secondLeg;
                    count++;
                }
                if (count == 3) {
                    break;
                }
            }
            if (outputTime > time) {
                outputTime = time;
                newRunnerList = runnerIdx;
            }
        }

        System.out.println(outputTime);

        for (int i = 0; i < 4; i++) {
            System.out.println(runnerList.get(newRunnerList.get(i)).runnerName);
        }

        fio.close();
    }
}

class Runner { 
    public String runnerName; 
    public double firstLeg; 
    public double secondLeg; 

    public Runner (String runnerName, double firstLeg, double secondLeg) {
        this.runnerName = runnerName;
        this.firstLeg = firstLeg; 
        this.secondLeg = secondLeg; 
    }
}

class SecondLegComp implements Comparator<Runner> {

    public int compare(Runner r1, Runner r2) {
        if (r1.secondLeg - r2.secondLeg > 0) {
            return 1;
        } else if (r1.secondLeg - r2.secondLeg < 0) {
            return -1;
        } else {
            return 0;
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
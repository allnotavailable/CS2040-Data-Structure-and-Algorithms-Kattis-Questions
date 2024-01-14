import java.util.Comparator;
import java.util.PriorityQueue;

class AssigningWorkstations {
    public static void main (String args[]) {
        FastIO fio = new FastIO();

        int numOfResearchers = fio.nextInt();
        int inactivity = fio.nextInt();
        int counter = 0;

        PriorityQueue<Researcher> pqResearchers = new PriorityQueue<Researcher>(numOfResearchers, new ArrivalComp());
        PriorityQueue<Integer> workstations = new PriorityQueue<Integer>();
        for (int i = 0; i < numOfResearchers; i++) {
            int arrivalTime = fio.nextInt();
            int useTime = fio.nextInt();
            pqResearchers.add(new Researcher(arrivalTime, useTime));
        }
        for (int i = 0; i < numOfResearchers; i++) {
            
            Researcher currResearcher = pqResearchers.poll();
            workstations.add(currResearcher.getArrivalTime() + currResearcher.getUseTime());
            while (workstations.size() != 0 && currResearcher.getArrivalTime() > workstations.peek() + inactivity) {
                workstations.poll();
            }
            if (workstations.size() != 0 && currResearcher.getArrivalTime() >= workstations.peek() && 
                currResearcher.getArrivalTime() <= workstations.peek() + inactivity) {
                    workstations.poll();
                    counter++;
            }
        }
        System.out.println(counter);
        fio.close();
    }
}

class Researcher {
    private int arrivalTime;
    private int useTime;

    Researcher(int arrivalTime, int useTime) {
        this.arrivalTime = arrivalTime;
        this.useTime = useTime;
    }

    public int getArrivalTime() {
        return arrivalTime;
    }

    public int getUseTime() {
        return useTime;
    }
}

class ArrivalComp implements Comparator<Researcher> {
    public int compare (Researcher r1, Researcher r2) {
        if (r1.getArrivalTime() > r2.getArrivalTime()) {
            return 1;
        } else if (r1.getArrivalTime() < r2.getArrivalTime()) {
            return -1;
        } else {
            return 0;
        }
    }
}

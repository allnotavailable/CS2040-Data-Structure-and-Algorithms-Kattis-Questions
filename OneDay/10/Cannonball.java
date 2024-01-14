import java.util.ArrayList;
import java.util.Arrays;
import java.util.PriorityQueue;

class Cannonball {
    public static void main (String args[]) {
        FastIO fio = new FastIO();
        double startX = fio.nextDouble();
        double startY = fio.nextDouble();
        double endX = fio.nextDouble();
        double endY = fio.nextDouble();
        int numOfCannons = fio.nextInt();
        ArrayList<Pair> position = new ArrayList<Pair>();
        position.add(new Pair(startX, startY));
        position.add(new Pair(endX, endY));

        double[][] adjMatrix = new double[numOfCannons + 2][numOfCannons + 2];
        for (int i = 0; i < numOfCannons; i++) {
            double cannonX = fio.nextDouble();
            double cannonY = fio.nextDouble();
            position.add(new Pair(cannonX, cannonY));
        }
        adjMatrix[0][1] = getDistance(position.get(0), position.get(1)) / 5;
        adjMatrix[1][0] = adjMatrix[0][1];
        for (int i = 0; i < numOfCannons; i++) {
            double distance = getDistance(position.get(0), position.get(i + 2));
            adjMatrix[0][i + 2] = distance / 5;
            adjMatrix[i + 2][0] = (Math.abs(distance - 50) / 5) + 2;
        }
        for (int i = 0; i < numOfCannons; i++) {
            double distance = getDistance(position.get(1), position.get(i + 2));
            adjMatrix[1][i + 2] = distance / 5;
            adjMatrix[i + 2][1] = (Math.abs(distance - 50) / 5) + 2;
        }
        for (int i = 0; i < numOfCannons; i++) {
            for (int j = 1; j < numOfCannons; j++) {
                double distance = getDistance(position.get(i + 2), position.get(j + 2));
                adjMatrix[i + 2][j + 2] = (Math.abs(distance - 50) / 5) + 2;
                adjMatrix[j + 2][i + 2] = adjMatrix[i + 2][j + 2];
            }
        }
        double[] times = new double[numOfCannons + 2];
		Arrays.fill(times, Double.MAX_VALUE);
		dijkstra(adjMatrix, times);
        System.out.println(times[1]);
        fio.close();
    }

    public static double getDistance(Pair p1, Pair p2) {
        double diffX = Math.abs(p1.x - p2.x);
        double diffY = Math.abs(p1.y - p2.y);
        return Math.hypot(diffX, diffY);
    }

    public static void dijkstra(double[][] adjMatrix, double[] times) {
        PriorityQueue<Vertex> pq = new PriorityQueue<Vertex>();
		int[] visited = new int[times.length];
		pq.add(new Vertex(0, 0));
		while (!pq.isEmpty()) {
			Vertex currVertex = pq.remove();
			if (visited[currVertex.index] == 1) {
				continue;
			}
			visited[currVertex.index] = 1;
			for (int i = 0; i < times.length; i++) {
				if (visited[i] == 0 && i != currVertex.index && currVertex.distance + adjMatrix[currVertex.index][i] < times[i]) {
					times[i] = currVertex.distance + adjMatrix[currVertex.index][i];
					pq.add(new Vertex(i, times[i]));
				}
			}
		}
	}
}

class Vertex implements Comparable<Vertex> {
		
    int index;
	double distance;
		
    public Vertex(int index, double distance) {
		this.index = index;
		this.distance = distance;
	}
		
	public int compareTo(Vertex other) {
		if (distance > other.distance) {
			return 1;
		} else if (distance < other.distance) {
			return -1;
		}
		return 0;
	}
}

class Pair {
    double x;
    double y;

    Pair(double x, double y) {
        this.x = x;
        this.y = y;
    }
}


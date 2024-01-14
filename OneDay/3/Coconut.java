/*  Read input of syllables in the rhyme and number of players
    Initialize the players and their initial state into a linked list.
    From the first player, get the next player by counting syllables.
    Change the state of the player where the syllables stop.
    When player palm is split into two, insert that player new hand to the linked list.
    Remove the player from the list if it reaches the last state.
    Repeat the steps until the list size is smaller equal to 1
    Get the first person in the list that would be the winner.
 */

import java.util.*;
import java.io.*;

class Coconut {
    public static void main (String args[]) {
        FastIO fio = new FastIO();
        int syllables = fio.nextInt();
        int numOfPlayers = fio.nextInt();
        int currPlayerId = 0;

        LinkedList<Player> playerList = new LinkedList<Player>();
        for (int i = 1; i <= numOfPlayers; i++) {
            playerList.add(new Player(i, 1));
        }

        while (playerList.size() > 1) {
            currPlayerId = (currPlayerId + syllables - 1) % playerList.size();
            Player currPlayer = playerList.get(currPlayerId);
            if (currPlayer.getState() == 1) {
                playerList.set(currPlayerId, new Player(currPlayer.getPlayerId(), 2));
                playerList.add(currPlayerId, new Player(currPlayer.getPlayerId(), 2));
            } else if (currPlayer.getState() == 2) {
                playerList.set(currPlayerId, new Player(currPlayer.getPlayerId(), 3));
                currPlayerId++;
            } else {
                playerList.remove(currPlayerId);
            }
        }
        System.out.println(playerList.get(0).getPlayerId());

        fio.close();
    }
}

class Player {
    private int playerId;
    private int state;

    Player(int playerId, int state) {
        this.playerId = playerId;
        this.state = state;
    } 

    public int getPlayerId() {
        return this.playerId;
    }
    
    public int getState() {
        return this.state;
    }

    public boolean isFolded() {
        return this.state == 1;
    }

    public boolean isSplit() {
        return this.state == 2;
    }

    public boolean isPalmDown() {
        return this.state == 3;
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
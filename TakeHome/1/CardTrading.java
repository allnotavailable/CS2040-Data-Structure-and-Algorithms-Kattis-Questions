import java.util.*;
import java.io.*;

class CardTrading {
    
    public static void main (String args[]) {
        FastIO fio = new FastIO();
        ArrayList<Integer> cardList = new ArrayList<Integer>();
        ArrayList<Integer> cardCounts = new ArrayList<Integer>(Collections.nCopies(1000001, 0));
        ArrayList<Card> cardTypes = new ArrayList<Card>();
        long profit = 0;

        int numOfCards = fio.nextInt();
        int typeOfCards = fio.nextInt();
        int combos = fio.nextInt();

        for (int i = 0; i < numOfCards; i++) {
            int cardNumber = fio.nextInt();
            cardList.add(cardNumber);
            cardCounts.set(cardNumber, cardCounts.get(cardNumber) + 1);
        }

        for (int i = typeOfCards; i > 0; i--) {
            long buyPrice = fio.nextLong();
            long sellPrice = fio.nextLong();
            int cardNumber = typeOfCards - i + 1;
            long buy = (2 - cardCounts.get(cardNumber)) * buyPrice;
            long sell = cardCounts.get(cardNumber) * sellPrice;
            cardTypes.add(new Card(cardNumber, buy, sell));
        }
        
        Collections.sort(cardTypes, new Comp());
        for (int i = 0; i < combos; i++) {
            Card nextCard = cardTypes.get(i);
            profit -= nextCard.buy;
        }
        for (int j = combos; j < typeOfCards; j++) {
            Card nextCard = cardTypes.get(j);
            profit += nextCard.sell;
        }
        System.out.println(profit);

    }
}

class Comp implements Comparator<Card> {

    public int compare(Card c1, Card c2) {
        long cost1 = c1.sell + c1.buy;
        long cost2 = c2.sell + c2.buy;
        if (cost1 < cost2) {
            return -1;
        } else if (cost1 > cost2) {
            return 1;
        } else {
            if (c1.buy < c2.buy) {
                return -1;
            } else if (c1.buy > c2.buy) {
                return 1;
            } else {
                return 0;
            }
        }
    }
}

class Card {
    public int type;
    public long buy;
    public long sell;

    Card(int type, long buy, long sell) {
        this.type = type;
        this.buy = buy;
        this.sell = sell;
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

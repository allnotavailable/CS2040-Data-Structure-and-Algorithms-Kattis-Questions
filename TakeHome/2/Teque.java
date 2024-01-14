import java.util.HashMap;

class Teque {
    public static void main (String args[]) {
        FastIO fio = new FastIO();

        HashMap<Integer, Integer> frontHalf = new HashMap<Integer, Integer>();
        HashMap<Integer, Integer> backHalf = new HashMap<Integer, Integer>();
        int frontHead = 0;
		int frontTail = 0;
		int backHead = 0;
		int backTail = 0;
        StringBuilder output = new StringBuilder();

        int operations = fio.nextInt();
        for (int i = 0; i < operations; i++) {
        
            String cmd = fio.next();
            int value = fio.nextInt();
            if (cmd.equals("push_back")) {
                backHalf.put(backTail, value);
                backTail++;
                if (backHalf.size() > frontHalf.size() + 1) {
					frontHalf.put(frontTail, backHalf.get(backHead));
					frontTail++;
					backHead++;
					backHalf.remove(backHead-1);
				}
            } else if (cmd.equals("push_front")) {
                frontHead--;
				frontHalf.put(frontHead,value);
                if (frontHalf.size() > backHalf.size()) {
                    frontTail--;
                    backHead--;
                    backHalf.put(backHead, frontHalf.get(frontTail));
                    frontHalf.remove(frontTail);
                }
			} else if (cmd.equals("push_middle")) {
                if (frontHalf.size() == backHalf.size()) {
                    backHead--;
                    backHalf.put(backHead, value);
                } else {
                    frontHalf.put(frontTail, backHalf.get(backHead));
                    frontTail++;
                    backHalf.put(backHead, value);
                }        
            } else {
                if (value < frontTail - frontHead) {
                    output.append(frontHalf.get(frontHead  + value)).append("\n");
                } else {
                    output.append(backHalf.get(backHead + value - (frontTail - frontHead))).append("\n");
                }
            }
        }
        System.out.println(output);
        fio.close();
    }
}

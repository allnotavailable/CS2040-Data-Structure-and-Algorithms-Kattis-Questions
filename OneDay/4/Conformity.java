import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;

class Conformity {
    
    public static void main (String args[]) {
        FastIO fio = new FastIO();
        int cases = fio.nextInt();
        HashMap<String, Integer> map = new HashMap<String, Integer>();
        for (int i = 0; i < cases; i++) {
            ArrayList<Integer> arrCode = new ArrayList<Integer>();
            for (int j = 0; j < 5; j++) {
                int inputCode = fio.nextInt();
                arrCode.add(inputCode);    
            }
            Collections.sort(arrCode);
            String stringCode = arrCode.toString();
            if (!map.containsKey(stringCode)) {
                map.put(stringCode, 1);
            } else {
                int value = map.get(stringCode);
                value++;
                map.replace(stringCode, value);
            }
        }
        int max = 0;
        int maxAmount = 0;
        Collection<Integer> counts = map.values();
        for (Integer count : counts) {
            if (count > max) {
                max = count;
                maxAmount = count;
            } else if (count == max) {
                maxAmount += count;
            }
        }
        System.out.println(maxAmount);
        fio.close();
    }
}

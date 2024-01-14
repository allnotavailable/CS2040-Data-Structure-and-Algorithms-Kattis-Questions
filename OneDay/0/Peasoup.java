// Timothy Hanny Fusanto A0265878E

import java.util.*;

class Peasoup {
    
    public static void main (String[] args) {
        Scanner sc = new Scanner(System.in);
        String output = "";
        int restNum = sc.nextInt();
        for (int i = 0; i < restNum; i++) {
            boolean hasPancakes = false;
            boolean hasPeasoup = false;
            int menuNum = sc.nextInt();
            sc.nextLine();
            String restName = sc.nextLine();
            for (int j = 0; j < menuNum; j++) {
                String menu = sc.nextLine();
                if (menu.equals("pancakes")) {
                    hasPancakes = true;
                } else if (menu.equals("pea soup")) {
                    hasPeasoup = true;
                }
            }
            if (hasPancakes && hasPeasoup) {
                output = restName;
                break;
            } else {
                output = "Anywhere is fine I guess";
            }
        }
        System.out.println(output);
    }
}
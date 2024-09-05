
package mng;

import java.util.ArrayList;
import java.util.Scanner;
import tools.MyTool;

public class Menu extends ArrayList<String> {

    public Menu() {
        super();
    }

    public Menu(String[] items) {
        super();
        for (String item : items) {
            this.add(item);
        }
    }

    public int getChoice(String title) {
        System.out.println(title);
        boolean valid = true;
        String choice = null;
        int count = 0;
        int n;
        for (int i = 0; i < this.size(); i++) {
            System.out.printf("%d-%s\n", (i + 1), this.get(i));
            count++;
        }
        System.out.println("Choose [1.." + count + "]: ");
        Scanner sc = new Scanner(System.in);
        while (true) {
            try {
                choice = sc.nextLine();
                n = Integer.parseInt(choice);
                break;
            } catch (Exception e) {
            }
        }
        return n;
    }
}

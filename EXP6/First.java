package EXP6;

public class First {
    public static void main(String args[]) throws Exception {
        String left[] = { "E", "A", "T", "B", "F" };
        String right[] = { "TA", "+TA/e", "FB", "*FB/e", "(E)/i" };

        String right1[][] = new String[10][10];

        for (int i = 0; i < right.length; i++) {
            // System.out.println("----"+right[i].split("/"));
            right1[i] = right[i].split("/");
        }

        /*
         * System.out.print(" "+right1[0][0]+" ");
         * System.out.print(" "+right1[1][0]+" ");
         * System.out.print(" "+right1[1][1]+" ");
         * System.out.print(" "+right1[2][0]+" ");
         * System.out.print(" "+right1[3][0]+" ");
         * System.out.print(" "+right1[3][1]+" ");
         * System.out.print(" "+right1[4][0]+" ");
         * System.out.print(" "+right1[4][1]+" \n\n");
         */

        // System.out.println("sssss: "+right1[0].length);

        for (int i = 0; i < right.length; i++) {

            System.out.println(left[i] + " -> " + right[i]);
        }

        System.out.println("\n");
        String FIRST[][] = new String[10][2];

        for (int i = 0; i < left.length; i++) {
            int k = i;

            for (int j = 0; j < right1[k].length; j++) {
                if (right1[k][j].charAt(0) < 'A' || right1[k][j].charAt(0) > 'Z') {
                    FIRST[i][j] = "" + right1[k][j].charAt(0);
                    // System.out.println("****"+FIRST[i][j]);
                } else {
                    for (int p = 0; p < left.length; p++)
                        if (left[p].charAt(0) == (right1[k][j].charAt(0))) {

                            k = p;
                            j = -1;
                            break;
                        }
                }
            }
        }

        for (int i = 0; i < 5; i++) {
            System.out.print("FIRST Of " + left[i] + " => ");
            for (int j = 0; j < FIRST[i].length; j++) {
                System.out.print(" " + FIRST[i][j]);
            }
            System.out.println("");
        }

        /*
         * System.out.println(FIRST[0][0]);
         * System.out.println(FIRST[0][1]);
         */
    }

}
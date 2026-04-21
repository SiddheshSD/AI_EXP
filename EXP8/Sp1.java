package EXP8;

public class Sp1 {

    public static void main(String[] args) {

        String s[][] = { { " ", "START", "101", "" },
                { " ", "MOVER", "BREG", "ONE" },
                { " ", "MOVEM", "BREG", "TERM" },
                { "AGAIN", "MULT", "BREG", "TERM" },
                { " ", "MOVER", "CREG", "ONE" },
                { " ", "ADD", "CREG", "N" },
                { " ", "MOVEM", "CREG", "RESULT" },
                { "N", "DS", "2", "" },
                { "RESULT", "DS", "2", "" },
                { "ONE", "DC", "1", "" },
                { "TERM", "DS", "1", "" },
                { " ", "END", "", "" } };

        for (int i = 0; i < 12; i++) {
            for (int j = 0; j < 4; j++) {
                System.out.print("\t" + s[i][j] + "\t");
            }
            System.out.println();
        }

        String a[][] = new String[5][3];

        int l = 0, c = 0;
        int lc = Integer.parseInt(s[0][2]);

        for (int i = 1; i < 12; i++) {
            if (!s[i][0].equals(" ")) {
                a[c][0] = s[i][0];
                a[c][1] = Integer.toString(lc);

                if (s[i][1] == "DS") {
                    l = Integer.parseInt(s[i][2]);
                    lc = lc + l;
                    a[c][2] = Integer.toString(l);
                } else {
                    lc++;
                    a[c][2] = "1";
                }

                c++;
            } else {
                lc++;
            }
        }

        System.out.println("\n****************************************");
        System.out.println("              Symbol Table              ");
        System.out.println("****************************************");
        System.out.println("\nSymbol\tAddr\tLength");
        for (int i = 0; i < c; i++) {
            for (int j = 0; j < 3; j++) {
                System.out.print(a[i][j] + "\t");
            }
            System.out.println("");
        }

    }

}

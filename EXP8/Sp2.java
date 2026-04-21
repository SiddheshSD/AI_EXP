package EXP8;

public class Sp2 {

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

        String inst[] = { "STOP", "SUB", "MULT", "MOVER", "MOVEM", "COMP", "BC", "DIV", "READ", "PRINT" };
        String reg[] = { "NULL", "AREG", "BREG", "CREG", "DREG" };

        int op[][] = new int[12][3];
        int i, j, k, w, cntl = 0, inc = 1;

        for (i = 1; i < 11; i++) {
            for (j = 1; j < 10; j++) {

                if (s[i][1].equalsIgnoreCase(inst[j])) {
                    op[cntl][0] = j;
                } else if (s[i][1].equalsIgnoreCase("DS")) {
                    inc = Integer.parseInt(s[i][2]);
                } else if (s[i][1].equalsIgnoreCase("DC")) {
                    op[cntl][2] = Integer.parseInt(s[i][2]);
                }
            }

            for (k = 0; k < 5; k++) {
                if (s[i][2].equalsIgnoreCase(reg[k]))
                    op[cntl][1] = k;
            }

            for (w = 0; w < 5; w++) {
                if (s[i][3].equalsIgnoreCase(a[w][0]))
                    op[cntl][2] = Integer.parseInt(a[w][1]);
            }

            cntl = cntl + inc;
        }

        int dcnt = Integer.parseInt(s[0][2]);

        System.out.println("\n****************************************");
        System.out.println("              OBJECT FILE             ");
        System.out.println("****************************************");
        for (i = 0; i < 12; i++) {
            System.out.print(dcnt++);
            for (j = 0; j < 3; j++) {
                System.out.print("\t" + op[i][j] + "\t");
            }
            System.out.println();
        }

    }

}
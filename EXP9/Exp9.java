package EXP9;

public class Exp9 {
    public static void main(String[] args) {
        int k = 0, i = 0, j = 0;
        String ala1[] = new String[3];
        String ala2[] = new String[3];
        String s[][] = { { "macro", "", "", "" },
                { "INCR2", "arg1", "arg2", "arg3" },
                { "", "A", "1", "arg1" },
                { "", "A", "2", "arg2" },
                { "", "A", "3", "arg3" },
                { "MEND", "", "", "" },
                { "INCR2", "DATA1", "DATA2", "DATA3" }
        };

        System.out.println("**************************************");
        System.out.println("ALA for PASS 1 is:");
        System.out.println("**************************************");
        if (s[i][0].equalsIgnoreCase("macro")) {
            for (i = i + 1, j = 1; j < 4; j++) {
                System.out.println("");
                System.out.print("\t" + k + "\t");
                System.out.print("\t" + s[i][j] + "\t");
                System.out.println("");
                ala1[k] = s[i][j];
                k++;
            }
        }

        k = 0;

        System.out.println("**************************************");
        System.out.println("ALA for PASS 2 is:");
        System.out.println("**************************************");
        for (i = i + 1; i < 7; i++) {
            if (s[i][0].equalsIgnoreCase("INCR2")) {
                for (j = 1; j < 4; j++) {
                    System.out.println("");
                    System.out.print("\t" + k + "\t");
                    System.out.print("\t" + s[i][j] + "\t");
                    System.out.println("");
                    ala2[k] = s[i][j];
                    k++;
                }
            }
        }

        for (i = 0; i < 3; i++) {
            System.out.println("");
            System.out.print(ala1[i]);
            System.out.print("\t" + ala2[i] + "\t");
            System.out.println("");
        }
    }
}

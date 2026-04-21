package EXP10;

public class MacroMDT {
    public static void main(String[] args) {
        String m[][] = {
                { "MACRO", "", "", "" },
                { "INCR", "&ARG1", "&ARG2", "&ARG3" },
                { "A", "1", ",", "&ARG1" },
                { "A", "2", ",", "&ARG2" },
                { "A", "3", ",", "&ARG3" },
                { "MEND", "", "", "" },
                { "INCR", "DATA1", "DATA2", "DATA3" },
        };
        String mname = new String();
        String MDT[][] = new String[10][10];
        for (int i = 0; i < 7; i++) {
            if (m[i][0].equalsIgnoreCase("MACRO")) {

                for (int j = 0; j < 5; j++) {

                    if (j == 0) {
                        MDT[j][0] = m[i + 1][0];
                        MDT[j][1] = m[i + 1][1];
                        MDT[j][2] = m[i + 1][2];
                        MDT[j][3] = m[i + 1][3];
                    } else {
                        for (int k = 0; k < 4; k++) {
                            if (m[i + j + 1][k].equalsIgnoreCase("mend")) {
                                MDT[j][0] = "MEND";
                                MDT[j][1] = "";
                                MDT[j][2] = "";
                                MDT[j][3] = "";
                            } else {
                                switch (m[i + j + 1][k]) {
                                    case "&ARG1":
                                        MDT[j][k] = "#1";
                                        break;
                                    case "&ARG2":
                                        MDT[j][k] = "#2";
                                        break;
                                    case "&ARG3":
                                        MDT[j][k] = "#3";
                                        break;
                                    default:
                                        MDT[j][k] = m[i + j + 1][k];
                                        break;
                                }
                            }
                        }
                    }
                }
            }
        }
        System.out.println("\t    Macro Definition Table");

        for (int i = 0; i < 5; i++) {
            for (int j = 0; j < 4; j++) {
                System.out.print("\t" + MDT[i][j]);
            }
            System.out.println("");
        }
    }
}
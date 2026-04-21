package EXP2;

import java.io.*;
import java.util.regex.*;

public class Exp2 {
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new FileReader(
                "C:\\AI_EXP\\EXP2\\Exp22.txt"));

        int flag = 0;
        String word[];
        String line;
        String kw[] = { "main", "public", "static", "int", "for", "while", "do", "continue", "char", "float", "String",
                "double", "if" };
        String op[] = { "+", "-", "*", "/", "=", "%", "<", ">" };
        String sp[] = { "(", ")", "{", "}", "[", "]", ";", "," };

        Pattern id = Pattern.compile("[a-zA-Z][A-Za-z0-9]*");
        Pattern num = Pattern.compile("[0-9]+");

        while ((line = br.readLine()) != null) {
            word = line.split("\\s+");
            // System.out.println(word[0]+" "+word[1]+" "+word[2]);
            for (int i = 0; i < word.length; i++) {
                flag = 0;
                for (int j = 0; j < kw.length; j++)
                    if (kw[j].equals(word[i])) {
                        System.out.println(kw[j] + "\t\t : KEYWORD");
                        flag = 1;
                    }
                for (int j = 0; j < op.length; j++)
                    if (op[j].equals(word[i]))
                        System.out.println(op[j] + "\t\t : OPERATOR");

                for (int j = 0; j < sp.length; j++)
                    if (sp[j].equals(word[i]))
                        System.out.println(sp[j] + "\t\t : SEPARATOR" + "");

                Matcher idm = id.matcher(word[i]);
                Matcher numm = num.matcher(word[i]);

                if (idm.matches() && flag == 0) {
                    System.out.println(word[i] + "\t\t : IDENTIFIER");
                }

                if (numm.matches()) {
                    System.out.println(word[i] + "\t\t : NUMBER");

                }

            }
        }

    }

}
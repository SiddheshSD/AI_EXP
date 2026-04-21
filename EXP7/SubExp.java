package EXP7;

import java.io.*;

public class SubExp {
    public static void main(String args[]) throws IOException {
        Sub r = new Sub();
        r.sub();
    }
}

class Sub {
    void sub() throws IOException {
        File file = new File("sub.txt");

        BufferedReader br = new BufferedReader(new FileReader(file));
        String read = new String();
        StringBuffer sb = new StringBuffer();
        System.out.println("\n input content--");

        while ((read = br.readLine()) != null) {
            System.out.println(read);
            sb.append(read + "\n");
        }

        System.out.println("\n\n output--");
        System.out.println("temp=b*c;");
        int x = sb.indexOf("{");
        int y = sb.indexOf("}");
        sb.replace(x + 1, x + y, "a=10 + temp\n x=temp+5 }");
        System.out.println(sb.toString());

    }
}
package EXP1;
// String starts with 00

import java.io.*;

class Dfa2 {
    public static void main(String arg[]) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        int i, ip, ns, l;
        System.out.println("Enter the length of string :");
        l = Integer.parseInt(br.readLine());
        System.out.print("Enter the String 0/1 :");
        int a[] = new int[l];
        for (i = 0; i < l; i++) {
            a[i] = Integer.parseInt(br.readLine());
        }
        int q[][] = { { 1, 5 }, { 2, 5 }, { 2, 4 }, { 2, 4 }, { 3, 4 }, { 5, 5 } };
        int in = 0, fs = 2, cs = in;

        for (i = 0; i < l; i++) {
            // System.out.print("-->q"+in);
            ip = a[i];
            ns = q[cs][ip];
            cs = ns;
        }
        // System.out.println("Currant State Is="+cs);
        // System.out.println("final State Is="+fs);
        if (cs == fs)
            System.out.println("String is Accepted");
        else
            System.out.println("string is rejected");
    }
}
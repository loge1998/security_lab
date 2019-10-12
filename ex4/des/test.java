import java.util.*;
import java.lang.*;
import java.io.*;

public class test {
    public static void main(String[] args) {
        String result = "1100000010000000110000001000000011000000100000001100000010000000";
        int len = result.length();
        String hex = "";
        int times = len / 4;
        for (int i = 0; i < times; i++) {
            int decimal = Integer.parseInt(result.substring(i * 4, i * 4 + 4), 2);
            hex += Integer.toString(decimal, 16);
        }
        System.out.println(hex);
    }
}

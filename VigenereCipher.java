import java.io.*;
import java.util.*;

public class VigenereCipher {

    public static int arr[][] = new int[26][26];

    public static void printArray(int arr[][],int row,int col)
    {
        for(int i=0;i<row;i++)
        {
            for(int j=0;j<col;j++)
            {
                System.out.print(arr[i][j]+" ");
            }
            System.out.println();
        }
    }


    public static String Encrypt(String plain_text,String key)
    {



        int val=0;
        for(int i=0;i<26;i++)
        {
            for(int j=0;j<26;j++)
            {
                arr[i][j]=((val+j)%26);
            }
            val++;
        }
        //printArray(arr,26,26);

        StringBuilder str = new StringBuilder();
        for(int i=0;i<plain_text.length();i++)
        {
            int col = plain_text.charAt(i)-97;
            int row = key.charAt(i)-97;
            str.append((char)(arr[row][col]+97));
        }
        return str.toString();
    }


    public static String Decrypt(String encrypt,String key)
    {
        System.out.println(encrypt + " "+ key);
        StringBuilder str = new StringBuilder();
        for(int i=0;i<encrypt.length();i++)
        {
            int row = key.charAt(i)-97;
            int val = encrypt.charAt(i)-97;
            for(int j=0;j<26;j++)
            {
                if(arr[row][j]==val)
                {
                    str.append((char)(j+97));
                }
            }
        }
        return str.toString();
    }

    public static void main(String[] args)
    {
        String plain_text,key;
        Scanner in = new Scanner(System.in);
        System.out.print("Plain Text : ");
        plain_text = in.nextLine();
        System.out.print("Enter key : ");
        key = in.nextLine();
        if(plain_text.length() > key.length())
        {
            int diff = plain_text.length()-key.length();
            int pos=0;
            while(diff>0)
            {
                key+=key.charAt(pos);
                pos = (pos+1)%key.length();
                diff--;
            }
        }
        System.out.println("Key " +key);
        String encrypted = Encrypt(plain_text,key);
        System.out.println(encrypted);
        String decrypted = Decrypt(encrypted,key);
        System.out.println(decrypted);    
        in.close();    
        return;
    }

}

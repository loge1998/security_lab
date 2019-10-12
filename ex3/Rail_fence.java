import java.util.*;
import java.io.*;

public class Rail_fence
{

    public static String encrypt(String plain_text,int key)
    {
        Character table[][]= new Character[key][plain_text.length()];

        for(int i=0;i<key;i++)
        {
            for(int j=0;j<plain_text.length();j++)
            {
                table[i][j]='\n';
            }
        }
        boolean down=false;
        int row=0;
        int col=0;
        StringBuilder str = new StringBuilder();
        for(int i=0;i<plain_text.length();i++)
        {

                if(row==0 || row==key-1)
                    down=!down;
                
                table[row][col++]=plain_text.charAt(i);

                if(down)
                    row++;
                else
                    row--;
        }

        for(int i=0;i<key;i++)
        {
            for(int j=0;j<plain_text.length();j++)
            {
                if(table[i][j]!='\n')
                    str.append(table[i][j]);
            }
        }
        


        return str.toString();
    }

    public static String decrypt(String cipher_text,int key)
    {
        Character table[][]= new Character[key][cipher_text.length()];

        for(int i=0;i<key;i++)
        {
            for(int j=0;j<cipher_text.length();j++)
            {
                table[i][j]='\n';
            }
        }
        boolean down=false;
        int row=0;
        int col=0;
        StringBuilder str = new StringBuilder();
        for(int i=0;i<cipher_text.length();i++)
        {
            if (row == 0) 
                down = true; 
            if (row == key-1) 
                down = false;  
            table[row][col++] = '*'; 
            if(down)
                row++;
            else
                row--;
        }
        int index = 0; 
        for (int i=0; i<key; i++) 
            for (int j=0; j<cipher_text.length(); j++) 
                if (table[i][j] == '*' && index<cipher_text.length()) 
                    table[i][j] = cipher_text.charAt(index++); 

        row=0;
        col=0;
        for(int i=0;i<cipher_text.length();i++)
        {
            if (row == 0) 
                down = true; 
            if (row == key-1) 
                down = false;  

            if (table[row][col] != '*') 
                str.append(table[row][col++]);                 

            if(down)
                row++;
            else
                row--;
        }                    
        return str.toString();
    }

    public static void main(String[] args) {
        String plain_text;
        int key;
        Scanner in = new Scanner(System.in);
        System.out.print("Plain Text : ");
        plain_text = in.nextLine();
        System.out.print("Enter key : ");
        key = in.nextInt();
        System.out.println("Key " +key);
        String encrypted = encrypt(plain_text,key);
        System.out.println(encrypted);
        String decrypted = decrypt(encrypted,key);
        System.out.println(decrypted);    
        in.close();    
        return;
        
    }
}
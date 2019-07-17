import java.util.*;
import java.io.*;

public class ColumnarCipher
{
    public static HashMap<Integer,Integer> keyMap =  new HashMap<Integer,Integer>(); 
  
    public static void setPermutationOrder(String key) 
    {             
        for(int i=0; i < key.length(); i++) 
        { 
            keyMap.put((int)key.charAt(i), i);
        } 
    } 


    public static String Encrypt(String plain_text,String key)
    {
        int row,col; 
        StringBuilder cipher = new StringBuilder(); 
          
        /* calculate column of the matrix*/
        col = key.length();  
          
        /* calculate Maximum row of the matrix*/
        row = plain_text.length()/col;  
          
        if (plain_text.length() % col >0) 
            row += 1; 
      
        Character matrix[][] = new Character[row][col]; 
      
        for (int i=0,k=0; i < row; i++) 
        { 
            for (int j=0; j<col; ) 
            { 
                if(k>=plain_text.length() ||  plain_text.charAt(k) == '\0') 
                { 
                    matrix[i][j] = '_';   
                    j++; 
                } 
                  
                else if( Character.isLetter(plain_text.charAt(k)) || plain_text.charAt(k)==' ') 
                {  
                    matrix[i][j] = plain_text.charAt(k); 
                    j++; 
                } 
                k++; 
            } 
        } 
      
        for (Map.Entry<Integer,Integer> entry : keyMap.entrySet())
        { 
            int j=entry.getValue();
            for (int i=0; i<row; i++) 
            { 
                if( Character.isLetter(matrix[i][j]) || matrix[i][j]==' ' || matrix[i][j]=='_') 
                    cipher.append(matrix[i][j]); 
            } 
        } 
      
        return cipher.toString(); 
    }


    public static String Decrypt(String cipher,String key)
    {
        int col = key.length(); 
  
        int row = cipher.length()/col; 
        Character cipherMat[][] = new Character[row][col];  
      
        /* add character into matrix column wise */
        for (int j=0,k=0; j<col; j++) 
            for (int i=0; i<row; i++) 
                cipherMat[i][j] = cipher.charAt(k++); 
      
        /* update the order of key for decryption */
        int index = 0; 
        for (Map.Entry<Integer,Integer> entry : keyMap.entrySet())
        {
            entry.setValue(index++);
        }
       
        Character decCipher[][] = new Character[row][col];  
        int k = 0; 
        for (int l=0,j;l<key.length(); k++) 
        { 
            j = keyMap.get((int)key.charAt(l++)); 
            for (int i=0; i<row; i++) 
            { 
                decCipher[i][k]=cipherMat[i][j]; 
            } 
        } 
      
        StringBuilder msg = new StringBuilder();
        for (int i=0; i<row; i++) 
        { 
            for(int j=0; j<col; j++) 
            { 
                if(decCipher[i][j] != '_') 
                    msg.append(decCipher[i][j]);
            } 
        } 
        return msg.toString(); 
    }


    public static void main(String[] args) {
        String plain_text,key;
        Scanner in = new Scanner(System.in);
        System.out.print("Plain Text : ");
        plain_text = in.nextLine();
        System.out.print("Key : ");
        key = in.nextLine();
        setPermutationOrder(key);
        String encrypted = Encrypt(plain_text,key);
        System.out.println(encrypted);
        String decrypted = Decrypt(encrypted,key);
        System.out.println(decrypted);  
        in.close();      
        return;   
    }
}
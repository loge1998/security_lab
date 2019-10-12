
import java.util.*;
import java.io.*;
import java.lang.*;


public class PlayFair {

    public static char arr[][] = new char[5][5];
    public static HashMap<Character,Integer> rowMap = new HashMap<Character,Integer>();
    public static HashMap<Character, Integer> colMap = new HashMap<Character, Integer>();
    
    public static String encryptString(String plain_text,String key1)
    {
        StringBuilder s = new StringBuilder();

        HashMap<Character,Integer> map1 = new HashMap<Character,Integer>();
        for(int i=0;i<key1.length();i++)
        {
          if(!map1.containsKey(key1.charAt(i)))
          {
            s.append(key1.charAt(i));
            map1.put(key1.charAt(i), 1);
          }
        }
        String key = s.toString();
        System.out.println("Key used for encryption : "+key);
        for(int i=0;i<key.length();i++)
        {
          rowMap.put(key.charAt(i),i/5);
          colMap.put(key.charAt(i),i%5);
          arr[i/5][i%5]=key.charAt(i);  
        }
        int pos = key.length();
        for(int i=0;i<26;i++)
        {
          if(!rowMap.containsKey((char)(i+97)))
          {
            rowMap.put((char)(i+97),pos/5);
            colMap.put((char)(i+97),pos%5);
            arr[pos/5][pos%5]=(char)(i+97);  
            if(pos!=24)
              pos++;          
          }
        }

        for(int i=0;i<5;i++)
        {
          for(int j=0;j<5;j++)
          {
            System.out.print(arr[i][j]+" ");
          }
          System.out.println();
        }

        StringBuilder str1 = new StringBuilder();
        str1.append(plain_text.charAt(0));
        for(int i=1;i<plain_text.length();i++)
        {
          if(str1.charAt(str1.length()-1)!=plain_text.charAt(i))
          {
            str1.append(plain_text.charAt(i));
          }
          else
          {
            str1.append('z');
            str1.append(plain_text.charAt(i));
          }
        }
        if(str1.length()%2!=0)
          str1.append('z');


        String new_text = str1.toString();
        System.out.println("Plain text after digram check "+ new_text);

        StringBuilder encrypted = new StringBuilder();
        for(int i=0;i<new_text.length();i+=2)
        {
          int x1 = rowMap.get(new_text.charAt(i));
          int x2 = colMap.get(new_text.charAt(i+1));
          int x3 = rowMap.get(new_text.charAt(i+1));
          int x4 = colMap.get(new_text.charAt(i));
          if(x1==x3)
          {
            encrypted.append(arr[x1][(x4+1)%5]);
            encrypted.append(arr[x3][(x2+1)%5]);             
          }
          else if(x2==x4)
          {
            encrypted.append(arr[(x1+1)%5][x2]);
            encrypted.append(arr[(x3+1)%5][x4]);             
          }
          else
          {
            encrypted.append(arr[x1][x2]);
            encrypted.append(arr[x3][x4]);  
          }
        }
        return encrypted.toString();
    }


    public static String decryptString(String cipher_text,String key)
    {
      StringBuilder decrypted = new StringBuilder();
      for(int i=0;i<cipher_text.length();i+=2)
      {
        int x1 = rowMap.get(cipher_text.charAt(i));
        int x2 = colMap.get(cipher_text.charAt(i+1));
        int x3 = rowMap.get(cipher_text.charAt(i+1));
        int x4 = colMap.get(cipher_text.charAt(i));
        if(x1==x3)
        {
          if((x4-1)<0)
          decrypted.append(arr[x1][(x4-1+5)%5]);
          else
          decrypted.append(arr[x1][(x4-1)%5]);
          
          if((x2-1)<0)
          decrypted.append(arr[x1][(x2-1+5)%5]);
          else
          decrypted.append(arr[x1][(x2-1)%5]);        
        }
        else if(x2==x4)
        {
          if((x1-1)<0)
            decrypted.append(arr[(x1-1+5)][x2]);
          else
            decrypted.append(arr[(x1-1)][x2]);

          if((x3-1)<0)
            decrypted.append(arr[(x3-1+5)][x4]);
          else
            decrypted.append(arr[(x3-1)][x4]);          
        }
        else
        {
          decrypted.append(arr[x1][x2]);
          decrypted.append(arr[x3][x4]);  
        }
      }
      return decrypted.toString();      
    } 


    public static void main(String[] args) {
		Scanner scan = new Scanner(System.in);
		System.out.print("Enter plain Text :");		
		String plain_text = scan.nextLine();
		System.out.print("Enter encrpytion key :");	
		String key = scan.nextLine();
		System.out.println("Plain Text : "+plain_text);
		String encrypted = encryptString(plain_text,key);
    System.out.println("Cipher Text : "+encrypted);
		System.out.println("Plain Text  After Decrpytion : "+decryptString(encrypted,key));
    }
    
}

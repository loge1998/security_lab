import java.lang.*;
import java.util.*;
import java.io.*;
public class CeasarCipher
{	
	public static String encryptString(String plain_text,int key)
	{
		StringBuilder cipher = new StringBuilder();
		for(int i=0;i<plain_text.length();i++)
		{
			if(Character.isLowerCase(plain_text.charAt(i)))
			{
				char ch = (char)(((int)plain_text.charAt(i)-97+key)%26+97);
				cipher.append(ch);
			}
			else if(Character.isUpperCase(plain_text.charAt(i)))
			{
				char ch = (char)(((int)plain_text.charAt(i)-65+key)%26+65);
				cipher.append(ch);				
			}
			else
			{
				cipher.append(plain_text.charAt(i));				
			}
		}
		return(cipher.toString());
	}

	public static String decryptString(String cipher_text,int key)
	{
		StringBuilder plain_text = new StringBuilder();
		for(int i=0;i<cipher_text.length();i++)
		{
			if(Character.isLowerCase(cipher_text.charAt(i)))
			{
				char v = cipher_text.charAt(i);
				int val = (int)v -97-key;
				if(val<0)
				{
					val+=26;
				}

				char ch = (char)(val+97);
				plain_text.append(ch);
			}
			else if(Character.isUpperCase(cipher_text.charAt(i)))
			{
				char v = cipher_text.charAt(i);
				int val = (int)v -65-key;
				if(val<0)
				{
					val+=26;
				}

				char ch = (char)(val+65);
				plain_text.append(ch);				
			}
			else
			{
				plain_text.append(cipher_text.charAt(i));				
			}
		}
		return(plain_text.toString());
	}

	
	public static void main(String[] args) {

		Scanner scan = new Scanner(System.in);
		System.out.print("Enter plain Text :");		
		String plain_text = scan.nextLine();
		System.out.print("Enter encrpytion key :");	
		int key = scan.nextInt();
		System.out.println("Plain Text : "+plain_text);
		String encrypted = encryptString(plain_text,key);
		System.out.println("Cipher Text : "+encrypted);
		System.out.println("Plain Text  After Decrpytion : "+decryptString(encrypted,key));
	}
}



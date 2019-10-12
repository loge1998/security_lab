import java.io.*;
import java.util.*;
import java.lang.Math;
import java.math.BigDecimal;
import java.math.BigInteger;  

public class RSA
{
	public static int gcd(int a, int b)
	{
		if (a == 0) 
        	return b; 
    	return gcd(b % a, a); 
	}
	public static int textToNum(String text)
	{
		int t,power = 0,number = 0;
		for(int i=text.length()-1;i>=0;i--)
		{
			t = (int)text.charAt(i);
			if(t>=65 && t<=90)
				t -= 64;
			else
				t -= 96;
			number += t*Math.pow(10,power);
			power++;
		}
		return number;
	}
	public static void main(String args[])
	{
		String plainText;
		int p,q,e=-1,d=-1,phi,temp,pnum,n,c;
		System.out.println("Enter two prime numbers");
		Scanner scanner = new Scanner(System. in);
		p = scanner. nextInt();
        q = scanner. nextInt();
		System.out.println("Enter the plain text:");
        Scanner scanner2 = new Scanner(System. in);
        plainText = scanner2. nextLine();
        n = p*q;
        phi = (p-1)*(q-1);
        for(int i=2;i<phi;i++)
        {
        	temp = gcd(i,phi);
        	if(temp==1)
        	{
        		e = i;
        		break;
        	}
        }
        for(int i=2;i<phi;i++)
        {
        	if((i*e)%phi == 1)
        	{
        		d = i;
        		break;
        	}	
        }
        System.out.println("Public key:(" + n + "," + e + ")");
        System.out.println("Private key:(" + d  + "," + p + "," + q + ")");
        pnum = textToNum(plainText);
        System.out.println(pnum);
        c = (int)(Math.pow(pnum,e))%n;
        System.out.println("Encrypt data:" + c);
        BigInteger temp2 = new BigInteger("1");
        temp2 = (BigInteger.valueOf(c)).pow(d);
        BigInteger decrypt = new BigInteger("1");
        decrypt = (temp2).mod(BigInteger.valueOf(n));
        System.out.println("Decrypt data:" + decrypt);
	}
}
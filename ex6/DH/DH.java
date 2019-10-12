import java.io.*;
import java.util.*;
import java.math.*;
import java.math.BigInteger; 

public class DH
{
	public static int power(int x, int y, int p)
	{
		int res = 1;
		x = x%p;
		while(y>0)
		{
			if((y&1) == 1)
			{
				res = (res*x) % p;
			}
			y = y>>1;
			x = (x*x) % p;
		}
		return res;
	}
	public static boolean millerTest(int d, int n)
	{
		int a = 2+(int)(Math.random()%(n-4));
		int x = power(a,d,n);
		if(x==1 || x==n-1)
			return true;
		while(d!=n-1)
		{
			x=(x*x)%n;
			d*=2;
			if(x==1)
			{
				return false;
			}
			if(x==n-1)
				return true;
		}
		return false;
	}
	public static boolean isPrime(int n, int k)
	{
		if(n<=1 || n==4)
			return false;
		if(n<=3)
			return true;
		int d = n-1;
		while(d%2 == 0)
			d/=2;
		for(int i=0;i<k;i++)
		{
			if(!millerTest(d,n))
				return false;
		}
		return true;
	}
	public static void main(String args[])
	{
		int p,g,xa,xb;
		BigInteger ya = new BigInteger("1");
        BigInteger yb = new BigInteger("1");
        BigInteger ka = new BigInteger("1");
        BigInteger kb = new BigInteger("1");
		System.out.println("Enter the prime numebr:");
		Scanner scanner = new Scanner(System.in);
        p = scanner.nextInt();
        while(!isPrime(p,4))
        {
	        System.out.println("Not a prime!Enter the prime number:");
	        p = scanner.nextInt();	
        }
        System.out.println("Enter the primitive root of prime:");
        g = scanner.nextInt();
        System.out.println("Enter the private key of A:");
        xa = scanner.nextInt();
        System.out.println("Enter the private key of B:");
        xb = scanner.nextInt();
        BigInteger temp1 = new BigInteger("1");
        temp1 = (BigInteger.valueOf(g)).pow(xa);
        ya = (temp1).mod(BigInteger.valueOf(p));
        System.out.println(ya);
        BigInteger temp2 = new BigInteger("1");
        temp2 = (BigInteger.valueOf(g)).pow(xb);
        yb = (temp2).mod(BigInteger.valueOf(p));
        System.out.println(yb);
        temp1 = yb.pow(xa);
        ka = (temp1).mod(BigInteger.valueOf(p));
        System.out.println(ka);
        temp1 = ya.pow(xb);
        kb = (temp1).mod(BigInteger.valueOf(p));
        System.out.println(kb);

	}
}

import java.io.*;
import java.util.*;

public class HillCipher {

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
    
    
    public static String Encrypt(String plain_text,int key[][],int n)
    {
        int row=n,column;
        int added=0;
        printArray(key,n,n);
        if(plain_text.length()%n!=0)
        {
            int x = plain_text.length()%n;
            added=x;
            while(x!=0)
            {
                plain_text+='z';
                x--;
            }
        }
        System.out.println(plain_text);
        column = plain_text.length()/n;
        int matrix[][] = new int[row][column];
        int row_id=0,col_id=0;
        for(int i=0;i<plain_text.length();i++)
        {
            if(row_id>=n)
            {
                row_id=0;
                col_id++;
            }
            matrix[row_id][col_id]=(int)(plain_text.charAt(i)-97);
            row_id++;
        }
        printArray(matrix,row,column);
        int encrpyted[][] = new int[n][column];
        
        for(int i=0;i<n;i++)
        {
            for(int j=0;j<column;j++)
            {
                for(int k=0;k<n;k++)
                {
                    encrpyted[i][j]+=key[i][k]*matrix[k][j];
                }
            }
        }
        printArray(encrpyted,n,column);
        StringBuilder str = new StringBuilder();
        int count=0;
        int total = plain_text.length();
        for(int i=0;i<column;i++)
        {
            for(int j=0;j<n;j++)
            {
                count++;
                if((total-added)>=count)
                {
                    int x = encrpyted[j][i]%26;
                    str.append((char)(x+97));
                }

            }
        }
        return str.toString();
    }
    
    public static int inv(int n)
    {
        for(int i=1;i<26;i++)
        {
            if((n*i)%26==1)
            {
                return i;
            }
        }
        return -1;
    }

    public static void getCofactor(int mat[][],int temp[][], int p, int q, int n) 
    {

        int i = 0, j = 0; 
        for (int row = 0; row < n; row++) 
        { 
            for (int col = 0; col < n; col++) 
            { 
                if (row != p && col != q) 
                { 
                    temp[i][j++] = mat[row][col]; 
                    if (j == n - 1) 
                    { 
                        j = 0; 
                        i++; 
                    } 
                } 
            } 
        } 
    } 

    static int determinantOfMatrix(int mat[][], int n,int N) 
    { 
        int D = 0; 

        if (n == 1) 
            return mat[0][0]; 
        
        // To store cofactors 
        int temp[][] = new int[N][N];  
        
        // To store sign multiplier 
        int sign = 1;  

        // Iterate for each element of first row 
        for (int f = 0; f < n; f++) 
        { 

        getCofactor(mat, temp, 0, f, n); 
        D += sign * mat[0][f] * determinantOfMatrix(temp, n - 1, N); 
        sign = -sign; 
        } 

        return D%26; 
    }

    public static void inverseOfMatrix(int key[][],int invkey[][],int n)
    {
        int adj[][] = new int[n][n];
        adjoint(key,adj,n);
        printArray(adj,n,n);
        int det = determinantOfMatrix(key,n,n);
        int inverse_key = inv(det);
        for(int i=0;i<n;i++)
        {
            for(int j=0;j<n;j++)
            {
                adj[i][j]*=inverse_key;
                invkey[i][j]=adj[i][j]%26;
            }
        }

    }
    public static void adjoint(int A[][],int adj[][],int N) 
    { 
        if (N == 1) 
        { 
            adj[0][0] = 1; 
            return; 
        } 
  
        int sign = 1;
        int temp[][] = new int[N][N]; 
    
        for (int i=0; i<N; i++) 
        { 
            for (int j=0; j<N; j++) 
            { 

                getCofactor(A, temp, i, j, N); 

                sign = ((i+j)%2==0)? 1: -1; 

                adj[j][i] = (sign)*(determinantOfMatrix(temp, N-1,N)); 
                if(adj[j][i]<0)
                {
                    adj[j][i]+=26;
                }
            } 
        } 
    } 
  

    
    public static String decrpyt(String Encrypt,int key[][],int n)
    {
        int row=n,column;
        int added=0;
        int invkey[][] = new int[n][n];
        inverseOfMatrix(key, invkey, n);
        printArray(invkey, n, n);
        if(Encrypt.length()%n!=0)
        {
            int x = Encrypt.length()%n;
            added=x;
            while(x!=0)
            {
                Encrypt+='z';
                x--;
            }
        }
        System.out.println(Encrypt);
        column = Encrypt.length()/n;
        int matrix[][] = new int[row][column];
        int row_id=0,col_id=0;
        for(int i=0;i<Encrypt.length();i++)
        {
            if(row_id>=n)
            {
                row_id=0;
                col_id++;
            }
            matrix[row_id][col_id]=(int)(Encrypt.charAt(i)-97);
            row_id++;
        }
        printArray(matrix,row,column);
        int decrpyted[][] = new int[n][column];
        
        for(int i=0;i<n;i++)
        {
            for(int j=0;j<column;j++)
            {
                for(int k=0;k<n;k++)
                {
                    decrpyted[i][j]+=invkey[i][k]*matrix[k][j];
                }
            }
        }
        printArray(decrpyted,n,column);
        StringBuilder str = new StringBuilder();
        int count=0;
        int total = Encrypt.length();
        for(int i=0;i<column;i++)
        {
            for(int j=0;j<n;j++)
            {
                count++;
                if((total-added)>=count)
                {
                    int x = decrpyted[j][i]%26;
                    str.append((char)(x+97));
                }
            }
        }
        return str.toString();
    }
    
    
    
    
    public static void main(String[] args)
    {
        String plain_text;
        Scanner in = new Scanner(System.in);
        System.out.print("Plain Text : ");
        plain_text = in.nextLine();
        System.out.print("Enter matrix size");
        int n;
        n = in.nextInt();
        int key[][] = new int[n][n];
        System.out.println("enter key matrix");
        for(int i=0;i<n;i++)
        {
            for(int j=0;j<n;j++)
            {
                key[i][j]=in.nextInt();
            }
        }
        String encrypted = Encrypt(plain_text,key,n);
        System.out.println(encrypted);
        String decrypted = decrpyt(encrypted,key, n);
        System.out.println(decrypted);        
        return;
    }
    
}

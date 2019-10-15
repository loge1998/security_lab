import java.util.*;
import java.nio.*;
import java.lang.*;

public class test {
    private  int INIT_A = 0x67452301;
    private  int INIT_B = 0xEFCDAB89;
    private  int INIT_C = 0x98BADCFE;
    private  int INIT_D = 0x10325476;
    private  int INIT_E = 0xC3D2E1F0;


    public String  encrypt(byte[] message) {
        int messageLenBytes = message.length;
        int totalLen = (messageLenBytes*8)+64;
        int  numBlocks = (totalLen)%512==0? totalLen/512:(totalLen/512)+1;
        int paddingLength = numBlocks*512 - totalLen;
        byte[] TotalMessage = new byte[numBlocks*64];
        for(int i=0;i<messageLenBytes;i++)
        {
            TotalMessage[i]=message[i];
        }
        TotalMessage[messageLenBytes]=(byte)0x80;//10000000
        long MessageLength = messageLenBytes*8;
        byte[] LenBytes = ByteBuffer.allocate(8).putLong(MessageLength).array();
        for(int i=0;i<8;i++)
        {
            TotalMessage[TotalMessage.length-8+i] = LenBytes[i];
        }


        int buffer[] = new int[80];

        for(int i=0;i<numBlocks;i++)
        {
          int index = i*64;
          for(int j=0;j<16;j++)
          {
            byte[] temp = new byte[4];
            for(int k=0;k<4;k++)
            {
              temp[k]=TotalMessage[index+4*j+k];
            }
            buffer[j]= ByteBuffer.wrap(temp).getInt();
          }

          for(int j=16;j<80;j++)
          {
            buffer[j] = buffer[j-3] ^ buffer[j-8] ^ buffer[j-14] ^ buffer[j-16];
            buffer[j]=Integer.rotateLeft(buffer[j], 1);
          }

          int a = INIT_A;
          int b = INIT_B;
          int c = INIT_C;
          int d = INIT_D;
          int e = INIT_E;
          int f=0;
          int k=0;
          for(int j=0;j<80;j++)
          {
            if(j >= 0 && j <= 19)
            {
              f = ( b & c) | (~b & d);
              k=0x5A827999;
            }
            else if(j >= 20 && j <= 39)
            {
              f = b ^ c ^ d;
              k = 0x6ED9EBA1;
            }
            else if(j >= 40 && j <= 59)
            {
              f = (b&c)|(b&d)|(c&d);
              k = 0x8F1BBCDC;
            }
            else if (j >= 60 && j <= 79)
            {
              f = b ^ c ^ d;
              k = 0xCA62C1D6;
            }
            int temp = Integer.rotateLeft(a, 5) + f + e + k +buffer[j];
            e=d;
            d=c;
            c= Integer.rotateLeft(b, 30);
            b=a;
            a=temp;
          }
          INIT_A+=a;
          INIT_B+=b;
          INIT_C+=c;
          INIT_D+=d;
          INIT_E+=e;
        }
        String ans = Integer.toHexString(INIT_A)+Integer.toHexString(INIT_B)
                      +Integer.toHexString(INIT_C)+Integer.toHexString(INIT_D)
                      +Integer.toHexString(INIT_E);

        return ans;
    }

    public static void main(String[] args) {
      Scanner scan = new Scanner(System.in);
      test t = new test();
      System.out.println("Enter the plainText:");
      Scanner scanner = new Scanner(System.in);
      String plainText = scanner.nextLine();
      System.out.println(t.encrypt(plainText.getBytes()));
    }
}

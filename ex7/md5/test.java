import java.util.*;
import java.nio.*;
import java.lang.*;

public class test {

        private static final int INIT_A = 0x67452301;
        private static final int INIT_B = 0xEFCDAB89;
        private static final int INIT_C = 0x98BADCFE;
        private static final int INIT_D = 0x10325476;

    private static final int[] SHIFT_AMTS = { 7, 12, 17, 22, 7, 12, 17, 22, 7, 12, 17, 22, 7, 12, 17, 22, 5, 9, 14, 20,
            5, 9, 14, 20, 5, 9, 14, 20, 5, 9, 14, 20, 4, 11, 16, 23, 4, 11, 16, 23, 4, 11, 16, 23, 4, 11, 16, 23, 6, 10,
            15, 21, 6, 10, 15, 21, 6, 10, 15, 21, 6, 10, 15, 21, 7, 12, 17, 22, 5, 9, 14, 20, 4, 11, 16, 23, 6, 10, 15,
            21 };

    private int[] tabel_t = new int[64];

    public byte[] ComputeMD5(byte[] message) {
        for (int i = 0; i < 64; i++) {
            tabel_t[i] = (int) (long) (Math.pow(2, 32) * Math.abs(Math.sin(i + 1)));
        }
        int messageLenBytes = message.length;
        int totalLen = (messageLenBytes*8)+64;
        int numBlocks = (totalLen)%512==0? totalLen/512:(totalLen/512)+1;
        int paddingLength = numBlocks*512 - totalLen;
        byte[] TotalMessage = new byte[numBlocks*64];
        System.out.println(TotalMessage.length*8+" "+paddingLength+" "+messageLenBytes);
        for(int i=0;i<messageLenBytes;i++)
        {
            TotalMessage[i]=message[i];
        }
        TotalMessage[messageLenBytes]=(byte)0x80;//10000000
        long MessageLength = messageLenBytes*8;
        byte[] LenBytes = ByteBuffer.allocate(8).putLong(MessageLength).array();
        for(int i=0;i<8;i++)
        {
            TotalMessage[TotalMessage.length-8+i] = LenBytes[7-i];
        }

        int a = INIT_A;
        int b = INIT_B;
        int c = INIT_C;
        int d = INIT_D;

        int buffer[] = new int[16];

        for(int no =0;no<numBlocks;no++)
        {
            int index = no*64;
            for(int i=0;i<16;i++)
            {
                byte[] temp = new byte[4];
                for(int j=0;j<4;j++)
                {
                    temp[j]=TotalMessage[index+4*i+j];
                }
                buffer[i]=ByteBuffer.wrap(temp).order(ByteOrder.LITTLE_ENDIAN).getInt();
            }
            int orgA = a;
            int orgB = b;
            int orgC = c;
            int orgD = d;
            for(int i=0;i<64;i++)
            {
                int div16 = i/16;
                int f=0;
                int bufferIndex=i;
                switch (div16) {
                    case 0:
                        f= (b&c)|(~b&d);
                        break;
                    case 1:
                        f=(b&d)|(c&~d);
                        bufferIndex = (bufferIndex*5+1)%16;
                        break;
                    case 2:
                        f=b^c^d;
                        bufferIndex = (bufferIndex*3+5)%16;
                        break;
                    case 3:
                        f=(c^(b|~d));
                        bufferIndex = (bufferIndex*7)%16;
                        break;
                }
                int temp = b+Integer.rotateLeft(a+f+tabel_t[i]+buffer[bufferIndex],SHIFT_AMTS[i]);
                a=d;
                d=c;
                c=b;
                b=temp;
            }
            a+=orgA;
            b+=orgB;
            c+=orgC;
            d+=orgD;

        }

        byte[] md5 = ByteBuffer.allocate(16)
                    .order(ByteOrder.LITTLE_ENDIAN).putInt(a)
                    .order(ByteOrder.LITTLE_ENDIAN).putInt(b)
                    .order(ByteOrder.LITTLE_ENDIAN).putInt(c)
                    .order(ByteOrder.LITTLE_ENDIAN).putInt(d)
                    .array();
        return md5;
    }


    public String toHexString(byte[] message)
    {
        String ans ="";
        for(int i=0;i<message.length;i++)
        {
            ans+=String.format("%02X",message[i]);
        }
        return ans;
    }

    public static void main(String[] args) {
        test t = new test();
        String plainText;
        System.out.println("Enter the plainText:");
        Scanner scanner = new Scanner(System.in);
        plainText = scanner.nextLine();
        System.out.println(t.toHexString(t.ComputeMD5(plainText.getBytes())));
    }
}

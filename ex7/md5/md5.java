import java.nio.*;
import java.util.*;

public class md5 {
  private static final int INIT_A = 0x67452301;
  private static final int INIT_B = 0xEFCDAB89;
  private static final int INIT_C = 0x98BADCFE;
  private static final int INIT_D = 0x10325476;

  private static final int[] SHIFT_AMTS = { 7, 12, 17, 22, 7, 12, 17, 22, 7, 12, 17, 22, 7, 12, 17, 22, 5, 9, 14, 20, 5,
      9, 14, 20, 5, 9, 14, 20, 5, 9, 14, 20, 4, 11, 16, 23, 4, 11, 16, 23, 4, 11, 16, 23, 4, 11, 16, 23, 6, 10, 15, 21,
      6, 10, 15, 21, 6, 10, 15, 21, 6, 10, 15, 21, 7, 12, 17, 22, 5, 9, 14, 20, 4, 11, 16, 23, 6, 10, 15, 21 };

  private int[] TABLE_T = new int[64];

  public byte[] computeMD5(byte[] message) {
    for (int i = 0; i < 64; i++)
      TABLE_T[i] = (int) (long) ((Math.pow(2, 32) * Math.abs(Math.sin(i + 1))));
    int messageLenBytes = message.length;
    int numBlocks = ((messageLenBytes + 8) >>> 6) + 1; // 64
    int totalLen = numBlocks * 64;
    byte[] paddingBytes = new byte[totalLen - messageLenBytes];
    paddingBytes[0] = (byte) 0x80;// 128
    /*
     * System.out.println("message : " + messageLenBytes * 8 + " " + numBlocks + " "
     * + totalLen * 8 + " " + ((paddingBytes.length * 8) - 64));
     */
    long messageLenBits = (long) messageLenBytes * 8;
    byte[] temp1 = ByteBuffer.allocate(8).putLong(messageLenBits).array();
    for (int i = 0; i < 8; i++) {
      paddingBytes[paddingBytes.length - 8 + i] = temp1[7 - i];
    }

    /*
     * System.out.println(Integer.parseInt(toHexString(message).substring(0, 8),
     * 16)); System.out.println(toHexString(paddingBytes));
     */
    int a = INIT_A;
    int b = INIT_B;
    int c = INIT_C;
    int d = INIT_D;

    int[] buffer = new int[16];
    byte[] total = ByteBuffer.allocate(message.length + paddingBytes.length).put(message).put(paddingBytes).array();

    /*
     * for (int i = 0; i < 16; i++) { byte[] bt = new byte[4]; for (int j = 0; j <
     * 4; j++) { bt[j] = total[4 * i + j]; } buffer[i] =
     * ByteBuffer.wrap(bt).order(ByteOrder.LITTLE_ENDIAN).getInt(); }
     */

    for (int k = 0; k < numBlocks; k++) {
      int index = k * 64;
      /*
       * for (int j = 0; j < 64; j++, index++) { // System.out.println((j >>> 2) + " "
       * + message[index] + " " + buffer[j >>> 2]); buffer[j >>> 2] = ((int) ((index <
       * messageLenBytes) ? message[index] : paddingBytes[index - messageLenBytes]) <<
       * 24) | (buffer[j >>> 2] >>> 8); }
       */
      for (int i = 0; i < 16; i++) {
        byte[] bt = new byte[4];
        for (int j = 0; j < 4; j++) {
          bt[j] = total[index + 4 * i + j];
        }
        buffer[i] = ByteBuffer.wrap(bt).order(ByteOrder.LITTLE_ENDIAN).getInt();
      }
      for (int i = 0; i < 16; i++) {
        System.out.println(buffer[i]);
      }

      int originalA = a;
      int originalB = b;
      int originalC = c;
      int originalD = d;
      for (int j = 0; j < 64; j++) {
        int div16 = j / 16;
        int f = 0;
        int bufferIndex = j;
        switch (div16) {
        case 0:
          f = (b & c) | (~b & d);
          break;

        case 1:
          f = (b & d) | (c & ~d);
          bufferIndex = (bufferIndex * 5 + 1) % 16;
          break;

        case 2:
          f = b ^ c ^ d;
          bufferIndex = (bufferIndex * 3 + 5) % 16;
          break;

        case 3:
          f = c ^ (b | ~d);
          bufferIndex = (bufferIndex * 7) % 16;
          break;
        }
        System.out.println(bufferIndex);
        int temp = b + Integer.rotateLeft(a + f + buffer[bufferIndex] + TABLE_T[j], SHIFT_AMTS[j]);
        a = d;
        d = c;
        c = b;
        b = temp;

      }

      a += originalA;
      b += originalB;
      c += originalC;
      d += originalD;
    }

    /*
     * for (int i = 0; i < 16; i++) System.out.println(buffer[i]);
     */

    byte[] md5 = new byte[16];
    int count = 0;
    for (int i = 0; i < 4; i++) {
      int n;
      if (i == 0)
        n = a;
      else if (i == 1)
        n = b;
      else if (i == 2)
        n = c;
      else
        n = d;
      byte[] temp = ByteBuffer.allocate(4).putInt(n).array();
      for (int j = 0; j < 4; j++) {
        md5[count++] = temp[3 - j];
      }
    }

    byte[] md5temp = ByteBuffer.allocate(16).order(ByteOrder.LITTLE_ENDIAN).putInt(a).order(ByteOrder.LITTLE_ENDIAN)
        .putInt(b).order(ByteOrder.LITTLE_ENDIAN).putInt(c).order(ByteOrder.LITTLE_ENDIAN).putInt(d).array();

    return md5;
  }

  public static String toHexString(byte[] b) {
    StringBuilder sb = new StringBuilder();
    for (int i = 0; i < b.length; i++) {
      sb.append(String.format("%02X", b[i]));
    }
    return sb.toString();
  }

  public static void main(String[] args) {
    String plainText;
    System.out.println("Enter the plainText:");
    Scanner scanner = new Scanner(System.in);
    plainText = scanner.nextLine();
    md5 md5 = new md5();
    System.out.println("0x" + toHexString(md5.computeMD5(plainText.getBytes())) + " <== \"" + plainText + "\"");
    return;
  }
}

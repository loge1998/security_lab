import java.io.*;
import java.util.*;

public class MD5 {
	private static final int[] SHIFT_AMTS = { 7, 12, 17, 22, 5, 9, 14, 20, 4, 11, 16, 23, 6, 10, 15, 21, 5, 9, 14, 20,
			5, 9, 14, 20, 5, 9, 14, 20, 5, 9, 14, 20, 4, 11, 16, 23, 4, 11, 16, 23, 4, 11, 16, 23, 4, 11, 16, 23, 6, 10,
			15, 21, 6, 10, 15, 21, 6, 10, 15, 21, 6, 10, 15, 21 };

	public static void main(String args[]) {
		/*
		 * String message; /*System.out.println("Enter the message:"); Scanner scanner =
		 * new Scanner(System. in); message = scanner.nextLine(); message =
		 * "attackattwoamtomorrowswarajismybirthrightattackattwoamtomorrowswarajismybirthright";
		 * byte[] bytes = message.getBytes(); StringBuilder pText = new StringBuilder();
		 * for (byte b : bytes) { int val = b; for (int i = 0; i < 8; i++) {
		 * pText.append((val & 128) == 0 ? 0 : 1); val <<= 1; } }
		 * System.out.println(pText); int mLength = pText.length();
		 * System.out.println(mLength); String mLenString =
		 * Integer.toBinaryString(mLength); System.out.println(mLenString); int lenOfMsg
		 * = mLenString.length(); int msgPad = 64 - lenOfMsg; StringBuilder binMsgLength
		 * = new StringBuilder(); for(int i=0;i<msgPad;i++) { binMsgLength.append(0); }
		 * binMsgLength.append(mLenString); System.out.println(binMsgLength);
		 * System.out.println(binMsgLength.length()); int padLength = (mLength +
		 * 64)%512; if(padLength!=0) { padLength = 512 - padLength; }
		 * System.out.println(padLength); StringBuilder finalMessage = new
		 * StringBuilder(); finalMessage.append(pText); finalMessage.append(1); for(int
		 * i=0;i<padLength-1;i++) { finalMessage.append(0); }
		 * finalMessage.append(binMsgLength); System.out.println(finalMessage);
		 * System.out.println(finalMessage.length());
		 */
		/*
		 * int[] TABLE_T = new int[64]; System.out.println(1L << 32); for (int i = 0; i
		 * < 64; i++) { System.out.println((int) (long) ((Math.pow(2, 32) *
		 * Math.abs(Math.sin(i + 1))))); System.out.println((int) (long) ((1L << 32) *
		 * Math.abs(Math.sin(i + 1)))); }
		 * 
		 * // TABLE_T[i] = (int) (long) ((1L << 32) * Math.abs(Math.sin(i + 1)));
		 * 
		 * /* for (int i : TABLE_T) { System.out.printf("%02X\n", i); }
		 */

		int messageLenBytes = 50;
		int numBlocks = ((messageLenBytes + 8) >>> 6) + 1;
		System.out.println(Integer.toBinaryString(messageLenBytes + 8));
		System.out.println((messageLenBytes + 8) >>> 6);
		int totalLen = numBlocks * 64;

		byte[] paddingBytes = new byte[totalLen - messageLenBytes];
		paddingBytes[0] = (byte) 0x80;// 128
		System.out.println(numBlocks + " " + totalLen);
		long message = 512;
		String value = Long.toBinaryString(message);
		String zeos = "0000000000000000000000000000000000000000000000000000000000000000"; // String of 64 zeros
		String bal = zeos.substring(value.length()) + value;
		System.out.println(bal);
		for (int i = 0; i < 8; i++) {
			System.out.println((byte) message);
			message >>>= 8;
		}
		System.out.print(SHIFT_AMTS.length);
	}
}
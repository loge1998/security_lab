import java.io.*;
import java.util.*;

public class DES {

	String[] Keys = new String[16];

	int[] PC1 = { 57, 49, 41, 33, 25, 17, 9, 1, 58, 50, 42, 34, 26, 18, 10, 2, 59, 51, 43, 35, 27, 19, 11, 3, 60, 52,
			44, 36, 63, 55, 47, 39, 31, 23, 15, 7, 62, 54, 46, 38, 30, 22, 14, 6, 61, 53, 45, 37, 29, 21, 13, 5, 28, 20,
			12, 4 };

	int[] PC2 = { 14, 17, 11, 24, 1, 5, 3, 28, 15, 6, 21, 10, 23, 19, 12, 4, 26, 8, 16, 7, 27, 20, 13, 2, 41, 52, 31,
			37, 47, 55, 30, 40, 51, 45, 33, 48, 44, 49, 39, 56, 34, 53, 46, 42, 50, 36, 29, 32 };

	int[] IP = { 58, 50, 42, 34, 26, 18, 10, 2, 60, 52, 44, 36, 28, 20, 12, 4, 62, 54, 46, 38, 30, 22, 14, 6, 64, 56,
			48, 40, 32, 24, 16, 8, 57, 49, 41, 33, 25, 17, 9, 1, 59, 51, 43, 35, 27, 19, 11, 3, 61, 53, 45, 37, 29, 21,
			13, 5, 63, 55, 47, 39, 31, 23, 15, 7 };

	int[] FP = { 40, 8, 48, 16, 56, 24, 64, 32, 39, 7, 47, 15, 55, 23, 63, 31, 38, 6, 46, 14, 54, 22, 62, 30, 37, 5, 45,
			13, 53, 21, 61, 29, 36, 4, 44, 12, 52, 20, 60, 28, 35, 3, 43, 11, 51, 19, 59, 27, 34, 2, 42, 10, 50, 18, 58,
			26, 33, 1, 41, 9, 49, 17, 57, 25 };

	int[] E = { 32, 1, 2, 3, 4, 5, 4, 5, 6, 7, 8, 9, 8, 9, 10, 11, 12, 13, 12, 13, 14, 15, 16, 17, 16, 17, 18, 19, 20,
			21, 20, 21, 22, 23, 24, 25, 24, 25, 26, 27, 28, 29, 28, 29, 30, 31, 32, 1 };

	int[] P = { 16, 7, 20, 21, 29, 12, 28, 17, 1, 15, 23, 26, 5, 18, 31, 10, 2, 8, 24, 14, 32, 27, 3, 9, 19, 13, 30, 6,
			22, 11, 4, 25 };

	final int[][][] S = { { // S[0]
			{ 14, 4, 13, 1, 2, 15, 11, 8, 3, 10, 6, 12, 5, 9, 0, 7 },
			{ 0, 15, 7, 4, 14, 2, 13, 1, 10, 6, 12, 11, 9, 5, 3, 8 },
			{ 4, 1, 14, 8, 13, 6, 2, 11, 15, 12, 9, 7, 3, 10, 5, 0 },
			{ 15, 12, 8, 2, 4, 9, 1, 7, 5, 11, 3, 14, 10, 0, 6, 13 } },
			{ // S[1]
					{ 15, 1, 8, 14, 6, 11, 3, 4, 9, 7, 2, 13, 12, 0, 5, 10 },
					{ 3, 13, 4, 7, 15, 2, 8, 14, 12, 0, 1, 10, 6, 9, 11, 5 },
					{ 0, 14, 7, 11, 10, 4, 13, 1, 5, 8, 12, 6, 9, 3, 2, 15 },
					{ 13, 8, 10, 1, 3, 15, 4, 2, 11, 6, 7, 12, 0, 5, 14, 9 } },
			{ // S[2]
					{ 10, 0, 9, 14, 6, 3, 15, 5, 1, 13, 12, 7, 11, 4, 2, 8 },
					{ 13, 7, 0, 9, 3, 4, 6, 10, 2, 8, 5, 14, 12, 11, 15, 1 },
					{ 13, 6, 4, 9, 8, 15, 3, 0, 11, 1, 2, 12, 5, 10, 14, 7 },
					{ 1, 10, 13, 0, 6, 9, 8, 7, 4, 15, 14, 3, 11, 5, 2, 12 } },
			{ // S[3]
					{ 7, 13, 14, 3, 0, 6, 9, 10, 1, 2, 8, 5, 11, 12, 4, 15 },
					{ 13, 8, 11, 5, 6, 15, 0, 3, 4, 7, 2, 12, 1, 10, 14, 9 },
					{ 10, 6, 9, 0, 12, 11, 7, 13, 15, 1, 3, 14, 5, 2, 8, 4 },
					{ 3, 15, 0, 6, 10, 1, 13, 8, 9, 4, 5, 11, 12, 7, 2, 14 } },
			{ // S[4]
					{ 2, 12, 4, 1, 7, 10, 11, 6, 8, 5, 3, 15, 13, 0, 14, 9 },
					{ 14, 11, 2, 12, 4, 7, 13, 1, 5, 0, 15, 10, 3, 9, 8, 6 },
					{ 4, 2, 1, 11, 10, 13, 7, 8, 15, 9, 12, 5, 6, 3, 0, 14 },
					{ 11, 8, 12, 7, 1, 14, 2, 13, 6, 15, 0, 9, 10, 4, 5, 3 } },
			{ // S[5]
					{ 12, 1, 10, 15, 9, 2, 6, 8, 0, 13, 3, 4, 14, 7, 5, 11 },
					{ 10, 15, 4, 2, 7, 12, 9, 5, 6, 1, 13, 14, 0, 11, 3, 8 },
					{ 9, 14, 15, 5, 2, 8, 12, 3, 7, 0, 4, 10, 1, 13, 11, 6 },
					{ 4, 3, 2, 12, 9, 5, 15, 10, 11, 14, 1, 7, 6, 0, 8, 13 } },
			{ // S[6]
					{ 4, 11, 2, 14, 15, 0, 8, 13, 3, 12, 9, 7, 5, 10, 6, 1 },
					{ 13, 0, 11, 7, 4, 9, 1, 10, 14, 3, 5, 12, 2, 15, 8, 6 },
					{ 1, 4, 11, 13, 12, 3, 7, 14, 10, 15, 6, 8, 0, 5, 9, 2 },
					{ 6, 11, 13, 8, 1, 4, 10, 7, 9, 5, 0, 15, 14, 2, 3, 12 } },
			{ // S[7]
					{ 13, 2, 8, 4, 6, 15, 11, 1, 10, 9, 3, 14, 5, 0, 12, 7 },
					{ 1, 15, 13, 8, 10, 3, 7, 4, 12, 5, 6, 11, 0, 14, 9, 2 },
					{ 7, 11, 4, 1, 9, 12, 14, 2, 0, 6, 10, 13, 15, 3, 5, 8 },
					{ 2, 1, 14, 7, 4, 10, 8, 13, 15, 12, 9, 0, 3, 5, 6, 11 } } };

	final int[] NumLeftShifts = { 1, 1, 2, 2, 2, 2, 2, 2, 1, 2, 2, 2, 2, 2, 2, 1 };

	public String DoPermutation(String str, int arr[]) {
		String res = "";
		for (int i : arr) {
			res += str.charAt(i - 1);
		}
		return res;
	}

	public String XOR(String one, String two) {
		StringBuilder sb = new StringBuilder();
		for (int i = 0; i < one.length(); i++) {
			sb.append((one.charAt(i) ^ two.charAt(i)));
		}
		return sb.toString();
	}

	public String SBOX(String result) {
		String binaryTarget = "";
		for (int i = 0; i < 8; i++) {
			String temp = result.substring(i * 6, i * 6 + 6);
			String rowStr = String.valueOf(temp.substring(0, 1) + temp.substring(5, 6));
			String colStr = String.valueOf(temp.substring(1, 5));
			int row = Integer.parseInt(rowStr, 2);
			int col = Integer.parseInt(colStr, 2);
			int target = S[i][row][col];
			binaryTarget += String.format("%4s", Integer.toBinaryString(target)).replace(' ', '0');
		}
		return binaryTarget;
	}

	public void keyGenerate(String originalKey) {
		System.out.println("Original Key in plain text :" + originalKey);
		String BinaryKey = strTobin(originalKey);
		System.out.println("Original Key in binary form :" + BinaryKey);
		BinaryKey = BinaryKey.replace(" ", "");
		String permutatedKey = DoPermutation(BinaryKey, PC1);
		System.out.println("56 bit Original key:    " + permutatedKey + " " + permutatedKey.length());
		/*
		 * Next, split this key into left and right halves, LK and RK, where each half
		 * has 28 bits.
		 */
		String Lkey = permutatedKey.substring(0, 28);
		String Rkey = permutatedKey.substring(28, 56);
		int Index = 0;
		for (int i = 0; i < 16; i++) {
			int shift = NumLeftShifts[i];
			Lkey = CircularLeftShift(Lkey, shift);
			Rkey = CircularLeftShift(Rkey, shift);
			Keys[Index] = Lkey + Rkey;
			Index++;
		}
		/* Then build the 16 48-bit sub keys using the PC-2 Permutation table */
		Index = 0;
		for (String key : Keys) {
			Keys[Index] = DoPermutation(key, PC2);
			Index++;
		}
	}

	public String OperationF(String PrevRight, String key) {
		PrevRight = DoPermutation(PrevRight, E);
		String result = XOR(key, PrevRight);
		String binaryTarget = SBOX(result);
		String function = DoPermutation(binaryTarget, P);
		return function;
	}

	public String Encryption(String plaintext, String originalKey) {
		keyGenerate(originalKey);
		// ENCRYPTION
		System.out.println("Original Plain Text: " + plaintext);
		String BinaryText = strTobin(plaintext);
		System.out.println("Plain Text in Binary: " + BinaryText);
		BinaryText = BinaryText.replace(" ", "");
		String encipher = "", finalResult = "";
		// Use plainTextBinary to form a loop
		int plainTextLength = BinaryText.length();
		if (plainTextLength < 64) {
			System.out.println(
					"Your Plain text is " + plainTextLength + "-bits long. It must be atleast 64-bits(8 characters)");
			System.exit(0);
		} else {
			int leftpadString = plainTextLength % 64;
			int loop = leftpadString != 0 ? plainTextLength / 64 + 1 : plainTextLength / 64;
			int start = 0;
			int end = 64;
			for (int id = loop, wordCount = 1; id > 0; id--, wordCount++) {
				int leftpad = 64 - leftpadString;
				int leftSpace = leftpad % 64 != 0 ? leftpad / 8 : 0;
				end = plainTextLength - start < 64 ? plainTextLength : end;
				String plainTextBinary = BinaryText.substring(start, end);
				while (plainTextBinary.length() != 64) {
					plainTextBinary = "0" + plainTextBinary;
				}

				/* Make an Initial Permutation on the plain text */
				String IPBinary = DoPermutation(plainTextBinary, IP);

				/* Divide the permuted block into two halves of 32 bits */
				String LeftIPBinary = IPBinary.substring(0, 32);
				String RightIPBinary = IPBinary.substring(32, 64);

				/* Now, proceed through 16 Rounds/iterations using a function f */
				// There are 16 keys thus 16 rounds, as expected
				int counter = 1;
				for (String k : Keys) {
					System.out.println();
					System.out.println("64-bit " + wordCount + " ENCRYPTION ROUND " + counter);
					System.out.print("KEY = ");
					printHex(k);
					// Left block becomes right block of previous round
					String LeftBlock = RightIPBinary;
					System.out.print("LEFT BLOCK  = ");
					printHex(LeftBlock);
					String RightBlock = OperationF(RightIPBinary, k);// Right block is previous left block XOR
																		// F(previous left block, round key)
					RightBlock = XOR(LeftIPBinary, RightBlock);
					System.out.print("RIGHT BLOCK = ");
					printHex(RightBlock);
					counter++;
					if (counter > 16) {
						// Reversely combine the two blocks to form a 64-bit block
						String result = RightBlock + LeftBlock;
						finalResult = DoPermutation(result, FP);
						// Final Permutation FP: The Inverse of the Initial permutation IP
						encipher += finalResult;
					}
					RightIPBinary = RightBlock;
					LeftIPBinary = LeftBlock;
				}
				end += 64;
				start += 64;
				System.out.print("CIPHER OF 64-bit " + wordCount + "Block = ");
				printHex(finalResult);
			}
			// Display the final cipher text
			System.out.print("\nCIPHER = ");
			printHex(encipher);

		}
		return encipher;
	}

	public String Decryption(String encipher) {
		String BinaryText = encipher;
		int encipherLength = encipher.length();
		int leftpadString = encipherLength % 64;
		int loop = leftpadString != 0 ? encipherLength / 64 + 1 : encipherLength / 64;
		int start = 0, end = 64;
		String finalResult = "", decipher = "", binaryDecipher = "";
		for (int id = loop, wordCount = 1; id > 0; id--, wordCount++) {
			int leftpad = 64 - leftpadString;
			int leftSpace = leftpad % 64 != 0 ? leftpad / 8 : 0;
			end = encipherLength - start < 64 ? encipherLength : end;
			String cipherTextBinary = BinaryText.substring(start, end);
			String IPBinary = DoPermutation(cipherTextBinary, IP);
			String LeftIPBinary = IPBinary.substring(0, 32);
			String RightIPBinary = IPBinary.substring(32, 64);
			/* Proceed through 16 Rounds/iterations each using a unique function f */
			// Go through the 16 keys in the REVERSE ORDER
			int counter = 1;
			String k;
			for (int p = 15; p >= 0; p--) {
				System.out.println();
				System.out.println("64-bit " + wordCount + " DECRYPTION ROUND " + counter);
				k = Keys[p];
				System.out.print("KEY = ");
				printHex(k);
				// Left block becomes right block of previous round
				String LeftBlock = RightIPBinary;
				System.out.print("LEFT BLOCK  = ");
				printHex(LeftBlock);
				// Right block is previous left block XOR F(previous left block, round key)
				String RightBlock = OperationF(RightIPBinary, k);
				RightBlock = XOR(LeftIPBinary, RightBlock);
				System.out.print("RIGHT BLOCK = ");
				printHex(RightBlock);
				counter++;
				if (counter > 16) {
					// Reversely combine the two blocks to form a 64-bit block
					String result = RightBlock + LeftBlock;
					finalResult = DoPermutation(result, FP);
					// Final Permutation FP: The Inverse of the Initial permutation IP
					binaryDecipher += finalResult;
					decipher += (id == 1 && leftSpace != 0) ? intTostr(finalResult, 8).substring(leftSpace)
							: intTostr(finalResult, 8);
				}
				LeftIPBinary = LeftBlock;
				RightIPBinary = RightBlock;
			}
			end += 64;
			start += 64;
		}

		// Display the original plain text
		System.out.print("DECRYPTED CIPHER IN HEX = ");
		printHex(binaryDecipher);
		System.out.println("DECRYPTED CIPHER IN PLAIN TEXT = " + decipher);
		return decipher;

	}

	public void printHex(String binary) {
		int len = binary.length();
		String hex = "";
		int times = len / 4;
		for (int i = 0; i < times; i++) {
			int decimal = Integer.parseInt(binary.substring(i * 4, i * 4 + 4), 2);
			hex += Integer.toString(decimal, 16);
		}
		System.out.println(hex);
	}

	String CircularLeftShift(String s, int k) {

		String result = s.substring(k);
		for (int i = 0; i < k; i++) {
			result += s.charAt(i);
		}
		return result;
	}

	public String intTostr(String stream, int size) {

		String result = "";
		for (int i = 0; i < stream.length(); i += size) {
			result += (stream.substring(i, Math.min(stream.length(), i + size)) + " ");
		}
		String[] ss = result.split(" ");
		StringBuilder sb = new StringBuilder();
		for (int i = 0; i < ss.length; i++) {
			sb.append((char) Integer.parseInt(ss[i], 2));
		}
		return sb.toString();
	}

	public String strTobin(String str) {

		byte[] bytes = str.getBytes();
		StringBuilder binary = new StringBuilder();
		String temp = "";
		for (byte b : bytes) {
			int val = b;
			temp = Integer.toBinaryString(val);// gives result as 110110
			temp = String.format("%8s", temp);// to make it as 8 bit (space)(space)110110
			binary.append(temp.replaceAll(" ", "0"));// to replace space with 0
			binary.append(' '); // inserting space between byte for printing purpose... will be removed after
								// printing
		}
		return binary.toString();
	}

	public static void main(String args[]) {
		DES des = new DES();
		String plainText;
		System.out.println("Enter the plainText:");
		Scanner scanner = new Scanner(System.in);
		plainText = scanner.nextLine();
		System.out.println("Enter 64 bit key ");
		String originalKey = scanner.nextLine();
		String encipher = des.Encryption(plainText, originalKey);
		plainText = des.Decryption(encipher);
		scanner.close();
	}
}

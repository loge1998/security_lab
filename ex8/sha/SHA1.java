import java.util.*;
import java.math.*;

public class SHA1 {
	public static void main (String args[]) {
		System.out.println("Enter plain text : ");
		Scanner terminal = new Scanner(System.in);
		String h0, h1, h2, h3, h4;
		String A, B, C, D, E, F, K;
		h0 = "01100111010001010010001100000001";
		h1 = "11101111110011011010101110001001";
		h2 = "10011000101110101101110011111110";
		h3 = "00010000001100100101010001110110";
		h4 = "11000011110100101110000111110000";
		A = "";
		B = "";
		C = "";
		D = "";
		E = "";
		F = "";
		K = "";

		String input = terminal.nextLine();

		byte bytes[] = input.getBytes();

		String binary[] = new String[input.length()];

		int lengthInBinary_i = 0;
		String input_binary = "";

		for (int i = 0; i < bytes.length; i++) {
			lengthInBinary_i += 8;
			binary[i] = toNBits(Integer.toBinaryString((int) bytes[i]), 8);
			input_binary += binary[i];
		}

		input_binary += "1";

		int added = 0;
		while (input_binary.length() != (448 % 512)) {
			input_binary += "0";
			added++;
		}

		String lengthInBinary_s = toNBits(Integer.toBinaryString(lengthInBinary_i), 64);

		input_binary += lengthInBinary_s;

		int tmp = input_binary.length() / 512;
		String chunks[] = new String[tmp];

		for (int i = 0; i < tmp; i++) {
			String sub = input_binary.substring(i * 512, (i + 1) * 512);
			chunks[i] = sub;
		}

		// for(int i=0;i<64;i++)
		// {
		// 	String temp = chunks[0].substring(i*8, i*8+8);
		// 	int n = Integer.parseInt(temp,2);
		// 	System.out.println(n);
		// }

		String words[][] = new String[tmp][80];

		for (int i = 0; i < chunks.length; i++) {
			String chunk = chunks[i];
			words[i] = new String[80];
			for (int j = 0; j < 16; j++) {
				String sub = chunk.substring(j * 32, (j + 1) * 32);
				words[i][j] = sub;
			}

			for (int j = 16; j < 80; j++) {
				String a = words[i][j - 3];
				String b = words[i][j - 8];
				String c = words[i][j - 14];
				String d = words[i][j - 16];

				words[i][j] = rotateLeft(xor(xor(xor(a, b), c), d), 1);
			}



			A = h0;
			B = h1;
			C = h2;
			D = h3;
			E = h4;

			for (int j = 0; j < 80; j++) {
				String word = words[i][j];
				if (j >= 0 && j <= 19) {
					F = function1(A, B, C, D, E);
					// System.out.println(F);
					K = "01011010100000100111100110011001";
				} else if (j >= 20 && j <= 39) {
					F = function2(A, B, C, D, E);
					K = "01101110110110011110101110100001";
				} else if (j >= 40 && j <= 59) {
					F = function3(A, B, C, D, E);
					K = "10001111000110111011110011011100";
				} else if (j >= 60 && j <= 79) {
					F = function4(A, B, C, D, E);
					K = "11001010011000101100000111010110";
				}
				// if (j == 0) break;

				String x = addAll(A, F, E, K, word);
				x = x.substring(x.length() - 32, x.length());

				E = D;
				D = C;
				C = rotateLeft(B, 30);
				B = A;
				A = x;
			}

			h0 = binaryAdd(h0, A);
			if (h0.length() > 32) h0 = h0.substring(h0.length() - 32, h0.length());
			h1 = binaryAdd(h1, B);
			if (h1.length() > 32) h1 = h1.substring(h1.length() - 32, h1.length());
			h2 = binaryAdd(h2, C);
			if (h2.length() > 32) h2 = h2.substring(h2.length() - 32, h2.length());
			h3 = binaryAdd(h3, D);
			if (h3.length() > 32) h3 = h3.substring(h3.length() - 32, h3.length());
			h4 = binaryAdd(h4, E);
			if (h4.length() > 32) h4 = h4.substring(h4.length() - 32, h4.length());

		}

		String h0Hex, h1Hex, h2Hex, h3Hex, h4Hex;
		h0Hex = Long.toHexString((new BigInteger(h0, 2)).longValue());
		h1Hex = Long.toHexString((new BigInteger(h1, 2)).longValue());
		h2Hex = Long.toHexString((new BigInteger(h2, 2)).longValue());
		h3Hex = Long.toHexString((new BigInteger(h3, 2)).longValue());
		h4Hex = Long.toHexString((new BigInteger(h4, 2)).longValue());

		String hash = h0Hex + h1Hex + h2Hex + h3Hex + h4Hex;
		System.out.println(hash);

	}

	private static String toNBits (String str, int n) {
		if (str.length() >= n) return str;

		while (str.length() != n) {
			str = "0" + str;
		}
		return str;
	}

	private static String xor (String a, String b) {
		int l = (a.length() >= b.length()) ? a.length() : b.length();
		long x = Long.parseLong(a, 2);
		long y = Long.parseLong(b, 2);
		long result = x ^ y;
		return toNBits(Long.toBinaryString(result), l);
	}

	private static String rotateLeft (String a, int n) {
		if (Long.parseLong(a, 2) == 0) return a;

		for (int i = 0; i < n; i++) {
			char first = a.charAt(0);
			a = a.substring(1) + first;
		}
		return a;
	}

	private static String unaryNot (String str) {
		int l = str.length();
		String r = "";
		for (int i = 0; i < l; i++) {
			if (str.charAt(i) == '0') {
				r += "1";
			} else {
				r += "0";
			}
		}
		return r;
	}

	private static String binaryAnd (String a, String b) {
		int l = (a.length() >= b.length()) ? a.length() : b.length();
		String r = "";
		for (int i = 0; i < l; i++) {
			char _a = a.charAt(i);
			char _b = b.charAt(i);

			if (_a == _b) {
				if (_a == '1') r += "1";
				else r += "0";
			} else r += "0";
		}
		return r;
	}

	private static String binaryOr (String a, String b) {
		int l = (a.length() >= b.length()) ? a.length() : b.length();
		String r = "";
		for (int i = 0; i < l; i++) {
			char _a = a.charAt(i);
			char _b = b.charAt(i);

			if (_a == '1' || _b == '1') r += "1";
			else r += "0";
		}
		return r;
	}

	private static String binaryXor (String a, String b) {
		int l = (a.length() >= b.length()) ? a.length() : b.length();
		String r = "";
		for (int i = 0; i < l; i++) {
			char _a = a.charAt(i);
			char _b = b.charAt(i);
			if (_a == _b) {
				r += "0";
			} else {
				r += "1";
			}
		}
		return r;
	}

	private static String binaryAdd (String a, String b) {
		int l = (a.length() >= b.length()) ? a.length() : b.length();
		BigInteger x = new BigInteger(a, 2);
		BigInteger y = new BigInteger(b, 2);
		x = x.add(y);
		String r = toNBits(Long.toBinaryString(x.longValue()), l);
		return r;
	}

	private static String function1 (String A, String B, String C, String D, String E) {
		int l = B.length();
		String result = binaryOr(binaryAnd(B, C), binaryAnd(unaryNot(B), D));
		return toNBits(result, l);
	}

	private static String function2 (String A, String B, String C, String D, String E) {
		int l = B.length();
		String result = binaryXor(binaryXor(B, C), D);
		return toNBits(result, l);
	}

	private static String function3 (String A, String B, String C, String D, String E) {
		int l = B.length();
		String result = binaryOr(binaryOr(binaryAnd(B, C), binaryAnd(B, D)), binaryAnd(C, D));
		return toNBits(result, l);
	}

	private static String function4 (String A, String B, String C, String D, String E) {
		return function2(A, B, C, D, E);
	}

	private static String addAll (String A, String F, String E, String K, String word) {
		String left = rotateLeft(A, 5);
		String right = F;
		String ans = binaryAdd(left, right);
		if (ans.length() == left.length()) ans = "1" + ans;

		left = ans;
		right = E;
		ans = binaryAdd(left, right);
		if (ans.length() == left.length()) ans = "1" + ans;

		left = ans;
		right = K;
		ans = binaryAdd(left, right);
		if (ans.length() == left.length()) ans = "1" + ans;

		left = ans;
		right = word;
		ans = binaryAdd(left, right);
		if (ans.length() == left.length()) ans = "1" + ans;

		return ans;
	}
}

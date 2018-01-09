package name.valery1707.smsc.utils;

public final class Hex {
	private Hex() {
	}

	private static final char[] DIGITS_LOWER = {'0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'a', 'b', 'c', 'd', 'e', 'f'};
	private static final char[] DIGITS_UPPER = {'0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'A', 'B', 'C', 'D', 'E', 'F'};

	private static char[] encodeHex(byte[] data, char[] toDigits) {
		int l = data.length;
		char[] out = new char[l << 1];
		// two characters form the hex value.
		for (int i = 0, j = 0; i < l; i++) {
			out[j++] = toDigits[(0xF0 & data[i]) >>> 4];
			out[j++] = toDigits[0x0F & data[i]];
		}
		return out;
	}

	public static char[] encodeHex(byte[] source, boolean toLowerCase) {
		return encodeHex(source, toLowerCase ? DIGITS_LOWER : DIGITS_UPPER);
	}

	public static char[] encodeHex(byte[] source) {
		return encodeHex(source, true);
	}

	public static String encodeHexString(byte[] source) {
		return new String(encodeHex(source));
	}
}

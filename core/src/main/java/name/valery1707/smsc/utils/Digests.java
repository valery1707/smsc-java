package name.valery1707.smsc.utils;

import java.nio.ByteBuffer;
import java.nio.CharBuffer;
import java.nio.charset.Charset;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;

public class Digests {
	private static MessageDigest getDigest(String algorithm) {
		try {
			return MessageDigest.getInstance(algorithm);
		} catch (NoSuchAlgorithmException e) {
			throw new IllegalStateException("Invalid digest algorithm: " + algorithm, e);
		}
	}

	private static MessageDigest getMd5Digest() {
		return getDigest("MD5");
	}

	public static byte[] md5(byte[] source) {
		return getMd5Digest().digest(source);
	}

	public static byte[] toBytes(char[] chars) {
		CharBuffer charBuffer = CharBuffer.wrap(chars);
		ByteBuffer byteBuffer = Charset.forName("UTF-8").encode(charBuffer);
		byte[] bytes = Arrays.copyOfRange(
				byteBuffer.array(),
				byteBuffer.position(),
				byteBuffer.limit()
		);
		Arrays.fill(charBuffer.array(), '\u0000'); // clear sensitive data
		Arrays.fill(byteBuffer.array(), (byte) 0); // clear sensitive data
		return bytes;
	}

	public static String md5Hex(char[] source) {
		return Hex.encodeHexString(md5(toBytes(source)));
	}
}

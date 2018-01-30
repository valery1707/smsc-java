package name.valery1707.smsc.utils;

import java.io.UnsupportedEncodingException;

public class Checker {
	Checker() {
		throw new IllegalStateException("Instance must not be created");
	}

	public static int checkRange(int value, int minInclusive, int maxInclusive) {
		if (value < minInclusive || value > maxInclusive) {
			throw new IllegalArgumentException(String.format(
					"Value must be in range [%d, %d] but in was: %d",
					minInclusive, maxInclusive, value
			));
		}
		return value;
	}

	public static double checkRange(double value, double minInclusive, double maxInclusive) {
		if (value < minInclusive || value > maxInclusive) {
			throw new IllegalArgumentException(String.format(
					"Value must be in range [%.2f, %.2f] but in was: %.2f",
					minInclusive, maxInclusive, value
			));
		}
		return value;
	}

	public static <T> T safeEncoding(UnsupportedEncodingExceptionCallable<T> callable) {
		try {
			return callable.call();
		} catch (UnsupportedEncodingException e) {
			throw new IllegalStateException("Encoding is not supported", e);
		}
	}

	@FunctionalInterface
	public interface UnsupportedEncodingExceptionCallable<T> {
		T call() throws UnsupportedEncodingException;
	}
}

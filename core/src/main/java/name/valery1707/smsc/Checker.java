package name.valery1707.smsc;

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
}

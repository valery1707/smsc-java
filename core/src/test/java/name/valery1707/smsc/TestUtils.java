package name.valery1707.smsc;

import java.util.Collection;
import java.util.Iterator;
import java.util.Random;

public class TestUtils {
	private static final Random RANDOM = new Random();

	/**
	 * @param minInc Minimal value, inclusive
	 * @param maxInc Maximal value, inclusive
	 * @return Pseudo random value in [{@code minInc}, {@code maxInc}]
	 */
	public static int random(int minInc, int maxInc) {
		try {
			return minInc + RANDOM.nextInt(maxInc - minInc + 1);
		} catch (IllegalArgumentException e) {
			throw new IllegalArgumentException(String.format("random(%d, %d)", minInc, maxInc), e);
		}
	}

	public static <T> T random(Collection<T> source) {
		int index = random(0, source.size() - 1);
		Iterator<T> iterator = source.iterator();
		while (index > 0) {
			index--;
			iterator.next();
		}
		return iterator.next();
	}
}

package name.valery1707.smsc;

import org.junit.Test;

public class CheckerTest {
	@Test
	public void testEncodingKnown() throws Exception {
		Checker.safeEncoding(() -> "test".getBytes("UTF-8"));
	}

	@Test(expected = IllegalStateException.class)
	public void testEncodingUnknown() throws Exception {
		Checker.safeEncoding(() -> "test".getBytes("SomeUnsupportedEncoding"));
	}
}

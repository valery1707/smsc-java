package name.valery1707.smsc;

import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class SmsCenterTest {
	public static SmsCenter centerDemo() {
		return new SmsCenterImpl(
				new HttpClientOkHttp(),
				new JsonMapperJackson(),
				"demo", "demo".toCharArray()
		);
	}

	public static SmsCenter centerInvalid() {
		return new SmsCenterImpl(
				new HttpClientOkHttp(),
				new JsonMapperJackson(),
				"demo", "demo1".toCharArray()
		);
	}

	@Test
	public void testBalance() throws Exception {
		Balance balance = centerDemo().balance();
		assertThat(balance).isNotNull();
		assertThat(balance.isError()).isFalse();
		assertThat(balance.getBalance()).isPositive();
		assertThat(balance.getCredit()).isNull();
		assertThat(balance.getCurrency()).isEqualTo("RUR");
	}
}

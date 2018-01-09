package name.valery1707.smsc;

import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class SmsCenterTest {
	private SmsCenter centerDemo() {
		return new SmsCenterImpl(
				new HttpClientOkHttp(),
				new JsonMapperJackson(),
				"demo", "demo".toCharArray()
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

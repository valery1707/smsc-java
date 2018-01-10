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

	public static SmsCenter centerTest() {
		return new SmsCenterImpl(
				SmsCenterImpl.DEFAULT_URL,
				new HttpClientOkHttp(),
				new JsonMapperJackson(),
				"test-java-api", "e0bb52c59ce14c76c4107f5e3ce79094"
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

package name.valery1707.smsc.mock;

import name.valery1707.smsc.HttpClientOkHttp;
import name.valery1707.smsc.JsonMapperJackson;
import name.valery1707.smsc.SmsCenterImpl;
import name.valery1707.smsc.error.InvalidCredentials;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class SmsCenterMockTest {
	private SmsCenterMock server;
	private SmsCenterImpl client;

	@Before
	public void setUp() throws Exception {
		server = new SmsCenterMock();
		server.start();
		client = new SmsCenterImpl(
				server.getUrl().toExternalForm(),
				new HttpClientOkHttp(),
				new JsonMapperJackson(),
				"demo", "demo".toCharArray()
		);
	}

	@After
	public void tearDown() throws Exception {
		server.stop();
	}

	@Test
	public void testAuthorization_empty() throws Exception {
		assertThat(client.balance().getBalance()).isPositive();
	}

	@Test
	public void testAuthorization_known() throws Exception {
		server.withAccount("demo", "demo");
		assertThat(client.balance().getBalance()).isPositive();
	}

	@Test(expected = InvalidCredentials.class)
	public void testAuthorization_unknown() throws Exception {
		server.withAccount("demo", "demo1");
		client.balance();
	}
}

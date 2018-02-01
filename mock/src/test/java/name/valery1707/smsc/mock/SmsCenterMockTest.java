package name.valery1707.smsc.mock;

import name.valery1707.smsc.HttpClientOkHttp;
import name.valery1707.smsc.JsonMapperJackson;
import name.valery1707.smsc.RequestExecutor;
import name.valery1707.smsc.SmsCenterImpl;
import name.valery1707.smsc.contact.Contact;
import name.valery1707.smsc.error.InvalidCredentials;
import name.valery1707.smsc.error.InvalidParameters;
import name.valery1707.smsc.message.Message;
import name.valery1707.smsc.message.MessageCost;
import name.valery1707.smsc.shared.ServerBaseResponse;
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
				server.url().toExternalForm(),
				new HttpClientOkHttp(),
				new JsonMapperJackson(),
				"demo", "demo".toCharArray()
		);
	}

	@After
	public void tearDown() throws Exception {
		server.stop();
	}

	@Test(expected = InvalidParameters.class)
	public void testGlobalRequiredParams() throws Exception {
		new RequestExecutor(
				server.url().toExternalForm() + "/balance.php",
				new HttpClientOkHttp(), new JsonMapperJackson()
		)
				.single(ServerBaseResponse.class);
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

	@Test(expected = InvalidParameters.class)
	public void testMessageParams_contact_invalid() throws Exception {
		Message message = new Message()
				.withContact(Contact.phone("790512"))
				.withText("Test");
		client.messages().cost(message);
	}

	@Test
	public void testMessageParams_contact_formatted() throws Exception {
		Message message = new Message()
				.withContact(Contact.phone("+7 905 123-45-67"))
				.withText("Test");
		MessageCost cost = client.messages().cost(message);
		assertThat(cost).isNotNull();
	}

	@Test
	public void testMessageCost1() throws Exception {
		Message message = new Message()
				.withContact(Contact.phone("79051234567"))
				.withText("Test");
		MessageCost cost = client.messages().cost(message);
		assertThat(cost).isNotNull();
		assertThat(cost.getCnt()).isEqualTo(message.getContacts().size());
		assertThat(cost.getCost()).isPositive();
		assertThat(cost.getPhones()).hasSameSizeAs(message.getContacts());
	}
}

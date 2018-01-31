package name.valery1707.smsc.message;

import name.valery1707.smsc.contact.Contact;
import name.valery1707.smsc.contact.Phone;
import name.valery1707.smsc.shared.ServerCharset;
import name.valery1707.smsc.time.DeliveryTime;
import org.junit.Test;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Objects;

import static name.valery1707.smsc.SmsCenterTest.centerDemo;
import static name.valery1707.smsc.SmsCenterTest.centerTest;
import static org.assertj.core.api.Assertions.assertThat;

public class MessageManagerTest {
	private static final Phone PHONE = Contact.phone("79051234567");
	private static final String MESSAGE = "Test Java Sender";

	private static void checkCost(MessageCost cost, boolean testMode, Contact... contacts) {
		assertThat(cost).isNotNull();
		assertThat(cost.getCnt()).isEqualTo(contacts.length);
		if (testMode) {
			assertThat(cost.getCost()).isZero();
		} else {
			assertThat(cost.getCost()).isPositive();
		}
		assertThat(cost.getPhones()).hasSize(contacts.length).hasOnlyElementsOfType(MessagePhone.class);
		for (int i = 0; i < contacts.length; i++) {
			Contact contact = contacts[i];
			if (contact instanceof Phone) {
				Phone phone = (Phone) contact;
				assertThat(cost.getPhones().get(i).getPhone()).isEqualTo(phone.getPhone());
			} else {
				assertThat(contact).isInstanceOf(Phone.class);
			}
			assertThat(cost.getPhones().get(i).getMccmnc()).isNotBlank();
			if (testMode) {
				assertThat(cost.getPhones().get(i).getCost()).isNull();
			} else {
				assertThat(cost.getPhones().get(i).getCost()).isNotNull();
			}
		}
		BigDecimal sum = cost.getPhones()
				.stream()
				.map(MessagePhone::getCost)
				.filter(Objects::nonNull)
				.reduce(BigDecimal.ZERO, BigDecimal::add);
		if (testMode) {
			assertThat(sum).isZero();
		} else {
			assertThat(sum).isPositive();
		}
		assertThat(sum).isEqualByComparingTo(cost.getCost());
	}

	@SuppressWarnings("SameParameterValue")
	private static void checkSend(MessageSend send, boolean testMode, Contact... contacts) {
		assertThat(send).isNotNull();
		checkCost(send, testMode, contacts);
		assertThat(send.getId()).isNotBlank();
		assertThat(send.getBalance()).isPositive();
	}

	/**
	 * Обычное сообщение
	 */
	@Test
	public void testSampleSms() throws Exception {
		MessageManager messages = centerDemo().messages();
		Message message = new Message()
				.withContact(PHONE)
				.withText(MESSAGE);
		MessageCost cost = messages.cost(message);
		checkCost(cost, false, PHONE);
	}

	/**
	 * Flash сообщение в кодировке "utf-8" от отправителя "ivan", переведенное в транслит, которое должно быть доставлено абоненту 01.01.2012 г. в 00:00
	 */
	@Test
	public void testSampleFlash() throws Exception {
		MessageManager messages = centerDemo().messages();
		Message message = new Message()
				.withType(MessageType.Known.FLASH)
				.withContact(PHONE)
				.withText(MESSAGE)
				.withTransliterate(Transliterate.Known.TRANSLIT)
				.withDelivery(DeliveryTime.instant(LocalDateTime.of(2012, 1, 1, 0, 0)))
				.withSender("ivan")
				.withCharset(ServerCharset.Known.UTF8);
		MessageCost cost = messages.cost(message);
		checkCost(cost, false, PHONE);
	}

	/**
	 * Бинарное EMS сообщение с текстом "Hello, World!", в котором слово "World" выделено курсивом и подчеркнуто
	 */
	@Test
	public void testSampleBinary() throws Exception {
		MessageManager messages = centerDemo().messages();
		Message message = new Message()
				.withType(MessageType.Known.BINARY_HEX)
				.withContact(PHONE)
				.withText("050A0307056048656C6C6F2C20576F726C6421");
		MessageCost cost = messages.cost(message);
		checkCost(cost, false, PHONE);
	}

	/**
	 * Бинарное WAP-push сообщение, передающее ссылку на сайт "http://wap.ru/"
	 */
	@Test
	public void testSampleWapPush1() throws Exception {
		MessageManager messages = centerDemo().messages();
		Message message = new Message()
				.withType(MessageType.Known.BINARY_HEX)
				.withContact(PHONE)
				.withText("0605040B8423F0DC0601AE02056A0045C60C037761702E72752F0001037761702E7275000101");
		MessageCost cost = messages.cost(message);
		checkCost(cost, false, PHONE);
	}

	/**
	 * WAP-push сообщение в текстовом виде, передающее ссылку на сайт "http://wap.ru" с заголовком "WAP.RU"
	 */
	@Test
	public void testSampleWapPush2() throws Exception {
		MessageManager messages = centerDemo().messages();
		Message message = new Message()
				.withType(MessageType.Known.WAP_PUSH)
				.withContact(PHONE)
				.withText("http://wap.ru\nWAP.RU");
		MessageCost cost = messages.cost(message);
		checkCost(cost, false, PHONE);
	}

	@Test
	public void testSms() throws Exception {
		MessageManager messages = centerTest().messages();
		Message message = new Message()
				.withContact(PHONE)
				.withText(MESSAGE);
		MessageCost cost = messages.cost(message);
		checkCost(cost, true, PHONE);
		MessageSend send = messages.send(message);
		checkSend(send, true, PHONE);
	}
}

package name.valery1707.smsc;

import name.valery1707.smsc.error.ServerError;
import name.valery1707.smsc.message.Message;
import name.valery1707.smsc.phone.Phone;
import name.valery1707.smsc.phone.PhoneGroup;
import name.valery1707.smsc.phone.PhoneSingle;
import name.valery1707.smsc.template.TemplateManager;
import name.valery1707.smsc.user.User;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.List;

public interface SmsCenter {
	MessageManager sender();

	TemplateManager templates();

	BulkManager bulk();

	Balance balance() throws IOException, ServerError;

	ContactManager contacts();

	UserManager users();

	/**
	 * <a href="https://smsc.ru/api/http/send/#menu">API</a>
	 */
	interface MessageManager {
		void send(Message message);

		void cost(Message message);

		void status();

		void delete(Message message);
	}

	interface BulkManager {
	}

	interface ContactManager {
		List<PhoneSingle> listSingle();

		List<PhoneGroup> listGroup();

		void create(Phone phone);

		void update(PhoneSingle phone, String number);

		void update(PhoneGroup phone, String name);

		void groupAdd(PhoneSingle phone, PhoneGroup group);

		void groupMove(PhoneSingle phone, PhoneGroup group);

		void groupDelete(PhoneSingle phone, PhoneGroup group);

		void delete(Phone phone);

		void blackAdd(Phone phone);

		void blackDel(Phone phone);
	}

	/**
	 * <a href="https://smsc.ru/api/http/users/subclients/#menu">API</a>
	 */
	interface UserManager {
		List<User> list();

		void create(User user);

		void update(User user);

		void pay(User user, BigDecimal amount);

		void stat(User user);

		void stat();

		void statResellers(boolean full);
	}
}

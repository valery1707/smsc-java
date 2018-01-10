package name.valery1707.smsc;

import name.valery1707.smsc.contact.Group;
import name.valery1707.smsc.contact.Phone;
import name.valery1707.smsc.error.ServerError;
import name.valery1707.smsc.message.Message;
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

	PhoneManager phones();

	GroupManager groups();

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

	interface PhoneManager {
		List<Phone> list() throws IOException, ServerError;

		List<Phone> listByGroup(Group group) throws IOException, ServerError;

		List<Phone> listByPhone(String phone) throws IOException, ServerError;

		List<Phone> listByFio(String fio) throws IOException, ServerError;

		List<Phone> search(String search) throws IOException, ServerError;

		Long create(Phone phone) throws IOException, ServerError;

		boolean update(Phone phone) throws IOException, ServerError;

		boolean update(Phone phone, String number) throws IOException, ServerError;

		boolean groupInclude(Phone phone, Group group) throws IOException, ServerError;

		boolean groupMove(Phone phone, Group group) throws IOException, ServerError;

		boolean groupExclude(Phone phone, Group group) throws IOException, ServerError;

		boolean delete(Phone phone) throws IOException, ServerError;

		void blackInclude(Phone phone) throws IOException, ServerError;

		void blackExclude(Phone phone) throws IOException, ServerError;
	}

	interface GroupManager {
		List<Group> list() throws IOException, ServerError;

		Long create(Group group) throws IOException, ServerError;

		boolean update(Group group) throws IOException, ServerError;

		boolean delete(Group group) throws IOException, ServerError;
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

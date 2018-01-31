package name.valery1707.smsc.contact;

import name.valery1707.smsc.shared.ServerType;

public interface Contact extends ServerType {
	static Phone phone(String phone) {
		return new Phone().withPhone(phone);
	}

	static Group group(Long id) {
		return new Group().withId(id);
	}
}

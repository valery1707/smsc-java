package name.valery1707.smsc;

import name.valery1707.smsc.message.Message;
import name.valery1707.smsc.phone.Phone;
import name.valery1707.smsc.time.DeliveryTime;

public interface SmsCenter {
	void send(Message message, DeliveryTime time, Phone... phones);

	void send(Message message, DeliveryTime time, Iterable<Phone> phones);
}

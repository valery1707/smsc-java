package name.valery1707.smsc.message;

import name.valery1707.smsc.RequestExecutor;
import name.valery1707.smsc.error.ServerError;
import name.valery1707.smsc.shared.ServerType;
import name.valery1707.smsc.time.DeliveryTime;
import name.valery1707.smsc.utils.Validation;

import java.io.IOException;
import java.net.InetAddress;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

/**
 * <a href="https://smsc.ru/api/http/send/#menu">API</a>
 */
@SuppressWarnings("WeakerAccess")
public class MessageManager {
	private final RequestExecutor executor;

	public MessageManager(RequestExecutor executor) {
		this.executor = executor;
	}

	private void validate(Message message) {
		Validation validation = message.getType()
				.validate(message)
				.validate(message.getContacts(), list -> !list.isEmpty(), list -> "Contacts must be set")
				.fieldLengthMax(message.getText(), "text", true, 800)
				.fieldMatches(message.getId(), "id", true, Message.ID_PATTERN)
				.fieldMatches(message.getSender(), "sender", true, Message.SENDER_PATTERN)
				//Formatting
				;
		if (validation.nonValid()) {
			throw new IllegalArgumentException("Exception");//todo
		}
	}

	private RequestExecutor prepare(Message message) {
		validate(message);
		return executor
				.clone()
				.with("phones", message.getContacts().stream().map(ServerType::presentation).collect(Collectors.joining(",")))
				.with("mes", message.getTransliterate().encode(message.getText()))
				.with("id", message.getId())
				.with("sender", message.getSender())
				.with(message.getType().presentation(), message.getType().value())
				.with("translit", message.getTransliterate())
				.with("tinyurl", message.getTinyUrl())
				.with("time", message.getDelivery(), DeliveryTime::valuePresentation)
				.with("tz", message.getDelivery(), DeliveryTime::zonePresentation)
				.with("period", message.getMulticast(), Multicast::presentationPeriod)
				.with("freq", message.getMulticast(), Multicast::presentationFrequency)
				.with("fileurl", message.getFileUrl())
				.with("voice", message.getCallVoice())
				.with("param", message.getCallParam())
				.with("subj", message.getSubject())
				.with("charset", message.getCharset())
				.with("valid", message.getValid(), valid -> String.format(
						"%02d:%02d"
						, Math.max(TimeUnit.SECONDS.toHours(valid.getSeconds()), 24)
						, valid.getSeconds() - TimeUnit.SECONDS.toHours(valid.getSeconds())
				))
				.with("maxsms", message.getSplitLimit())
				.with("imgcode", message.getFilterCode())
				.with("userip", message.getFilterIP(), InetAddress::toString)
				;
	}

	public MessageSend send(Message message) throws ServerError, IOException {
		return this.
				prepare(message)
				.with("cost", "3")
				.with("op", "1")
				.single(MessageSend.class);
	}

	public MessageCost cost(Message message) throws ServerError, IOException {
		return this.
				prepare(message)
				.with("cost", "1")
				.with("op", "1")
				.single(MessageCost.class);
	}

	public void status() {
		throw new UnsupportedOperationException("Not implemented");
	}

	public void delete(Message message) {
		throw new UnsupportedOperationException("Not implemented");
	}
}

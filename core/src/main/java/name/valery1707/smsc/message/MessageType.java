package name.valery1707.smsc.message;

import name.valery1707.smsc.shared.ServerType;
import name.valery1707.smsc.utils.Validation;

import java.util.function.BiFunction;

public interface MessageType extends ServerType {
	String value();

	Validation validate(Message message);

	enum Known implements MessageType {
		SMS("", "", (validation, message) -> validation
				.fieldNonNull(message.getText(), "text")
		),
		FLASH("flash", (validation, message) -> validation
		),
		BINARY_URL_ENCODE("bin", "1", (validation, message) -> validation
				.fieldNonNull(message.getText(), "text")
		),
		BINARY_HEX("bin", "2", (validation, message) -> validation
				.fieldNonNull(message.getText(), "text")
		),
		WAP_PUSH("push", (validation, message) -> validation
				.validate(message.getText(),
						text -> text != null && text.contains("\n"),
						text -> "Push message must contains link and title combined over '\\n' symbol"
				)
		),
		HLR("hlr", (validation, message) -> validation
				.fieldIsNull(message.getText(), "text")
		),
		PING("ping", (validation, message) -> validation
				.fieldIsNull(message.getText(), "text")
		),
		MMS("mms", (validation, message) -> validation
				.validate(message,
						m -> m.getText() != null || m.getSubject() != null,
						m -> ""
				)
		),
		MAIL("mail", (validation, message) -> validation
				.fieldNonNull(message.getSubject(), "subject")
		),
		VIBER("viber", (validation, message) -> validation
		),
		CALL("call", (validation, message) -> validation
		),
		//Formatting
		;

		private final String name;
		private final String value;
		private final BiFunction<Validation, Message, Validation> validator;

		Known(String name, String value, BiFunction<Validation, Message, Validation> validator) {
			this.name = name;
			this.value = value;
			this.validator = validator;
		}

		Known(String name, BiFunction<Validation, Message, Validation> validator) {
			this(name, "1", validator);
		}

		@Override
		public String presentation() {
			return name;
		}

		@Override
		public String value() {
			return value;
		}

		@Override
		public Validation validate(Message message) {
			return validator.apply(new Validation(), message);
		}
	}
}

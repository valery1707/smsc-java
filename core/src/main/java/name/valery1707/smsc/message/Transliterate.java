package name.valery1707.smsc.message;

import name.valery1707.smsc.shared.ServerType;

import javax.annotation.Nullable;
import java.net.URLEncoder;
import java.util.function.Function;

import static name.valery1707.smsc.Checker.safeEncoding;

public interface Transliterate extends ServerType {
	String encode(@Nullable String message);

	enum Known implements Transliterate {
		AS_IS(0, message -> message),
		TRANSLIT(1, message -> safeEncoding(() -> URLEncoder.encode(message, "UTF-8"))),
		MPA_HC(2, message -> message),
		//Formatting
		;

		private final String presentation;
		private final Function<String, String> encoder;

		Known(int presentation, Function<String, String> encoder) {
			this.presentation = String.valueOf(presentation);
			this.encoder = encoder;
		}

		@Override
		public String presentation() {
			return presentation;
		}


		@Override
		public String encode(@Nullable String message) {
			if (message == null) {
				return null;
			} else {
				return encoder.apply(message);
			}
		}
	}
}

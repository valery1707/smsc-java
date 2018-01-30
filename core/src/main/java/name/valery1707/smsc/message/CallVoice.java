package name.valery1707.smsc.message;

import name.valery1707.smsc.shared.ServerType;

public interface CallVoice extends ServerType {

	enum Known implements CallVoice {
		MALE1("m"),
		MALE2("m2"),
		FEMALE1("w"),
		FEMALE2("w2"),
		FEMALE3("w3"),
		FEMALE4("w4"),
		//Formatting
		;

		private final String presentation;

		Known(String presentation) {
			this.presentation = presentation;
		}

		@Override
		public String presentation() {
			return presentation;
		}
	}
}

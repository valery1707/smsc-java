package name.valery1707.smsc.message;

public interface Transliterate {
	String presentation();

	enum Known implements Transliterate {
		AS_IS(0),
		TRANSLIT(1),
		MPA_HC(2),
		//Formatting
		;

		private final String presentation;

		Known(int presentation) {
			this.presentation = String.valueOf(presentation);
		}

		@Override
		public String presentation() {
			return presentation;
		}
	}
}

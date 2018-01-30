package name.valery1707.smsc.shared;

public interface ServerCharset extends ServerType {
	enum Known implements ServerCharset {
		WINDOWS1251("windows-1251"),
		UTF8("utf-8"),
		KIO8R("koi8-r"),
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

package name.valery1707.smsc.message;

public class CallVoice {
	public static final CallVoice MALE1 = new CallVoice("m");
	public static final CallVoice MALE2 = new CallVoice("m2");
	public static final CallVoice FEMALE1 = new CallVoice("w");
	public static final CallVoice FEMALE2 = new CallVoice("w2");
	public static final CallVoice FEMALE3 = new CallVoice("w3");
	public static final CallVoice FEMALE4 = new CallVoice("w4");

	private final String code;

	public CallVoice(String code) {
		this.code = code;
	}

	public String getCode() {
		return code;
	}
}

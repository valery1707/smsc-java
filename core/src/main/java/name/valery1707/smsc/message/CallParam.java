package name.valery1707.smsc.message;

import static name.valery1707.smsc.Checker.checkRange;

public class CallParam {
	private final int waitAnswer;
	private final int repeatInterval;
	private final int repeatCount;

	public CallParam(int waitAnswer, int repeatInterval, int repeatCount) {
		this.waitAnswer = checkRange(waitAnswer, 0, 99);//Real range: [10..35]
		this.repeatInterval = checkRange(repeatInterval, 0, 3600);
		this.repeatCount = checkRange(repeatCount, 0, 9);
	}

	public int getWaitAnswer() {
		return waitAnswer;
	}

	public int getRepeatInterval() {
		return repeatInterval;
	}

	public int getRepeatCount() {
		return repeatCount;
	}

	public String presentation() {
		return String.format("%d,%d,%d", getWaitAnswer(), getRepeatInterval(), getRepeatCount());
	}
}

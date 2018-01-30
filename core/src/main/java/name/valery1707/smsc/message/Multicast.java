package name.valery1707.smsc.message;

import static name.valery1707.smsc.utils.Checker.checkRange;

@SuppressWarnings("WeakerAccess")
public class Multicast {
	private final double period;
	private final int frequency;

	public Multicast(double period, int frequency) {
		this.period = checkRange(period, 0.1, 720);
		this.frequency = checkRange(frequency, 1, 1440);
	}

	public double getPeriod() {
		return period;
	}

	public String presentationPeriod() {
		return String.format("%.2f", getPeriod());
	}

	public int getFrequency() {
		return frequency;
	}

	public String presentationFrequency() {
		return String.format("%d", getFrequency());
	}
}

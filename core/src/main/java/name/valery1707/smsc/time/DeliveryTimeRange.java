package name.valery1707.smsc.time;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.TimeZone;

import static name.valery1707.smsc.Checker.checkRange;

public class DeliveryTimeRange extends DeliveryTimeBase {
	private final int min;
	private final int max;

	public DeliveryTimeRange(int min, int max, @Nullable TimeZone timeZone) {
		super(timeZone);
		this.min = checkRange(min, 0, 23);
		this.max = checkRange(max, min, 23);
	}

	public DeliveryTimeRange(int min, int max) {
		this(min, max, null);
	}

	public int getMin() {
		return min;
	}

	public int getMax() {
		return max;
	}

	@Nonnull
	@Override
	public String valuePresentation() {
		return String.format("%d-%d", getMin(), getMax());
	}
}

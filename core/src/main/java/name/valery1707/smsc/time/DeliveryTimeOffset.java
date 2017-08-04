package name.valery1707.smsc.time;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.TimeZone;

import static name.valery1707.smsc.Checker.checkRange;

public class DeliveryTimeOffset extends DeliveryTimeBase {
	private final int offset;

	public DeliveryTimeOffset(int offset, @Nullable TimeZone timeZone) {
		super(timeZone);
		this.offset = checkRange(offset, 0, Integer.MAX_VALUE);
	}

	public DeliveryTimeOffset(int offset) {
		this(offset, null);
	}

	public int getOffset() {
		return offset;
	}

	@Nonnull
	@Override
	public String valuePresentation() {
		return "+" + getOffset();
	}
}

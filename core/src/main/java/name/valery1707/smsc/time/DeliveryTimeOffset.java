package name.valery1707.smsc.time;

import javax.annotation.Nonnull;

import static name.valery1707.smsc.Checker.checkRange;

public class DeliveryTimeOffset extends DeliveryTimeBase {
	private final int offset;

	public DeliveryTimeOffset(int offset) {
		super(null);
		this.offset = checkRange(offset, 0, Integer.MAX_VALUE);
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

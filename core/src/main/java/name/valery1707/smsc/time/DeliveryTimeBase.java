package name.valery1707.smsc.time;

import javax.annotation.Nullable;
import java.util.Date;
import java.util.TimeZone;

/**
 *
 */
public abstract class DeliveryTimeBase implements DeliveryTime {
	private final TimeZone timeZone;

	protected DeliveryTimeBase(@Nullable TimeZone timeZone) {
		this.timeZone = timeZone;
	}

	public TimeZone getTimeZone() {
		return timeZone;
	}

	@Nullable
	@Override
	public String zonePresentation() {
		return getTimeZone() == null ? null : zoneOffset().toString();
	}

	/**
	 * @return Смещение относительно дефолтной зоны, в часах
	 */
	protected Number zoneOffset() {
		long now = new Date().getTime();
		int offsetCurrent = getTimeZone().getOffset(now);
		int offsetDefault = DEFAULT_ZONE.getOffset(now);
		return (offsetCurrent - offsetDefault) * 1.0 / (MILLIS_IN_HOUR);
	}
}

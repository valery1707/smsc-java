package name.valery1707.smsc.time;

import javax.annotation.Nullable;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
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
		return getTimeZone() == null ? null : String.valueOf(zoneOffset().intValue());
	}

	/**
	 * @return Смещение относительно дефолтной зоны, в часах
	 */
	protected Number zoneOffset() {
		return zoneOffset(LocalDateTime.now());
	}

	/**
	 * @return Смещение относительно дефолтной зоны, в часах
	 */
	protected Number zoneOffset(LocalDateTime atDateTime) {
		ZoneOffset offsetCurrent = getTimeZone().toZoneId().getRules().getOffset(atDateTime);
		ZoneOffset offsetDefault = DEFAULT_ZONE.toZoneId().getRules().getOffset(atDateTime);
		return (offsetCurrent.getTotalSeconds() - offsetDefault.getTotalSeconds()) * 1.0 / (SECOND_IN_HOUR);
	}
}

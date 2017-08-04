package name.valery1707.smsc.time;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimeZone;

public class DeliveryTimeInstant extends DeliveryTimeBase {
	private static final DateFormat FORMAT = new SimpleDateFormat("dd.MM.yy hh:mm");
	private static final ThreadLocal<DateFormat> FORMAT_SYNC = new ThreadLocal<DateFormat>() {
		@Override
		protected DateFormat initialValue() {
			return (DateFormat) FORMAT.clone();
		}
	};
	private final Date instant;

	public DeliveryTimeInstant(@Nonnull Date instant, @Nullable TimeZone timeZone) {
		super(timeZone);
		this.instant = instant;
	}

	public DeliveryTimeInstant(Date instant) {
		this(instant, null);
	}

	public DeliveryTimeInstant(long unixEpochMillis, @Nullable TimeZone timeZone) {
		this(new Date(unixEpochMillis), timeZone);
	}

	public DeliveryTimeInstant(long unixEpochMillis) {
		this(unixEpochMillis, null);
	}

	public Date getInstant() {
		return instant;
	}

	@Nonnull
	@Override
	public String valuePresentation() {
		return FORMAT_SYNC.get().format(getInstant());
	}
}

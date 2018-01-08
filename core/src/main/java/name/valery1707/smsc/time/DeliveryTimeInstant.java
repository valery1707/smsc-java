package name.valery1707.smsc.time;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.time.format.DateTimeFormatter;
import java.util.Locale;
import java.util.TimeZone;

import static java.time.LocalDateTime.ofEpochSecond;

public class DeliveryTimeInstant extends DeliveryTimeBase {
	/**
	 * Format: <pre>{@code dd.MM.yy HH:mm}</pre>
	 */
	private static final DateTimeFormatter FORMAT = DateTimeFormatter.ofPattern("dd.MM.yy HH:mm", Locale.US);

	private final LocalDateTime instant;

	public DeliveryTimeInstant(@Nonnull LocalDateTime instant, @Nullable TimeZone timeZone) {
		super(timeZone);
		this.instant = instant;
	}

	public DeliveryTimeInstant(LocalDateTime instant) {
		this(instant, null);
	}

	public DeliveryTimeInstant(long unixEpochMillis, @Nullable TimeZone timeZone) {
		this(
				ofEpochSecond(
						unixEpochMillis / 1000,
						(int) (unixEpochMillis % 1000),
						ZoneOffset.UTC
				),
				timeZone
		);
	}

	public DeliveryTimeInstant(long unixEpochMillis) {
		this(unixEpochMillis, null);
	}

	public LocalDateTime getInstant() {
		return instant;
	}

	@Override
	protected Number zoneOffset() {
		return super.zoneOffset(getInstant());
	}

	@Nonnull
	@Override
	public String valuePresentation() {
		return FORMAT.format(getInstant());
	}
}

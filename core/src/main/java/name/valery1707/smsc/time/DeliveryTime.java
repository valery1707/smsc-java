package name.valery1707.smsc.time;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.TimeZone;

public interface DeliveryTime {
	TimeZone DEFAULT_ZONE = TimeZone.getTimeZone("Europe/Moscow");
	int MILLIS_IN_SECOND = 1000;
	int SECOND_IN_MINUTE = 60;
	int MILLIS_IN_MINUTE = SECOND_IN_MINUTE * MILLIS_IN_SECOND;
	int MINUTE_IN_HOUR = 60;
	int MILLIS_IN_HOUR = MINUTE_IN_HOUR * MILLIS_IN_MINUTE;

	@Nonnull
	String valuePresentation();

	@Nullable
	String zonePresentation();
}

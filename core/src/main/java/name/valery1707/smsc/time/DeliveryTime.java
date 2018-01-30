package name.valery1707.smsc.time;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.time.LocalDateTime;
import java.time.ZonedDateTime;
import java.util.TimeZone;

import static java.util.TimeZone.getTimeZone;

@SuppressWarnings("SameParameterValue")
public interface DeliveryTime {
	TimeZone DEFAULT_ZONE = getTimeZone("Europe/Moscow");
	int MILLIS_IN_SECOND = 1000;
	int SECOND_IN_MINUTE = 60;
	int MILLIS_IN_MINUTE = SECOND_IN_MINUTE * MILLIS_IN_SECOND;
	int MINUTE_IN_HOUR = 60;
	int SECOND_IN_HOUR = MINUTE_IN_HOUR * SECOND_IN_MINUTE;
	int MILLIS_IN_HOUR = MINUTE_IN_HOUR * MILLIS_IN_MINUTE;

	@Nonnull
	String valuePresentation();

	/**
	 * Часовой пояс, в котором задается параметр time.
	 * Указывается относительно московского времени.
	 * Параметр tz может быть как положительным, так и отрицательным.
	 * Если tz равен 0, то будет использован московский часовой пояс, если же параметр tz не задан, то часовой пояс будет взят из настроек Клиента.
	 *
	 * @return Смещение относительно часового пояса Москвы
	 */
	@Nullable
	String zonePresentation();

	static DeliveryTimeInstant instant(@Nonnull LocalDateTime instant, @Nullable TimeZone timeZone) {
		return new DeliveryTimeInstant(instant, timeZone);
	}

	static DeliveryTimeInstant instant(@Nonnull LocalDateTime instant) {
		return new DeliveryTimeInstant(instant);
	}

	static DeliveryTimeInstant instant(long unixEpochMillis, @Nullable TimeZone timeZone) {
		return new DeliveryTimeInstant(unixEpochMillis, timeZone);
	}

	static DeliveryTimeInstant instant(long unixEpochMillis) {
		return new DeliveryTimeInstant(unixEpochMillis);
	}

	static DeliveryTimeInstant instant(ZonedDateTime instant) {
		return new DeliveryTimeInstant(instant.toLocalDateTime(), getTimeZone(instant.getZone()));
	}

	/**
	 * @param offset Offset in minutes
	 * @return DeliveryTime
	 */
	static DeliveryTimeOffset offset(int offset) {
		return new DeliveryTimeOffset(offset);
	}

	static DeliveryTimeRange range(int min, int max, @Nullable TimeZone timeZone) {
		return new DeliveryTimeRange(min, max, timeZone);
	}

	static DeliveryTimeRange range(int min, int max) {
		return new DeliveryTimeRange(min, max);
	}
}

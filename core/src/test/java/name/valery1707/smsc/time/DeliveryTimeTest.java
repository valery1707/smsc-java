package name.valery1707.smsc.time;

import org.junit.Test;

import java.time.LocalDateTime;
import java.time.ZonedDateTime;
import java.util.TimeZone;

import static java.util.TimeZone.getTimeZone;
import static name.valery1707.smsc.time.DeliveryTime.*;
import static org.assertj.core.api.Assertions.assertThat;

public class DeliveryTimeTest {
	private static final TimeZone TEST_ZONE = getTimeZone("Asia/Almaty");

	@Test
	public void instant_dateTime_timeZone() throws Exception {
		DeliveryTime time = instant(LocalDateTime.of(2018, 1, 8, 17, 52, 33, 1), TEST_ZONE);
		assertThat(time.valuePresentation())
				.isNotNull().isEqualTo("08.01.18 17:52");
		assertThat(time.zonePresentation())
				.isNotNull().isEqualTo("3");
	}

	@Test
	public void instant_dateTime() throws Exception {
		DeliveryTime time = instant(LocalDateTime.of(2018, 1, 8, 17, 52, 33, 1));
		assertThat(time.valuePresentation())
				.isNotNull().isEqualTo("08.01.18 17:52");
		assertThat(time.zonePresentation())
				.isNull();
	}

	@Test
	public void instant_unixEpoch_timeZone() throws Exception {
		DeliveryTime time = instant(1515433953000L, TEST_ZONE);
		assertThat(time.valuePresentation())
				.isNotNull().isEqualTo("08.01.18 17:52");
		assertThat(time.zonePresentation())
				.isNotNull().isEqualTo("3");
	}

	@Test
	public void instant_unixEpoch() throws Exception {
		DeliveryTime time = instant(1515433953000L);
		assertThat(time.valuePresentation())
				.isNotNull().isEqualTo("08.01.18 17:52");
		assertThat(time.zonePresentation())
				.isNull();
	}

	@Test
	public void instant_zonedDateTime() throws Exception {
		DeliveryTime time = instant(ZonedDateTime.of(2018, 1, 8, 17, 52, 33, 1, TEST_ZONE.toZoneId()));
		assertThat(time.valuePresentation())
				.isNotNull().isEqualTo("08.01.18 17:52");
		assertThat(time.zonePresentation())
				.isNotNull().isEqualTo("3");
	}

	@Test
	public void offset_offset() throws Exception {
		DeliveryTime time = offset(53);
		assertThat(time.valuePresentation())
				.isNotNull().isEqualTo("+53");
		assertThat(time.zonePresentation())
				.isNull();
	}

	@Test
	public void range_range_timeZone() throws Exception {
		DeliveryTime time = range(1, 12, TEST_ZONE);
		assertThat(time.valuePresentation())
				.isNotNull().isEqualTo("1-12");
		assertThat(time.zonePresentation())
				.isNotNull().isEqualTo("3");
	}

	@Test
	public void range_range() throws Exception {
		DeliveryTime time = range(1, 12);
		assertThat(time.valuePresentation())
				.isNotNull().isEqualTo("1-12");
		assertThat(time.zonePresentation())
				.isNull();
	}
}

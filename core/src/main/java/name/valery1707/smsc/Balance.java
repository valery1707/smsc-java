package name.valery1707.smsc;

import java.math.BigDecimal;

public class Balance {
	private final BigDecimal value;
	private final String currency;

	public Balance(BigDecimal value, String currency) {
		this.value = value;
		this.currency = currency;
	}

	public BigDecimal getValue() {
		return value;
	}

	public String getCurrency() {
		return currency;
	}
}

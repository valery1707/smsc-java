package name.valery1707.smsc;

import java.math.BigDecimal;

public class Balance extends ServerErrorResponse {
	private BigDecimal balance;
	private BigDecimal credit;
	private String currency;

	public Balance() {
	}

	public BigDecimal getBalance() {
		return balance;
	}

	public void setBalance(BigDecimal balance) {
		this.balance = balance;
	}

	public BigDecimal getCredit() {
		return credit;
	}

	public void setCredit(BigDecimal credit) {
		this.credit = credit;
	}

	public String getCurrency() {
		return currency;
	}

	public void setCurrency(String currency) {
		this.currency = currency;
	}
}

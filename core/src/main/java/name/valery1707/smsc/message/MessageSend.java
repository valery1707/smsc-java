package name.valery1707.smsc.message;

import java.math.BigDecimal;

public class MessageSend extends MessageCost {
	private String id;
	private BigDecimal balance;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public BigDecimal getBalance() {
		return balance;
	}

	public void setBalance(BigDecimal balance) {
		this.balance = balance;
	}
}

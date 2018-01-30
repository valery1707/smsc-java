package name.valery1707.smsc.message;

import name.valery1707.smsc.ServerErrorResponse;

import java.math.BigDecimal;
import java.util.List;

public class MessageCost extends ServerErrorResponse {
	private int cnt;
	private BigDecimal cost;
	private List<MessagePhone> phones;

	public int getCnt() {
		return cnt;
	}

	public void setCnt(int cnt) {
		this.cnt = cnt;
	}

	public BigDecimal getCost() {
		return cost;
	}

	public void setCost(BigDecimal cost) {
		this.cost = cost;
	}

	public List<MessagePhone> getPhones() {
		return phones;
	}

	public void setPhones(List<MessagePhone> phones) {
		this.phones = phones;
	}
}

package name.valery1707.smsc.message;

import name.valery1707.smsc.shared.ServerBaseResponse;

import java.util.Objects;

public class MessageTemplate extends ServerBaseResponse {
	private Long id;
	//todo Enum?
	private String format;
	private String message;
	private String name;
	private String sender = "";
	private int flag;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public MessageTemplate withId(Long id) {
		setId(id);
		return this;
	}

	public String getFormat() {
		return format;
	}

	public void setFormat(String format) {
		this.format = format;
	}

	public MessageTemplate withFormat(String format) {
		setFormat(format);
		return this;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public MessageTemplate withMessage(String message) {
		setMessage(message);
		return this;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public MessageTemplate withName(String name) {
		setName(name);
		return this;
	}

	public String getSender() {
		return sender;
	}

	public void setSender(String sender) {
		this.sender = sender;
	}

	public MessageTemplate withSender(String sender) {
		setSender(sender);
		return this;
	}

	public int getFlag() {
		return flag;
	}

	public void setFlag(int flag) {
		this.flag = flag;
	}

	public MessageTemplate withFlag(int flag) {
		setFlag(flag);
		return this;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) {
			return true;
		}
		if (o == null || getClass() != o.getClass()) {
			return false;
		}
		MessageTemplate that = (MessageTemplate) o;
		return getFlag() == that.getFlag() &&
			   Objects.equals(getId(), that.getId()) &&
			   Objects.equals(getFormat(), that.getFormat()) &&
			   Objects.equals(getMessage(), that.getMessage()) &&
			   Objects.equals(getName(), that.getName()) &&
			   Objects.equals(getSender(), that.getSender());
	}

	@Override
	public int hashCode() {
		return Objects.hash(getId(), getFormat(), getMessage(), getName(), getSender(), getFlag());
	}
}

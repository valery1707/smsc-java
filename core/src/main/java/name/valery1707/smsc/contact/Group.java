package name.valery1707.smsc.contact;

import name.valery1707.smsc.shared.ServerBaseResponse;

import java.util.Objects;

import static java.util.Objects.requireNonNull;

public class Group extends ServerBaseResponse implements Contact {
	private Long id;
	private String name;
	private String number;
	private String cnt;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Group withId(Long id) {
		setId(id);
		return this;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Group withName(String name) {
		setName(name);
		return this;
	}

	public String getNumber() {
		return number;
	}

	public void setNumber(String number) {
		this.number = number;
	}

	public Group withNumber(String number) {
		setNumber(number);
		return this;
	}

	public String getCnt() {
		return cnt;
	}

	public void setCnt(String cnt) {
		this.cnt = cnt;
	}

	public Group withCnt(String cnt) {
		setCnt(cnt);
		return this;
	}

	@Override
	public String presentation() {
		return "G" + requireNonNull(getId(), "Id must be set");
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) {
			return true;
		}
		if (o == null || getClass() != o.getClass()) {
			return false;
		}
		Group group = (Group) o;
		return Objects.equals(getId(), group.getId()) &&
			   Objects.equals(getName(), group.getName()) &&
			   Objects.equals(getNumber(), group.getNumber()) &&
			   Objects.equals(getCnt(), group.getCnt());
	}

	@Override
	public int hashCode() {
		return Objects.hash(getId(), getName(), getNumber(), getCnt());
	}
}

package name.valery1707.smsc.contact;

import name.valery1707.smsc.shared.ServerBaseResponse;

import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.stream.StreamSupport;

import static java.util.Collections.emptyList;
import static java.util.stream.Collectors.joining;
import static java.util.stream.Collectors.toList;

public class Phone extends ServerBaseResponse implements Contact {
	private Long id;
	private String name;
	private String phone;
	private String group = "0";
	private String firstName;
	private String lastName;
	private String middleName;
	private String birthday;//todo More correct format
	private String comments;
	private String tags;
	private String phoneOther;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Phone withId(Long id) {
		setId(id);
		return this;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Phone withName(String name) {
		setName(name);
		return this;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public Phone withPhone(String phone) {
		setPhone(phone);
		return this;
	}

	public String getGroup() {
		return group;
	}

	public void setGroup(String group) {
		this.group = group;
	}

	public Phone withGroup(String group) {
		setGroup(group);
		return this;
	}

	public List<Long> getGroupIds() {
		if (getGroup() == null) {
			return emptyList();
		}
		return Arrays
				.stream(getGroup().split("[|,]"))
				.map(Long::parseLong)
				.collect(toList());
	}

	public void setGroupIds(Iterable<Long> groupIds) {
		String ids = StreamSupport
				.stream(groupIds.spliterator(), false)
				.map(Object::toString)
				.collect(joining(","));
		setGroup(ids.isEmpty() ? null : ids);
	}

	public Phone withGroupIds(Iterable<Long> groupIds) {
		setGroupIds(groupIds);
		return this;
	}

	public Phone withGroupIds(Long... groupIds) {
		return withGroupIds(Arrays.asList(groupIds));
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public Phone withFirstName(String firstName) {
		setFirstName(firstName);
		return this;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public Phone withLastName(String lastName) {
		setLastName(lastName);
		return this;
	}

	public String getMiddleName() {
		return middleName;
	}

	public void setMiddleName(String middleName) {
		this.middleName = middleName;
	}

	public Phone withMiddleName(String middleName) {
		setMiddleName(middleName);
		return this;
	}

	public String getBirthday() {
		return birthday;
	}

	public void setBirthday(String birthday) {
		this.birthday = birthday;
	}

	public Phone withBirthday(String birthday) {
		setBirthday(birthday);
		return this;
	}

	public String getComments() {
		return comments;
	}

	public void setComments(String comments) {
		this.comments = comments;
	}

	public Phone withComments(String comments) {
		setComments(comments);
		return this;
	}

	public String getTags() {
		return tags;
	}

	public void setTags(String tags) {
		this.tags = tags;
	}

	public Phone withTags(String tags) {
		setTags(tags);
		return this;
	}

	public String getPhoneOther() {
		return phoneOther;
	}

	public void setPhoneOther(String phoneOther) {
		this.phoneOther = phoneOther;
	}

	public Phone withPhoneOther(String phoneOther) {
		setPhoneOther(phoneOther);
		return this;
	}

	@Override
	public String presentation() {
		return getPhone();
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) {
			return true;
		}
		if (o == null || getClass() != o.getClass()) {
			return false;
		}
		Phone that = (Phone) o;
		return Objects.equals(getId(), that.getId()) &&
			   Objects.equals(getName(), that.getName()) &&
			   Objects.equals(getPhone(), that.getPhone()) &&
			   Objects.equals(getGroup(), that.getGroup()) &&
			   Objects.equals(getFirstName(), that.getFirstName()) &&
			   Objects.equals(getLastName(), that.getLastName()) &&
			   Objects.equals(getMiddleName(), that.getMiddleName()) &&
			   Objects.equals(getBirthday(), that.getBirthday()) &&
			   Objects.equals(getComments(), that.getComments()) &&
			   Objects.equals(getTags(), that.getTags()) &&
			   Objects.equals(getPhoneOther(), that.getPhoneOther());
	}

	@Override
	public int hashCode() {
		return Objects.hash(getId(), getName(), getPhone(), getGroup(), getFirstName(), getLastName(), getMiddleName(), getBirthday(), getComments(), getTags(), getPhoneOther());
	}
}

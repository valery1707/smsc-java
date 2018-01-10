package name.valery1707.smsc.contact;

import name.valery1707.smsc.IdContainer;
import name.valery1707.smsc.RequestExecutor;
import name.valery1707.smsc.ResultContainer;
import name.valery1707.smsc.SmsCenter;
import name.valery1707.smsc.error.InvalidId;
import name.valery1707.smsc.error.ServerError;

import java.io.IOException;
import java.util.List;

import static java.util.Collections.emptyList;
import static java.util.stream.Collectors.joining;

public class PhoneManagerImpl implements SmsCenter.PhoneManager {
	private final RequestExecutor executor;

	public PhoneManagerImpl(RequestExecutor executor) {
		this.executor = executor;
	}

	@Override
	public List<Phone> list() throws IOException, ServerError {
		try {
			return executor
					.clone()
					.with("get", 1)
					.multi(Phone.class);
		} catch (InvalidId e) {
			return emptyList();
		}
	}

	@Override
	public List<Phone> listByGroup(Group group) throws IOException, ServerError {
		try {
			return executor
					.clone()
					.with("get", 1)
					.with("grp", group.getId())
					.multi(Phone.class);
		} catch (InvalidId e) {
			return emptyList();
		}
	}

	@Override
	public List<Phone> listByPhone(String phone) throws IOException, ServerError {
		try {
			return executor
					.clone()
					.with("get", 1)
					.with("phone", phone)
					.multi(Phone.class);
		} catch (InvalidId e) {
			return emptyList();
		}
	}

	@Override
	public List<Phone> listByFio(String fio) throws IOException, ServerError {
		try {
			return executor
					.clone()
					.with("get", 1)
					.with("fio", fio)
					.multi(Phone.class);
		} catch (InvalidId e) {
			return emptyList();
		}
	}

	@Override
	public List<Phone> search(String search) throws IOException, ServerError {
		try {
			return executor
					.clone()
					.with("get", 1)
					.with("search", search)
					.multi(Phone.class);
		} catch (InvalidId e) {
			return emptyList();
		}
	}

	@Override
	public Long create(Phone phone) throws IOException, ServerError {
		return executor
				.clone()
				.with("add", 1)
				.with("name", phone.getName())
				.with("phone", phone.getPhone())
				.with("grp", phone.getGroupIds().stream().map(Object::toString).collect(joining(",")))
				.with("fnm", phone.getFirstName())
				.with("lnm", phone.getLastName())
				.with("mnm", phone.getMiddleName())
				.with("bd", phone.getBirthday())
				.with("myid", phone.getId())
				.with("cmt", phone.getComments())
				.with("tags", phone.getTags())
				.with("pho", phone.getPhoneOther())
				.single(IdContainer.class)
				.getId();
	}

	@Override
	public boolean update(Phone phone) throws IOException, ServerError {
		return executor
				.clone()
				.with("chg", 1)
				.with("phone", phone.getPhone())
				.with("name", phone.getName())
				.with("grp", phone.getGroupIds().stream().map(Object::toString).collect(joining(",")))
				.with("fnm", phone.getFirstName())
				.with("lnm", phone.getLastName())
				.with("mnm", phone.getMiddleName())
				.with("bd", phone.getBirthday())
				.with("myid", phone.getId())
				.with("cmt", phone.getComments())
				.with("tags", phone.getTags())
				.with("pho", phone.getPhoneOther())
				.single(ResultContainer.class)
				.getResult().equals("OK");
	}

	@Override
	public boolean update(Phone phone, String number) throws IOException, ServerError {
		return executor
				.clone()
				.with("chg", 1)
				.with("phone", phone.getPhone())
				.with("new_phone", number)
				.single(ResultContainer.class)
				.getResult().equals("OK");
	}

	@Override
	public boolean groupInclude(Phone phone, Group group) throws IOException, ServerError {
		return executor
				.clone()
				.with("move_group", 2)
				.with("phone", phone.getPhone())
				.with("grp", group.getId())
				.single(ResultContainer.class)
				.getResult().equals("OK");
	}

	@Override
	public boolean groupMove(Phone phone, Group group) throws IOException, ServerError {
		return executor
				.clone()
				.with("move_group", 1)
				.with("phone", phone.getPhone())
				.with("grp", group.getId())
				.single(ResultContainer.class)
				.getResult().equals("OK");
	}

	@Override
	public boolean groupExclude(Phone phone, Group group) throws IOException, ServerError {
		return executor
				.clone()
				.with("move_group", 3)
				.with("phone", phone.getPhone())
				.with("grp", group.getId())
				.single(ResultContainer.class)
				.getResult().equals("OK");
	}

	@Override
	public boolean delete(Phone phone) throws IOException, ServerError {
		return executor
				.clone()
				.with("del", 1)
				.with("phone", phone.getPhone())
				.single(ResultContainer.class)
				.getResult().equals("OK");
	}

	@Override
	public void blackInclude(Phone phone) throws IOException, ServerError {

	}

	@Override
	public void blackExclude(Phone phone) throws IOException, ServerError {

	}
}

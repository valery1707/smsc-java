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

public class GroupManagerImpl implements SmsCenter.GroupManager {
	private final RequestExecutor executor;

	public GroupManagerImpl(RequestExecutor executor) {
		this.executor = executor;
	}

	@Override
	public List<Group> list() throws IOException, ServerError {
		try {
			return executor
					.clone()
					.with("get_group", 1)
					.multi(Group.class);
		} catch (InvalidId e) {
			return emptyList();
		}
	}

	@Override
	public Long create(Group group) throws IOException, ServerError {
		return executor
				.clone()
				.with("add_group", 1)
				.with("name", group.getName())
				.with("num", group.getNumber())
				.single(IdContainer.class)
				.getId();
	}

	@Override
	public boolean update(Group group) throws IOException, ServerError {
		return executor
				.clone()
				.with("chg_group", 1)
				.with("grp", group.getId())
				.with("name", group.getName())
				.with("num", group.getNumber())
				.single(ResultContainer.class)
				.getResult().equals("OK");
	}

	@Override
	public boolean delete(Group group) throws IOException, ServerError {
		return executor
				.clone()
				.with("del_group", 1)
				.with("grp", group.getId())
				.single(ResultContainer.class)
				.getResult().equals("OK");
	}
}

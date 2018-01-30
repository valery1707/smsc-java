package name.valery1707.smsc.template;

import name.valery1707.smsc.RequestExecutor;
import name.valery1707.smsc.error.ServerError;
import name.valery1707.smsc.message.MessageTemplate;
import name.valery1707.smsc.shared.IdContainer;
import name.valery1707.smsc.shared.ResultContainer;

import java.io.IOException;
import java.util.List;

public class TemplateManager {
	private final RequestExecutor executor;

	public TemplateManager(RequestExecutor executor) {
		this.executor = executor;
	}

	public List<MessageTemplate> list() throws IOException, ServerError {
		return executor
				.clone()
				.with("get", 1)
				.multi(MessageTemplate.class);
	}

	public Long create(MessageTemplate template) throws IOException, ServerError {
		return executor
				.clone()
				.with("add", 1)
				.with("format", template.getFormat())
				.with("msg", template.getMessage())
				.with("name", template.getName())
				.with("sender", template.getSender())
				.single(IdContainer.class)
				.getId();
	}

	public boolean update(MessageTemplate template) throws IOException, ServerError {
		return executor
				.clone()
				.with("chg", 1)
				.with("id", template.getId())
				.with("format", template.getFormat())
				.with("msg", template.getMessage())
				.with("name", template.getName())
				.with("sender", template.getSender())
				.single(ResultContainer.class)
				.getResult().equals("OK");
	}

	public boolean delete(MessageTemplate template) throws IOException, ServerError {
		return executor
				.clone()
				.with("del", 1)
				.with("id", template.getId())
				.single(ResultContainer.class)
				.getResult().equals("OK");
	}
}

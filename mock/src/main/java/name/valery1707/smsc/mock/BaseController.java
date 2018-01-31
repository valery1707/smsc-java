package name.valery1707.smsc.mock;

import spark.Request;
import spark.Response;

import java.util.HashMap;

public abstract class BaseController implements SmsController {
	private Database database;

	@Override
	public void setDatabase(Database database) {
		this.database = database;
	}

	protected Database database() {
		return database;
	}

	@Override
	public Object handle(Request request, Response response) throws Exception {
		HashMap<String, String> params = extractParams(request);
		return handle(params, request, response);
	}

	public static HashMap<String, String> extractParams(Request request) {
		HashMap<String, String> params = new HashMap<>();
		if (request.requestMethod().toLowerCase().equals("post")) {
			String body = request.body().trim();
			if (!body.isEmpty()) {
				for (String nameAndValuePair : body.split("&")) {
					String[] pair = nameAndValuePair.split("=");
					String name = pair[0];
					String value = pair[1];
					params.put(name, value);
				}
			}
		} else {
			params.putAll(request.params());
		}
		return params;
	}

	protected abstract Object handle(HashMap<String, String> params, Request request, Response response) throws Exception;
}

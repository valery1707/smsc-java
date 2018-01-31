package name.valery1707.smsc.mock;

import name.valery1707.smsc.error.*;
import name.valery1707.smsc.shared.ServerBaseResponse;
import spark.Request;
import spark.Response;

import java.util.HashMap;

public abstract class BaseController<R extends ServerBaseResponse> implements SmsController {
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
		try {
			return handle(params, request, response);
		} catch (ServerError e) {
			ServerBaseResponse error = new ServerBaseResponse();
			error.setError(e.getMessage());
			if (e instanceof InvalidParameters) {
				error.setErrorCode(1);
			} else if (e instanceof InvalidCredentials) {
				error.setErrorCode(2);
			} else if (e instanceof InvalidId) {
				error.setErrorCode(3);
			} else if (e instanceof LockedIp) {
				error.setErrorCode(4);
			} else if (e instanceof PersistError) {
				error.setErrorCode(5);
			} else if (e instanceof ToManyRequests) {
				error.setErrorCode(9);
			} else {
				error.setErrorCode(-1);
			}
			return error;
		} catch (Exception e) {
			ServerBaseResponse error = new ServerBaseResponse();
			error.setError(e.getMessage());
			error.setErrorCode(-2);
			return error;
		}
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

	protected abstract R handle(HashMap<String, String> params, Request request, Response response) throws ServerError;
}

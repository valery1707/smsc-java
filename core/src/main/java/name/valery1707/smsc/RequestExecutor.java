package name.valery1707.smsc;

import name.valery1707.smsc.error.*;

import java.io.IOException;
import java.util.LinkedHashMap;
import java.util.Map;

public class RequestExecutor {
	private final String url;
	private final HttpClient client;
	private final JsonMapper mapper;
	private final Map<String, String> params = new LinkedHashMap<>();

	public RequestExecutor(String url, HttpClient client, JsonMapper mapper) {
		this.url = url;
		this.client = client;
		this.mapper = mapper;
	}

	public RequestExecutor with(String name, String value) {
		params.put(name, value);
		return this;
	}

	public RequestExecutor with(String name, Number value) {
		return with(name, value.toString());
	}

	public <T extends ServerErrorResponse> T map(Class<T> targetClass)
			throws IOException, InvalidParameters, InvalidCredentials, LockedIp, UnknownServerError, ToManyRequests {
		String raw = client.execute(url, params);
		T response = mapper.read(raw, targetClass);
		if (response.isError()) {
			switch (response.getErrorCode()) {
				case 1:
					throw new InvalidParameters(response.getError());
				case 2:
					throw new InvalidCredentials(response.getError());
				case 4:
					throw new LockedIp(response.getError());
				case 9:
					throw new ToManyRequests(response.getError());
				default:
					throw new UnknownServerError(response.getError());
			}
		}
		return response;
	}
}

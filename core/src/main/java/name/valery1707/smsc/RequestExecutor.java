package name.valery1707.smsc;

import name.valery1707.smsc.error.*;

import java.io.IOException;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

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
		if (value != null && !value.trim().isEmpty()) {
			params.put(name, value.trim());
		}
		return this;
	}

	public RequestExecutor with(String name, Number value) {
		if (value != null) {
			return with(name, value.toString());
		} else {
			return this;
		}
	}

	public <T extends ServerErrorResponse> T single(Class<T> targetClass)
			throws IOException, InvalidParameters, InvalidCredentials, LockedIp, UnknownServerError, ToManyRequests, InvalidId, PersistError {
		String raw = client.execute(url, params);
		checkError(raw);
		T response = mapper.single(raw, targetClass);
		checkError(response);
		return response;
	}

	private static final Pattern INLINE_ERROR_FORMAT = Pattern.compile("ERROR = (\\d+) \\(([^)]+)\\)");

	/**
	 * In some cases (Update Template with unknown id) server return response in plain format
	 *
	 * @param raw Raw server response
	 */
	private void checkError(String raw)
			throws ToManyRequests, IOException, LockedIp, InvalidParameters, InvalidCredentials, UnknownServerError, InvalidId, PersistError {
		Matcher matcher = INLINE_ERROR_FORMAT.matcher(raw);
		if (matcher.find()) {
			ServerErrorResponse response = new ServerErrorResponse();
			response.setError(matcher.group(2));
			response.setErrorCode(Integer.parseInt(matcher.group(1)));
			checkError(response);
		}
	}

	private void checkError(ServerErrorResponse response)
			throws IOException, InvalidParameters, InvalidCredentials, LockedIp, UnknownServerError, ToManyRequests, InvalidId, PersistError {
		if (response.isError()) {
			switch (response.getErrorCode()) {
				case 1:
					throw new InvalidParameters(response.getError());
				case 2:
					throw new InvalidCredentials(response.getError());
				case 3:
					throw new InvalidId(response.getError());
				case 4:
					throw new LockedIp(response.getError());
				case 5:
					throw new PersistError(response.getError());
				case 9:
					throw new ToManyRequests(response.getError());
				default:
					throw new UnknownServerError(response.getErrorCode() + ": " + response.getError());
			}
		}
	}

	private static final Pattern JSON_ARRAY = Pattern.compile("^\\s*\\[\\s*\\{");

	public <T extends ServerErrorResponse> List<T> multi(Class<T> targetClass)
			throws IOException, InvalidParameters, InvalidCredentials, LockedIp, UnknownServerError, ToManyRequests, InvalidId, PersistError {
		String raw = client.execute(url, params);
		checkError(raw);
		if (JSON_ARRAY.matcher(raw).find()) {
			return mapper.multi(raw, targetClass);
		} else {
			checkError(mapper.single(raw, ServerErrorResponse.class));
			throw new UnknownServerError("Invalid response format: " + raw);
		}
	}

	@Override
	@SuppressWarnings({"CloneDoesntDeclareCloneNotSupportedException", "MethodDoesntCallSuperMethod"})
	public RequestExecutor clone() {
		RequestExecutor copy = new RequestExecutor(url, client, mapper);
		params.forEach(copy::with);
		return copy;
	}
}

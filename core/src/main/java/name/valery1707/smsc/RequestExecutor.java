package name.valery1707.smsc;

import name.valery1707.smsc.error.*;
import name.valery1707.smsc.shared.ServerBaseResponse;
import name.valery1707.smsc.shared.ServerType;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.io.IOException;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@SuppressWarnings("WeakerAccess")
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

	public RequestExecutor with(@Nonnull String name, @Nullable String value) {
		if (value != null && !value.trim().isEmpty()) {
			params.put(name, value.trim());
		}
		return this;
	}

	public <T> RequestExecutor with(@Nonnull String name, @Nullable T value, @Nonnull Function<T, String> presenter) {
		if (value != null) {
			return with(name, presenter.apply(value));
		} else {
			return this;
		}
	}

	public RequestExecutor with(@Nonnull String name, @Nullable Number value) {
		return with(name, value, Object::toString);
	}

	public RequestExecutor with(@Nonnull String name, @Nullable Boolean value) {
		return with(name, value, v -> v ? "1" : "0");
	}

	public RequestExecutor with(@Nonnull String name, @Nullable ServerType value) {
		return with(name, value, ServerType::presentation);
	}

	public <T extends ServerBaseResponse> T single(Class<T> targetClass)
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
			ServerBaseResponse response = new ServerBaseResponse();
			response.setError(matcher.group(2));
			response.setErrorCode(Integer.parseInt(matcher.group(1)));
			checkError(response);
		}
	}

	private void checkError(ServerBaseResponse response)
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

	public <T extends ServerBaseResponse> List<T> multi(Class<T> targetClass)
			throws IOException, InvalidParameters, InvalidCredentials, LockedIp, UnknownServerError, ToManyRequests, InvalidId, PersistError {
		String raw = client.execute(url, params);
		checkError(raw);
		if (JSON_ARRAY.matcher(raw).find()) {
			return mapper.multi(raw, targetClass);
		} else {
			checkError(mapper.single(raw, ServerBaseResponse.class));
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

package name.valery1707.smsc;

import name.valery1707.smsc.error.ServerError;

import java.io.IOException;

import static java.util.Objects.requireNonNull;
import static name.valery1707.smsc.utils.Digests.md5Hex;

public class SmsCenterImpl implements SmsCenter {
	private static final int FORMAT_JSON = 3;

	private final String serverUrl;
	private final HttpClient client;
	private final JsonMapper mapper;
	private final String username;
	private final String password;

	public SmsCenterImpl(String serverUrl, HttpClient client, JsonMapper mapper, String username, char[] password) {
		this.serverUrl = requireNonNull(serverUrl, "serverUrl is null").endsWith("/") ? serverUrl : serverUrl + "/";
		this.client = requireNonNull(client, "client is null");
		this.mapper = requireNonNull(mapper, "mapper is null");
		this.username = requireNonNull(username, "username is null");
		this.password = md5Hex(requireNonNull(password, "password is null"));
	}

	public SmsCenterImpl(HttpClient client, JsonMapper mapper, String username, char[] password) {
		this("https://smsc.ru/sys/", client, mapper, username, password);
	}

	private RequestExecutor call(String url) {
		return new RequestExecutor(serverUrl + url, client, mapper)
				.with("login", username)
				.with("psw", password)
				.with("fmt", FORMAT_JSON);
	}

	@Override
	public MessageManager sender() {
		return null;
	}

	@Override
	public TemplateManager templates() {
		return null;
	}

	@Override
	public BulkManager bulk() {
		return null;
	}

	@Override
	public Balance balance() throws IOException, ServerError {
		return this
				.call("balance.php")
				.with("cur", "1")
				.map(Balance.class);
	}

	@Override
	public ContactManager contacts() {
		return null;
	}

	@Override
	public UserManager users() {
		return null;
	}
}

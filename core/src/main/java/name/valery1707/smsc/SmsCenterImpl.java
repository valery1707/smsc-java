package name.valery1707.smsc;

import name.valery1707.smsc.contact.GroupManagerImpl;
import name.valery1707.smsc.contact.PhoneManagerImpl;
import name.valery1707.smsc.error.ServerError;
import name.valery1707.smsc.template.TemplateManager;

import java.io.IOException;

import static java.util.Objects.requireNonNull;
import static name.valery1707.smsc.utils.Digests.md5Hex;

public class SmsCenterImpl implements SmsCenter {
	public static final String DEFAULT_URL = "https://smsc.ru/sys/";
	private static final int FORMAT_JSON = 3;

	private final String serverUrl;
	private final HttpClient client;
	private final JsonMapper mapper;
	private final String username;
	private final String password;

	SmsCenterImpl(String serverUrl, HttpClient client, JsonMapper mapper, String username, String hashedPassword) {
		this.serverUrl = requireNonNull(serverUrl, "serverUrl is null").endsWith("/") ? serverUrl : serverUrl + "/";
		this.client = requireNonNull(client, "client is null");
		this.mapper = requireNonNull(mapper, "mapper is null");
		this.username = requireNonNull(username, "username is null");
		this.password = requireNonNull(hashedPassword, "password is null");
	}

	public SmsCenterImpl(String serverUrl, HttpClient client, JsonMapper mapper, String username, char[] clearPassword) {
		this(serverUrl, client, mapper, username, md5Hex(requireNonNull(clearPassword, "password is null")));
	}

	public SmsCenterImpl(HttpClient client, JsonMapper mapper, String username, char[] clearPassword) {
		this(DEFAULT_URL, client, mapper, username, clearPassword);
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
		return new TemplateManager(call("templates.php"));
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
				.single(Balance.class);
	}

	@Override
	public PhoneManager phones() {
		return new PhoneManagerImpl(call("phones.php"));
	}

	@Override
	public GroupManager groups() {
		return new GroupManagerImpl(call("phones.php"));
	}

	@Override
	public UserManager users() {
		return null;
	}
}

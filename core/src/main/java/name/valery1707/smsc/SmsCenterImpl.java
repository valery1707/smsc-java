package name.valery1707.smsc;

import name.valery1707.smsc.contact.GroupManagerImpl;
import name.valery1707.smsc.contact.PhoneManagerImpl;
import name.valery1707.smsc.error.ServerError;
import name.valery1707.smsc.message.MessageManager;
import name.valery1707.smsc.shared.ServerType;
import name.valery1707.smsc.template.TemplateManager;

import java.io.IOException;

import static java.util.Objects.requireNonNull;
import static name.valery1707.smsc.utils.Digests.md5Hex;

@SuppressWarnings("WeakerAccess")
public class SmsCenterImpl implements SmsCenter {
	public static final String DEFAULT_URL = "https://smsc.ru/sys/";

	@SuppressWarnings("unused")
	public enum Format implements ServerType {
		TEXT("0"), PLAIN("1"), XML("2"), JSON("3");

		private final String presentation;

		Format(String presentation) {
			this.presentation = presentation;
		}

		@Override
		public String presentation() {
			return presentation;
		}
	}

	private final String serverUrl;
	private final HttpClient client;
	private final JsonMapper mapper;
	private final String username;
	private final String password;

	/**
	 * @param serverUrl      Server base URL
	 * @param client         HTTP client
	 * @param mapper         Json mapper
	 * @param username       Client username
	 * @param hashedPassword Client password (already hashed for security reasons)
	 */
	SmsCenterImpl(String serverUrl, HttpClient client, JsonMapper mapper, String username, String hashedPassword) {
		this.serverUrl = requireNonNull(serverUrl, "serverUrl is null").endsWith("/") ? serverUrl : serverUrl + "/";
		this.client = requireNonNull(client, "client is null");
		this.mapper = requireNonNull(mapper, "mapper is null");
		this.username = requireNonNull(username, "username is null");
		this.password = requireNonNull(hashedPassword, "password is null");
	}

	/**
	 * @param serverUrl     Server base URL
	 * @param client        HTTP client
	 * @param mapper        Json mapper
	 * @param username      Client username
	 * @param clearPassword Client password (clear)
	 * @see <a href="https://stackoverflow.com/a/8881376">Why is char[] preferred over String for passwords?</a>
	 */
	public SmsCenterImpl(String serverUrl, HttpClient client, JsonMapper mapper, String username, char[] clearPassword) {
		this(serverUrl, client, mapper, username, md5Hex(requireNonNull(clearPassword, "password is null")));
	}

	/**
	 * @param client        HTTP client
	 * @param mapper        Json mapper
	 * @param username      Client username
	 * @param clearPassword Client password (clear)
	 * @see <a href="https://stackoverflow.com/a/8881376">Why is char[] preferred over String for passwords?</a>
	 */
	public SmsCenterImpl(HttpClient client, JsonMapper mapper, String username, char[] clearPassword) {
		this(DEFAULT_URL, client, mapper, username, clearPassword);
	}

	private RequestExecutor call(String url) {
		return new RequestExecutor(serverUrl + url, client, mapper)
				.with("login", username)
				.with("psw", password)
				.with("fmt", Format.JSON);
	}

	@Override
	public MessageManager messages() {
		return new MessageManager(call("send.php"));
	}

	@Override
	public TemplateManager templates() {
		return new TemplateManager(call("templates.php"));
	}

	@Override
	public BulkManager bulk() {
		throw new UnsupportedOperationException("Not implemented");
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
		throw new UnsupportedOperationException("Not implemented");
	}
}

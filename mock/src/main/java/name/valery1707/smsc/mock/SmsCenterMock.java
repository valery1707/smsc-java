package name.valery1707.smsc.mock;

import com.google.gson.FieldNamingPolicy;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.jcabi.manifests.Manifests;
import name.valery1707.smsc.shared.ServerBaseResponse;
import name.valery1707.smsc.utils.Digests;
import spark.Filter;
import spark.Service;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.*;

import static java.util.Collections.emptySet;

@SuppressWarnings("WeakerAccess")
public class SmsCenterMock {
	private final URL url;
	private Service server;
	private final Map<String, Set<String>> accounts = new HashMap<>();

	public SmsCenterMock() throws MalformedURLException {
		this(0, "/sys");
	}

	public SmsCenterMock(int port, String file) throws MalformedURLException {
		this(new URL("http", "localhost", port, file));
	}

	public SmsCenterMock(URL mockUrl) {
		url = mockUrl;
		server = Service.ignite();
		server.port(mockUrl.getPort());
		server.path(mockUrl.getPath(), () -> {
			Gson gson = new GsonBuilder()
					.setFieldNamingPolicy(FieldNamingPolicy.LOWER_CASE_WITH_UNDERSCORES)
					.create();

			//Authorization
			server.before("/*", authorization(gson));

			//Processing
			Database database = new Database();
			for (SmsController.Implementations impl : SmsController.Implementations.values()) {
				SmsController controller = impl.controller();
				controller.setDatabase(database);
				server.post("/" + controller.path(), controller, gson::toJson);
				server.get("/" + controller.path(), controller, gson::toJson);
			}

			//GZIP
			server.after((request, response) -> {
				response.header("Content-Encoding", "gzip");
				response.header("X-Powered-By", "smsc-mock/" + Manifests.read("SmsC-Mock-Version"));
			});
		});
	}

	private Filter authorization(Gson gson) {
		return (request, response) -> {
			if (!accounts.isEmpty()) {
				HashMap<String, String> params = BaseController.extractParams(request);
				String username = params.get("login");
				String password = params.get("psw");
				boolean known = accounts
						.getOrDefault(username, emptySet())
						.contains(password);
				if (!known) {
					ServerBaseResponse error = new ServerBaseResponse();
					error.setErrorCode(2);
					error.setError("Unknown account or invalid password");
					//noinspection ThrowableNotThrown
					server.halt(HttpServletResponse.SC_OK, gson.toJson(error));
				}
			}
		};
	}

	public void start() throws IOException {
		server.awaitInitialization();
	}

	public void stop() {
		server.stop();
		try {
			server.awaitInitialization();
		} catch (IllegalStateException ignored) {
		}
	}

	public int getPort() {
		return server.port();
	}

	public URL getUrl() {
		try {
			return new URL(url.getProtocol(), "localhost", getPort(), url.getFile());
		} catch (MalformedURLException e) {
			throw new IllegalStateException("Can not build mock URL", e);
		}
	}

	public SmsCenterMock withAccount(String username, String password) {
		accounts.put(username, new HashSet<>(Arrays.asList(password, Digests.md5Hex(password.toCharArray()))));
		return this;
	}
}

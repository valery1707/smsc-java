package name.valery1707.smsc;

import okhttp3.FormBody;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;

import java.io.IOException;
import java.util.Map;

public class HttpClientOkHttp implements HttpClient {
	private static final MediaType JSON
			= MediaType.parse("application/json; charset=utf-8");

	private final OkHttpClient client;

	public HttpClientOkHttp() {
		client = new OkHttpClient();
	}

	@Override
	public String execute(String url, Map<String, String> params) throws IOException {
		FormBody.Builder bodyBuilder = new FormBody.Builder();
		params.forEach(bodyBuilder::add);
		Request request = new Request.Builder()
				.url(url)
				.post(bodyBuilder.build()).build();
		return client.newCall(request).execute().body().string();
	}
}

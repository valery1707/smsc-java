package name.valery1707.smsc;

import java.io.IOException;
import java.util.Map;

public interface HttpClient {
	String execute(String url, Map<String, String> params) throws IOException;
}

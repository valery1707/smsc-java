package name.valery1707.smsc;

import java.io.IOException;
import java.util.List;

public interface JsonMapper {
	<T> T single(String json, Class<T> clazz) throws IOException;

	<T> List<T> multi(String json, Class<T> clazz) throws IOException;
}

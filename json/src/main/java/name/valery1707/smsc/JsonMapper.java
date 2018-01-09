package name.valery1707.smsc;

import java.io.IOException;

public interface JsonMapper {
	<T> T read(String json, Class<T> clazz) throws IOException;
}

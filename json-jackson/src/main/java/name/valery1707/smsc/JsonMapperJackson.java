package name.valery1707.smsc;

import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;

public class JsonMapperJackson implements JsonMapper {
	private final ObjectMapper mapper;

	public JsonMapperJackson() {
		mapper = new ObjectMapper()
				.findAndRegisterModules();
	}

	@Override
	public <T> T read(String json, Class<T> clazz) throws IOException {
		return mapper.readValue(json, clazz);
	}
}

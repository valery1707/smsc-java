package name.valery1707.smsc;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.type.CollectionType;

import java.io.IOException;
import java.util.List;

public class JsonMapperJackson implements JsonMapper {
	private final ObjectMapper mapper;

	public JsonMapperJackson() {
		mapper = new ObjectMapper()
				.findAndRegisterModules()
				.setPropertyNamingStrategy(PropertyNamingStrategy.SNAKE_CASE);
	}

	@Override
	public <T> T single(String json, Class<T> clazz) throws IOException {
		return mapper.readValue(json, clazz);
	}

	@Override
	public <T> List<T> multi(String json, Class<T> clazz) throws IOException {
		CollectionType type = mapper.getTypeFactory().constructCollectionType(List.class, clazz);
		return mapper.readValue(json, type);
	}
}

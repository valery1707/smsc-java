package name.valery1707.smsc.template;

import name.valery1707.smsc.SmsCenter;
import name.valery1707.smsc.error.InvalidCredentials;
import name.valery1707.smsc.error.InvalidId;
import name.valery1707.smsc.message.MessageTemplate;
import org.junit.Ignore;
import org.junit.Test;

import java.util.List;
import java.util.Optional;

import static name.valery1707.smsc.SmsCenterTest.centerDemo;
import static name.valery1707.smsc.SmsCenterTest.centerInvalid;
import static org.assertj.core.api.Assertions.assertThat;

public class TemplateManagerTest {
	@Test
	public void testListDemo() throws Exception {
		List<MessageTemplate> list = centerDemo().templates().list();
		assertThat(list)
				.isNotNull().isNotEmpty()
				.hasOnlyElementsOfType(MessageTemplate.class)
				.size().isPositive();
	}

	@Test(expected = InvalidCredentials.class)
	public void testListInvalid() throws Exception {
		List<MessageTemplate> list = centerInvalid().templates().list();
		assertThat(list).isNotNull().isEmpty();
	}

	@Test
	public void testCRUDDemo() throws Exception {
		SmsCenter center = centerDemo();

		List<MessageTemplate> listBefore = center.templates().list();
		MessageTemplate source = new MessageTemplate()
				.withFormat("sms")
				.withName("Java-test-name")
				.withMessage("java-test-message");

		Long id = center.templates().create(source);
		assertThat(id).isNotNull().isPositive();

		List<MessageTemplate> listAfter = center.templates().list();
		assertThat(listAfter)
				.containsAll(listBefore)
				.size().isEqualTo(listBefore.size() + 1);

		Optional<MessageTemplate> templateOptional = listAfter
				.stream()
				.filter(t -> id.equals(t.getId()))
				.findFirst();
		assertThat(templateOptional).isPresent();

		MessageTemplate created = templateOptional.get();
		assertThat(created).isNotNull();
		assertThat(created.getId()).isEqualTo(id);
		assertThat(created.getFormat()).isEqualTo(source.getFormat());
		assertThat(created.getMessage()).isEqualTo(source.getMessage());
		assertThat(created.getName()).isEqualTo(source.getName());
		assertThat(created.getSender()).isEqualTo(source.getSender());
		assertThat(created.getFlag()).isEqualTo(source.getFlag());

		boolean updated = center.templates().update(created);
		assertThat(updated).isNotNull().isTrue();

		boolean deleted = center.templates().delete(created);
		assertThat(deleted).isNotNull().isTrue();

		assertThat(center.templates().list()).containsOnlyElementsOf(listBefore);
	}

	@Test(expected = InvalidId.class)
	public void testUpdateUnknownDemo() throws Exception {
		centerDemo().templates().update(new MessageTemplate().withId(100500L).withName("Unknown").withMessage("Unknown"));
	}

	@Test(expected = InvalidId.class)
	public void testDeleteUnknownDemo() throws Exception {
		centerDemo().templates().delete(new MessageTemplate().withId(100500L).withName("Unknown").withMessage("Unknown"));
	}
}

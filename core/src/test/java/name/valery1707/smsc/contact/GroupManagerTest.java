package name.valery1707.smsc.contact;

import name.valery1707.smsc.SmsCenter.GroupManager;
import org.junit.Test;

import java.util.List;
import java.util.Optional;

import static name.valery1707.smsc.SmsCenterTest.centerDemo;
import static org.assertj.core.api.Assertions.assertThat;

public class GroupManagerTest {
	@Test
	public void testCRUD() throws Exception {
		GroupManager groups = centerDemo().groups();

		Group source = new Group()
				.withName("Java-test")
				.withNumber("17");

		List<Group> listBefore = groups.list();
		assertThat(listBefore)
				.isNotNull().isNotEmpty()
				.size().isPositive();//Default groups

		Long id = groups.create(source);
		assertThat(id).isNotNull().isPositive();

		List<Group> listAfterCreate = groups.list();
		assertThat(listAfterCreate)
				.hasOnlyElementsOfType(Group.class)
				.containsAll(listBefore)
				.hasSize(listBefore.size() + 1);

		Optional<Group> createdOpt = listAfterCreate
				.stream()
				.filter(group -> id.equals(group.getId()))
				.findFirst();
		assertThat(createdOpt).isPresent();

		Group created = createdOpt.get();
		assertThat(created).isNotNull();
		assertThat(created.getId()).isEqualTo(id);
		assertThat(created.getName()).isEqualTo(source.getName());
		assertThat(created.getNumber()).isEqualTo(source.getNumber());
		assertThat(created.getCnt()).isEqualTo("0");

		created.withName("Java-test2").withNumber("7");
		assertThat(groups.update(created)).isTrue();

		List<Group> listAfterUpdate = groups.list();
		assertThat(listAfterUpdate)
				.hasOnlyElementsOfType(Group.class)
				.hasSameSizeAs(listAfterCreate);

		Optional<Group> updatedOpt = listAfterUpdate
				.stream()
				.filter(group -> id.equals(group.getId()))
				.findFirst();
		assertThat(updatedOpt).isPresent();

		Group updated = updatedOpt.get();
		assertThat(updated).isNotNull();
		assertThat(updated.getId()).isEqualTo(id);
		assertThat(updated.getName()).isEqualTo(created.getName());
		assertThat(updated.getNumber()).isEqualTo(created.getNumber());
		assertThat(updated.getCnt()).isEqualTo("0");

		assertThat(groups.delete(updated)).isTrue();
	}
}

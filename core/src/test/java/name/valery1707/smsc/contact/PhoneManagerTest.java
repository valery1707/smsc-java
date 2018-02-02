package name.valery1707.smsc.contact;

import name.valery1707.smsc.SmsCenter;
import name.valery1707.smsc.SmsCenter.GroupManager;
import name.valery1707.smsc.SmsCenter.PhoneManager;
import name.valery1707.smsc.error.ServerError;
import org.junit.Test;

import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.function.Function;
import java.util.stream.Stream;

import static java.util.stream.Collectors.toList;
import static java.util.stream.Collectors.toMap;
import static name.valery1707.smsc.SmsCenterTest.centerTest;
import static org.assertj.core.api.Assertions.assertThat;

public class PhoneManagerTest {
	@Test
	public void testCRUD() throws Exception {
		SmsCenter center = centerTest();
		PhoneManager phones = center.phones();

		Phone source = new Phone()
				.withId(100500L)
				.withName("Java-phone")
				.withPhone("79050533578")
//				.withGroup("100500")//Only from exists groups
				.withFirstName("java-first")
				.withMiddleName("java-middle")
				.withLastName("java-last")
				.withBirthday("17.07.1983")
				.withComments("java-comments")
				.withTags("java-tag1, java-tag2")
				.withPhoneOther("77014541882,79050533678");

		//List: before
		List<Phone> listBefore = phones.list();
		assertThat(listBefore).isNotNull().isEmpty();
		assertThat(listBefore)
				.isNotNull().isEmpty();
		List<Long> idsBefore = listBefore
				.stream()
				.map(Phone::getId)
				.filter(Objects::nonNull)
				.collect(toList());
		assertThat(idsBefore).isEmpty();

		//Create
		Long id = phones.create(source);
		assertThat(id).isNotNull().isPositive();

		//List: After create
		Thread.sleep(15_000);
		List<Phone> listAfterCreate = phones.list();
		assertThat(listAfterCreate)
				.hasOnlyElementsOfType(Phone.class)
				.containsAll(listBefore)
				.size().isEqualTo(listBefore.size() + 1);

		Optional<Phone> createdOpt = listAfterCreate
				.stream()
				.filter(phone -> source.getPhone().equals(phone.getPhone()))
				.findFirst();
		assertThat(createdOpt).isPresent();

		Phone created = createdOpt.get();
		assertThat(created).isNotNull();
		assertThat(created.getId()).isEqualTo(source.getId());
		assertThat(created.getName()).isEqualTo(source.getName());
		assertThat(created.getPhone()).isEqualTo(source.getPhone());
		assertThat(created.getGroup()).isEqualTo("0");
		assertThat(created.getFirstName()).isEqualTo(source.getFirstName());
		assertThat(created.getMiddleName()).isEqualTo(source.getMiddleName());
		assertThat(created.getLastName()).isEqualTo(source.getLastName());
		assertThat(created.getBirthday()).isEqualTo(source.getBirthday());
		assertThat(created.getComments()).isEqualTo(source.getComments());
		assertThat(created.getTags()).isEqualTo(source.getTags());
		assertThat(created.getPhoneOther()).isEqualTo(source.getPhoneOther());

		//Update: full
		source
				.withName(source.getName() + "~2")
//				.withGroup(source.getGroup() + "~2")
				.withFirstName(source.getFirstName() + "~2")
				.withMiddleName(source.getMiddleName() + "~2")
				.withLastName(source.getLastName() + "~2")
				.withBirthday(source.getBirthday().replace("17.07", "23.06"))
				.withComments(source.getComments() + "~2")
				.withTags(source.getTags() + "~2")
				.withPhoneOther(source.getPhoneOther() + "~2")
		;
		assertThat(phones.update(source)).isTrue();

		//List: After update full
		Thread.sleep(15_000);
		List<Phone> listAfterUpdate1 = phones
				.listByPhone(source.getPhone());
		assertThat(listAfterUpdate1)
				.containsAll(listBefore)
				.size().isEqualTo(listBefore.size() + 1);
		Optional<Phone> updated1Opt = listAfterUpdate1
				.stream()
				.filter(phone -> source.getPhone().equals(phone.getPhone()))
				.findFirst();
		assertThat(createdOpt).isPresent();

		Phone updated1 = updated1Opt.get();
		assertThat(updated1).isNotNull();
		assertThat(updated1.getId()).isEqualTo(source.getId());
		assertThat(updated1.getName()).isEqualTo(source.getName());
		assertThat(updated1.getPhone()).isEqualTo(source.getPhone());
		assertThat(updated1.getGroup()).isEqualTo("0");
		assertThat(updated1.getFirstName()).isEqualTo(source.getFirstName());
		assertThat(updated1.getMiddleName()).isEqualTo(source.getMiddleName());
		assertThat(updated1.getLastName()).isEqualTo(source.getLastName());
		assertThat(updated1.getBirthday()).isEqualTo(source.getBirthday());
		assertThat(updated1.getComments()).isEqualTo(source.getComments());
		assertThat(updated1.getTags()).isEqualTo(source.getTags());
		assertThat(updated1.getPhoneOther()).isEqualTo(source.getPhoneOther());

		//Update: number
		Thread.sleep(15_000);
		assertThat(phones.update(updated1, "79050533678")).isTrue();

		//List: After update number
		Thread.sleep(15_000);
		List<Phone> listAfterUpdate2 = phones
				.listByFio(source.getLastName());
		assertThat(listAfterUpdate2)
				.containsAll(listBefore)
				.size().isEqualTo(listBefore.size() + 1);
		Optional<Phone> updated2Opt = listAfterUpdate2
				.stream()
				.filter(phone -> "79050533678".equals(phone.getPhone()))
				.findFirst();
		assertThat(updated2Opt).isPresent();

		Phone updated2 = updated2Opt.get();
		assertThat(updated2).isNotNull();
		assertThat(updated2.getId()).isEqualTo(source.getId());
		assertThat(updated2.getName()).isEqualTo(source.getName());
		assertThat(updated2.getPhone()).isNotEqualTo(source.getPhone()).isEqualTo("79050533678");
		assertThat(updated2.getGroup()).isEqualTo("0");
		assertThat(updated2.getFirstName()).isEqualTo(source.getFirstName());
		assertThat(updated2.getMiddleName()).isEqualTo(source.getMiddleName());
		assertThat(updated2.getLastName()).isEqualTo(source.getLastName());
		assertThat(updated2.getBirthday()).isEqualTo(source.getBirthday());
		assertThat(updated2.getComments()).isEqualTo(source.getComments());
		assertThat(updated2.getTags()).isEqualTo(source.getTags());
		assertThat(updated2.getPhoneOther()).isEqualTo(source.getPhoneOther());

		//Groups: Create
		GroupManager groups = center.groups();
		List<Long> groupIds = Stream.of(1, 2, 3, 4)
				.map(n -> new Group().withName("Test" + n).withNumber("" + n))
				.map(group -> {
					try {
						return groups.create(group);
					} catch (IOException | ServerError e) {
						throw new IllegalStateException(e);
					}
				})
				.collect(toList());
		Map<Long, Group> groupById = groups
				.list()
				.stream()
				.collect(toMap(Group::getId, Function.identity()));
		assertThat(groupById).hasSize(
				2/*дефолтные группы*/
				+ groupIds.size()
				- 1/*одна дефолтная пропадает иногда*/);
		Group group1 = groupById.get(groupIds.get(0));
		Group group2 = groupById.get(groupIds.get(1));
		Group group3 = groupById.get(groupIds.get(2));
		Group group4 = groupById.get(groupIds.get(3));

		//Groups: Use
		Thread.sleep(15_000);
		assertThat(phones.groupInclude(updated2, group1)).isTrue();
		assertThat(phones.groupMove(updated2, group2)).isTrue();
		assertThat(phones.groupInclude(updated2, group3)).isTrue();
		assertThat(phones.groupInclude(updated2, group4)).isTrue();
		assertThat(phones.groupExclude(updated2, group4)).isTrue();

		//Groups: Test
		Thread.sleep(15_000);
		List<Phone> listInGroup3 = phones.listByGroup(group3);
		assertThat(listInGroup3)
				.isNotNull()
				.hasOnlyElementsOfType(Phone.class)
				.hasSize(1);
		Phone inGroup3 = listInGroup3.get(0);
		assertThat(inGroup3).isNotNull();
		assertThat(inGroup3.getId()).isEqualTo(updated2.getId());
		assertThat(inGroup3.getName()).isEqualTo(updated2.getName());
		assertThat(inGroup3.getPhone()).isEqualTo(updated2.getPhone());
		assertThat(inGroup3.getGroupIds())
				.isNotNull().isNotEmpty()
				.containsOnly(group2.getId(), group3.getId());
		assertThat(inGroup3.getFirstName()).isEqualTo(updated2.getFirstName());
		assertThat(inGroup3.getMiddleName()).isEqualTo(updated2.getMiddleName());
		assertThat(inGroup3.getLastName()).isEqualTo(updated2.getLastName());
		assertThat(inGroup3.getBirthday()).isEqualTo(updated2.getBirthday());
		assertThat(inGroup3.getComments()).isEqualTo(updated2.getComments());
		assertThat(inGroup3.getTags()).isEqualTo(updated2.getTags());
		assertThat(inGroup3.getPhoneOther()).isEqualTo(updated2.getPhoneOther());

		//Delete
		boolean deleted = phones.delete(updated2);
		assertThat(deleted).isTrue();

		//Create: With groups
		Thread.sleep(15_000);
		id = phones.create(source.withGroupIds(group1.getId(), group2.getId()));
		assertThat(id).isNotNull().isPositive();
		Thread.sleep(15_000);
		List<Phone> listInGroup1 = phones.listByGroup(group1);
		assertThat(listInGroup1).isNotNull().isNotEmpty().hasSize(1);
		assertThat(listInGroup1.get(0).getGroupIds()).isNotNull().isNotEmpty().containsOnly(group1.getId(), group2.getId());

		//Groups: Clear
		for (Group group : groupIds.stream().map(groupById::get).collect(toList())) {
			assertThat(groups.delete(group)).isTrue();
		}
		Thread.sleep(15_000);
		assertThat(phones.listByGroup(group2)).isNotNull().isEmpty();

		Thread.sleep(15_000);
		assertThat(phones.search("server has empty list")).containsOnlyElementsOf(listBefore);

		//List: empty lists
		Thread.sleep(15_000);
		assertThat(phones.listByPhone("77014541882")).isNotNull().isEmpty();
		Thread.sleep(15_000);
		assertThat(phones.listByFio(source.getFirstName())).isNotNull().isEmpty();
	}
}

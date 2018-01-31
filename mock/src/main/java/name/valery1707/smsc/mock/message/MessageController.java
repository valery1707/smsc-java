package name.valery1707.smsc.mock.message;

import com.google.i18n.phonenumbers.NumberParseException;
import com.google.i18n.phonenumbers.PhoneNumberUtil;
import com.google.i18n.phonenumbers.Phonenumber.PhoneNumber;
import name.valery1707.smsc.error.InvalidParameters;
import name.valery1707.smsc.error.ServerError;
import name.valery1707.smsc.message.MessageCost;
import name.valery1707.smsc.message.MessagePhone;
import name.valery1707.smsc.mock.BaseController;
import spark.Request;
import spark.Response;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static java.util.function.Function.identity;
import static java.util.stream.Collectors.toList;
import static java.util.stream.Collectors.toMap;

public class MessageController extends BaseController<MessageCost> {
	private final PhoneNumberUtil phoneNumberUtil;

	public MessageController() {
		phoneNumberUtil = PhoneNumberUtil.getInstance();
	}

	@Override
	public String path() {
		return "send.php";
	}

	@Override
	protected MessageCost handle(HashMap<String, String> params, Request request, Response response) throws ServerError {
		List<PhoneNumber> phones = Stream
				.of(params.get("phones").split(","))
				.map(String::trim)
				.map(this::parse)
				.collect(Collectors.toList());
		if (phones.contains(null)) {
			throw new InvalidParameters("Some phone numbers is invalid");
		}
		LinkedHashMap<PhoneNumber, BigDecimal> costByPhone = phones.stream().collect(toMap(identity(), database()::price, (a, b) -> a, LinkedHashMap::new));
		BigDecimal costTotal = costByPhone.values().stream().reduce(BigDecimal.ZERO, BigDecimal::add);
		boolean isCost = params.containsKey("cost");
		if (isCost) {
			MessageCost cost = new MessageCost();
			cost.setCnt(phones.size());
			cost.setCost(costTotal);
			cost.setPhones(costByPhone.entrySet()
					.stream()
					.map(e -> {
						MessagePhone phone = new MessagePhone();
						phone.setPhone(phoneNumberUtil.format(e.getKey(), PhoneNumberUtil.PhoneNumberFormat.E164).replace("+", ""));
						phone.setCost(e.getValue());
						phone.setMccmnc("253");//todo More correct value
						return phone;
					})
					.collect(toList())
			);
			return cost;
		} else {
			return null;
		}
	}

	private PhoneNumber parse(String phone) {
		if (!phone.startsWith("+")) {
			phone = "+" + phone;
		}
		try {
			PhoneNumber number = phoneNumberUtil.parse(phone, "ZZ");
			if (number != null && phoneNumberUtil.isValidNumber(number)) {
				return number;
			}
		} catch (NumberParseException ignored) {
		}
		return null;
	}
}

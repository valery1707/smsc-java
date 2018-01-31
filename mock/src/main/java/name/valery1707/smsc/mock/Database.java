package name.valery1707.smsc.mock;

import com.google.i18n.phonenumbers.Phonenumber.PhoneNumber;
import name.valery1707.smsc.Balance;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import java.util.Random;

@SuppressWarnings("WeakerAccess")
public class Database {
	private final Random random = new Random();
	private final Map<String, Balance> balances = new HashMap<>();
	private final Map<String, BigDecimal> prices = new HashMap<>();

	private int random(int minInc, int maxInc) {
		return minInc + random.nextInt(maxInc - minInc + 1);
	}

	private double random(double minInc, double maxInc) {
		return minInc + (random.nextDouble() * (maxInc - minInc + 1));
	}

	private BigDecimal randomBig(double minInc, double maxInc) {
		return new BigDecimal(String.format(Locale.ENGLISH, "%.2f", random(minInc, maxInc)));
	}

	public Balance balance(String username) {
		return balances.computeIfAbsent(username, v -> balance());
	}

	private Balance balance() {
		Balance balance = new Balance();
		balance.setCurrency("RUB");
		balance.setCredit(BigDecimal.ZERO);
		balance.setBalance(randomBig(1.0, 100.0));
		return balance;
	}

	public BigDecimal price(PhoneNumber number) {
		String carrier = String
				.valueOf(number.getNationalNumber())
				.substring(0, 3);
		return prices.computeIfAbsent(carrier.intern(), v -> randomBig(0.7, 4.0));
	}
}

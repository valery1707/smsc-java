package name.valery1707.smsc.utils;

import javax.annotation.Nonnull;
import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Objects;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.function.Supplier;
import java.util.regex.Pattern;

@SuppressWarnings({"WeakerAccess", "SameParameterValue"})
public class Validation implements Iterable<String> {
	private final List<String> errors = new ArrayList<>();

	public Validation() {
	}

	public boolean isValid() {
		return errors.isEmpty();
	}

	public boolean nonValid() {
		return !isValid();
	}

	@Override
	@Nonnull
	public Iterator<String> iterator() {
		return errors.iterator();
	}

	public Validation addError(String error) {
		errors.add(error);
		return this;
	}

	public Validation validate(boolean valid, Supplier<String> message) {
		if (valid) {
			return this;
		} else {
			return addError(message.get());
		}
	}

	public Validation validate(boolean valid, String message) {
		return validate(valid, () -> message);
	}

	public <T> Validation validate(T value, Predicate<T> predicate, Function<T, String> message) {
		return validate(predicate.test(value), () -> message.apply(value));
	}

	public <T> Validation checkIsNull(T value, Function<T, String> message) {
		return validate(value, Objects::isNull, message);
	}

	public <T> Validation checkIsNull(T value, Supplier<String> message) {
		return checkIsNull(value, v -> message.get());
	}

	public <T> Validation checkIsNull(T value, String message) {
		return checkIsNull(value, () -> message);
	}

	private static final MessageFormat FIELD_IS_NULL = new MessageFormat("Field ''{0}'' must be null");

	public <T> Validation fieldIsNull(T value, String field) {
		return checkIsNull(value, () -> FIELD_IS_NULL.format(new Object[]{field}));
	}

	public <T> Validation checkNonNull(T value, Function<T, String> message) {
		return validate(value, Objects::nonNull, message);
	}

	public <T> Validation checkNonNull(T value, Supplier<String> message) {
		return checkNonNull(value, v -> message.get());
	}

	public <T> Validation checkNonNull(T value, String message) {
		return checkNonNull(value, () -> message);
	}

	private static final MessageFormat FIELD_NON_NULL = new MessageFormat("Field ''{0}'' must be non null");

	public <T> Validation fieldNonNull(T value, String field) {
		return checkNonNull(value, () -> FIELD_NON_NULL.format(new Object[]{field}));
	}

	private static final MessageFormat FIELD_MATCHES_NULLABLE = new MessageFormat("Field ''{0}'' can be null or must matches with pattern ''{1}''");
	private static final MessageFormat FIELD_MATCHES_NONNULL = new MessageFormat("Field ''{0}'' must matches with pattern ''{1}''");

	public Validation fieldMatches(String value, String field, boolean canBeNull, Pattern pattern) {
		return validate(value
				, v ->
						(v == null && canBeNull)
						||
						(v != null && pattern.matcher(v).matches())
				, v -> (canBeNull ? FIELD_MATCHES_NULLABLE : FIELD_MATCHES_NONNULL).format(new Object[]{field, pattern})
		);
	}

	private static String range(Integer minInclusive, Integer maxInclusive) {
		StringBuilder str = new StringBuilder("range ");
		if (minInclusive == null) {
			str.append("(");
		} else {
			str.append("[").append(minInclusive);
		}
		str.append(", ");
		if (maxInclusive == null) {
			str.append(")");
		} else {
			str.append(maxInclusive).append("]");
		}
		return str.toString();
	}

	private static final MessageFormat FIELD_LENGTH_NULLABLE = new MessageFormat("Field ''{0}'' can be null or must have size in {1}");
	private static final MessageFormat FIELD_LENGTH_NONNULL = new MessageFormat("Field ''{0}'' must have size in {1}");

	public Validation fieldLength(String value, String field, boolean canBeNull, Integer minInclusive, Integer maxInclusive) {
		return validate(value
				, v -> (v == null && canBeNull)
					   ||
					   (
							   (minInclusive == null || (v != null && v.length() >= minInclusive))
							   &&
							   (maxInclusive == null || (v != null && v.length() <= maxInclusive))
					   )
				, v -> (canBeNull ? FIELD_LENGTH_NULLABLE : FIELD_LENGTH_NONNULL).format(new Object[]{field, range(minInclusive, maxInclusive)})
		);
	}

	public Validation fieldLengthMin(String value, String field, boolean canBeNull, int minInclusive) {
		return fieldLength(value, field, canBeNull, minInclusive, null);
	}

	public Validation fieldLengthMax(String value, String field, boolean canBeNull, int maxInclusive) {
		return fieldLength(value, field, canBeNull, null, maxInclusive);
	}
}

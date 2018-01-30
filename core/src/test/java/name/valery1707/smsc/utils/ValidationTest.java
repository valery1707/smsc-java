package name.valery1707.smsc.utils;

import org.junit.Test;

import java.util.regex.Pattern;

import static org.assertj.core.api.Assertions.assertThat;

public class ValidationTest {
	private static final String VALUE = "value";
	private static final String FAIL = "fail";
	private static final String MESSAGE = "message";
	private static final String FIELD = "field";

	private static Validation v() {
		return new Validation();
	}

	@Test
	public void testEmpty() throws Exception {
		assertThat(v()).isNotNull().isEmpty();
		assertThat(v().isValid()).isTrue();
		assertThat(v().nonValid()).isFalse();
	}

	@Test
	public void testValidate_BooleanString() throws Exception {
		assertThat(v().validate(true, MESSAGE)).isEmpty();
		assertThat(v().validate(false, MESSAGE)).isNotEmpty();
	}

	@Test
	public void testCheckIsNull_ValueString() throws Exception {
		assertThat(v().checkIsNull(null, MESSAGE)).isEmpty();
		assertThat(v().checkIsNull(VALUE, MESSAGE)).isNotEmpty();
	}

	@Test
	public void testFieldIsNull_Value() throws Exception {
		assertThat(v().fieldIsNull(null, FIELD)).isEmpty();
		assertThat(v().fieldIsNull(VALUE, FIELD)).isNotEmpty().containsOnly("Field 'field' must be null");
	}

	@Test
	public void testCheckNonNull_ValueString() throws Exception {
		assertThat(v().checkNonNull(VALUE, MESSAGE)).isEmpty();
		assertThat(v().checkNonNull(null, MESSAGE)).isNotEmpty();
	}

	@Test
	public void testFieldNonNull_Value() throws Exception {
		assertThat(v().fieldNonNull(VALUE, FIELD)).isEmpty();
		assertThat(v().fieldNonNull(null, FIELD)).isNotEmpty().containsOnly("Field 'field' must be non null");
	}

	private static final Pattern IS_VALUE = Pattern.compile(VALUE);

	@Test
	public void testFieldMatches_Nullable() throws Exception {
		assertThat(v().fieldMatches(null, FIELD, true, IS_VALUE)).isEmpty();
		assertThat(v().fieldMatches(VALUE, FIELD, true, IS_VALUE)).isEmpty();
		assertThat(v().fieldMatches(FAIL, FIELD, true, IS_VALUE)).isNotEmpty().containsOnly("Field 'field' can be null or must matches with pattern 'value'");
	}

	@Test
	public void testFieldMatches_Nonnull() throws Exception {
		assertThat(v().fieldMatches(null, FIELD, false, IS_VALUE)).isNotEmpty();
		assertThat(v().fieldMatches(VALUE, FIELD, false, IS_VALUE)).isEmpty();
		assertThat(v().fieldMatches(FAIL, FIELD, false, IS_VALUE)).isNotEmpty().containsOnly("Field 'field' must matches with pattern 'value'");
	}

	@Test
	public void testFieldLengthMin_Nullable() throws Exception {
		assertThat(v().fieldLengthMin(null, FIELD, true, 100)).isEmpty();
		assertThat(v().fieldLengthMin(VALUE, FIELD, true, VALUE.length() - 1)).isEmpty();
		assertThat(v().fieldLengthMin(VALUE, FIELD, true, VALUE.length())).isEmpty();
		assertThat(v().fieldLengthMin(VALUE, FIELD, true, VALUE.length() + 1)).isNotEmpty().containsOnly("Field 'field' can be null or must have size in range [6, )");
	}

	@Test
	public void testFieldLengthMin_Nonnull() throws Exception {
		assertThat(v().fieldLengthMin(null, FIELD, false, 100)).isNotEmpty().containsOnly("Field 'field' must have size in range [100, )");
		assertThat(v().fieldLengthMin(VALUE, FIELD, false, VALUE.length() - 1)).isEmpty();
		assertThat(v().fieldLengthMin(VALUE, FIELD, false, VALUE.length())).isEmpty();
		assertThat(v().fieldLengthMin(VALUE, FIELD, false, VALUE.length() + 1)).isNotEmpty().containsOnly("Field 'field' must have size in range [6, )");
	}

	@Test
	public void testFieldLengthMax_Nullable() throws Exception {
		assertThat(v().fieldLengthMax(null, FIELD, true, 100)).isEmpty();
		assertThat(v().fieldLengthMax(VALUE, FIELD, true, VALUE.length() - 1)).isNotEmpty().containsOnly("Field 'field' can be null or must have size in range (, 4]");
		assertThat(v().fieldLengthMax(VALUE, FIELD, true, VALUE.length())).isEmpty();
		assertThat(v().fieldLengthMax(VALUE, FIELD, true, VALUE.length() + 1)).isEmpty();
	}

	@Test
	public void testFieldLengthMax_Nonnull() throws Exception {
		assertThat(v().fieldLengthMax(null, FIELD, false, 100)).isNotEmpty().containsOnly("Field 'field' must have size in range (, 100]");
		assertThat(v().fieldLengthMax(VALUE, FIELD, false, VALUE.length() - 1)).isNotEmpty().containsOnly("Field 'field' must have size in range (, 4]");
		assertThat(v().fieldLengthMax(VALUE, FIELD, false, VALUE.length())).isEmpty();
		assertThat(v().fieldLengthMax(VALUE, FIELD, false, VALUE.length() + 1)).isEmpty();
	}

	@Test
	public void testFieldLength_Nullable() throws Exception {
		assertThat(v().fieldLength(null, FIELD, true, null, null)).isEmpty();
		assertThat(v().fieldLength(null, FIELD, true, 1, 100)).isEmpty();
		assertThat(v().fieldLength(VALUE, FIELD, true, VALUE.length() - 1, VALUE.length() + 1)).isEmpty();
		assertThat(v().fieldLength(VALUE, FIELD, true, VALUE.length(), VALUE.length())).isEmpty();
		assertThat(v().fieldLength(VALUE, FIELD, true, VALUE.length() / 2, VALUE.length() / 2)).isNotEmpty().containsOnly("Field 'field' can be null or must have size in range [2, 2]");
	}

	@Test
	public void testFieldLength_Nonnull() throws Exception {
		assertThat(v().fieldLength(null, FIELD, false, null, null)).isEmpty();
		assertThat(v().fieldLength(null, FIELD, false, 1, 100)).isNotEmpty().containsOnly("Field 'field' must have size in range [1, 100]");
		assertThat(v().fieldLength(VALUE, FIELD, false, VALUE.length() - 1, VALUE.length() + 1)).isEmpty();
		assertThat(v().fieldLength(VALUE, FIELD, false, VALUE.length(), VALUE.length())).isEmpty();
		assertThat(v().fieldLength(VALUE, FIELD, false, VALUE.length() / 2, VALUE.length() / 2)).isNotEmpty().containsOnly("Field 'field' must have size in range [2, 2]");
	}
}

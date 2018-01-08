package name.valery1707.smsc.message;

import javax.annotation.MatchesPattern;
import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.regex.Pattern;

public interface Message {
	String ID_REGEXP =
			"^" +
			"[a-zA-Z0-9._-]{1,40}" +
			"$";
	Pattern ID_PATTERN = Pattern.compile(ID_REGEXP);

	@Nullable
	@MatchesPattern(ID_REGEXP)
	String getId();

	@Nonnull
	String getText();

	@Nullable
	Transliterate transliterate();

	@Nullable
	Boolean tinyUrl();

	@Nullable
	Boolean flash();

	@Nullable
	Boolean binary();

	@Nullable
	Boolean wapPush();

	@Nullable
	Boolean hlr();

	@Nullable
	Boolean ping();

	@Nullable
	Boolean mms();

	@Nullable
	Boolean mail();

	@Nullable
	Boolean call();

	@Nullable
	CallVoice callVoice();

	@Nullable
	CallParam callParam();
}

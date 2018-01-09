package name.valery1707.smsc.error;

public class UnknownServerError extends ServerError {
	public UnknownServerError(String message) {
		super(message);
	}

	public UnknownServerError(String message, Throwable cause) {
		super(message, cause);
	}
}

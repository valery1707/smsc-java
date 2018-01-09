package name.valery1707.smsc.error;

public class ServerError extends Exception {
	public ServerError() {
	}

	public ServerError(String message) {
		super(message);
	}

	public ServerError(String message, Throwable cause) {
		super(message, cause);
	}
}

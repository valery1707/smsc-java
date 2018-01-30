package name.valery1707.smsc.shared;

public class ServerBaseResponse {
	private String error;
	private Integer errorCode;

	public String getError() {
		return error;
	}

	public void setError(String error) {
		this.error = error;
	}

	public Integer getErrorCode() {
		return errorCode;
	}

	public void setErrorCode(Integer errorCode) {
		this.errorCode = errorCode;
	}

	public boolean isError() {
		return errorCode != null;
	}
}

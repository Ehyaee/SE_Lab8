package usermanagement.exception;

public class UserNotFoundException extends RuntimeException {

	private final Integer userId;

	public UserNotFoundException(String message, Integer userId) {
		super(message);
		this.userId = userId;
	}

	public Integer getUserId() {
		return userId;
	}
}

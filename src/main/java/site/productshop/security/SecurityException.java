package site.productshop.security;

public class SecurityException extends Exception {

	public SecurityException() {
		super();
	}
	
	public SecurityException(String message, Throwable cause) {
		super(message, cause);
	}
	
	public SecurityException(String message) {
		super(message);
	}
}

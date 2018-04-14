package site.productshop.config;

public class ConfigException extends Exception {

	public ConfigException() {
		super();
	}
	
	public ConfigException(String message, Throwable cause) {
		super(message, cause);
	}
	
	public ConfigException(String message) {
		super(message);
	}
}
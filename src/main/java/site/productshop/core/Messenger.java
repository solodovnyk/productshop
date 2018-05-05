package site.productshop.core;

import java.util.ArrayList;

public class Messenger {
	private ArrayList<String> successMessages = new ArrayList<>();
	private ArrayList<String> errorMessages = new ArrayList<>();
	
	public void addSuccessMessage(String message) {
		successMessages.add(message);
	}
	
	public ArrayList<String> getSuccessMessages() {
		return successMessages;
	}
	
	public void addErrorMessage(String message) {
		errorMessages.add(message);
	}
	
	public ArrayList<String> getErrorMessages() {
		return errorMessages;
	}
}
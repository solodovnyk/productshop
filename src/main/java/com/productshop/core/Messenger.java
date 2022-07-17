package com.productshop.core;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import java.util.ArrayList;

import static org.springframework.web.context.WebApplicationContext.SCOPE_REQUEST;

@Component
@Scope(SCOPE_REQUEST)
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
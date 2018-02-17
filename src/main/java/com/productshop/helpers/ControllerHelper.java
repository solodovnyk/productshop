package com.productshop.helpers;

public class ControllerHelper {

	public static String getShortControllerName(String controllerName) {
		return controllerName.substring(0, controllerName.indexOf("Controller"))
				.toLowerCase();
	}
	
	public static String getShortActionName(String actionName) {
		return actionName.substring(6).toLowerCase();
	}

}

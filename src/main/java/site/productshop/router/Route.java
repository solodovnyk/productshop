package site.productshop.router;

public class Route {
	private boolean isValid;
	private String controllerName;
	private String actionName;
	private String[] params;
	
	public Route(String controllerName, String actionName, String[] params) {
		this.setValid(true);
		this.controllerName = controllerName;
		this.actionName = actionName;
		this.params = params;
	}
	
	public String getControllerName() {
		return controllerName;
	}

	public String getActionName() {
		return actionName;
	}

	public String[] getParams() {
		return params;
	}

	public boolean isValid() {
		return isValid;
	}

	public void setValid(boolean isValid) {
		this.isValid = isValid;
	}
}
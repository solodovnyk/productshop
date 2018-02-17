package com.productshop.config;

public class ConfigurationComponent {
	
	private String componentID;
	private String name;
	private String value;
	
	public ConfigurationComponent(String componentID) throws ConfigException {
		if(componentID == null) throw new ConfigException("ID компонента не должно быть NULL.");
		this.componentID = componentID;
	}
	
	public String getName() {
		return name;
	}
	
	public void setName(String name) {
		this.name = name;
	}
	
	public String getValue() {
		return value;
	}
	
	public void setValue(String value) {
		this.value = value;
	}
	
	public String getID() {
		return componentID;
	}
}

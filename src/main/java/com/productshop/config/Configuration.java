package com.productshop.config;

import java.util.LinkedHashMap;
import java.util.Set;

public class Configuration {
	
	private LinkedHashMap<String, ConfigurationComponent> componentsSet = new LinkedHashMap<>();
	private String configurationName;
	
	public Configuration(String configurationName) throws ConfigException {
		if(configurationName == null) throw new ConfigException("Имя конфигурации не должно быть NULL.");
		this.configurationName = configurationName;
	}
	
	public void addComponent(ConfigurationComponent component) {
		componentsSet.put(component.getID(), component);
	}
	
	public String getName() {
		return configurationName;
	}
	
	public int getSize() {
		return componentsSet.size();
	}
	
	public Set<String> getComponentList() {
		return componentsSet.keySet();
	}
	
	public ConfigurationComponent getComponent(String componentID) {
		return componentsSet.get(componentID);
	}
}
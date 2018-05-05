package site.productshop.config;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Set;

import javax.xml.stream.XMLInputFactory;
import javax.xml.stream.XMLOutputFactory;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamReader;
import javax.xml.stream.XMLStreamWriter;

public class Configurations {
	
	private static Configurations configurationsInstance = null;
	private LinkedHashMap<String, Configuration> configurationsSet = new LinkedHashMap<>();
	private String fullFilePath;
	
	private Configurations(String filePath) throws ConfigException {
		try {
			setConfigurations(filePath);
		} catch (Exception e) {
			throw new ConfigException(e.getMessage(), e);
		}
	}
	
	private void setConfigurations(String filePath) throws ConfigException {
		
		Configuration currentConfiguration = null;
		ConfigurationComponent currentComponent = null;
		String componentValue = null;
		
		XMLStreamReader xmlr = null;
		
		try {
			xmlr = XMLInputFactory.newInstance()
					.createXMLStreamReader(new FileInputStream(filePath), "UTF-8");
			
			while (xmlr.hasNext()) {
                xmlr.next();
                if (xmlr.isStartElement()) {
                	
                	if(xmlr.getLocalName() == "configuration") {
                		String configurationName = xmlr.getAttributeValue(0);
                		currentConfiguration = new Configuration(configurationName);
                		configurationsSet.put(configurationName, currentConfiguration);
                	}
                	
                	if(xmlr.getLocalName() == "configuration-component") {
                		String componentID = xmlr.getAttributeValue(0);
                		currentComponent = new ConfigurationComponent(componentID);
                		currentConfiguration.addComponent(currentComponent);
                	}
                	
                	if(xmlr.getLocalName() == "name") {
                		componentValue = "name";
                	}
                	
                	if(xmlr.getLocalName() == "value") {
                		componentValue = "value";
                	}
                	
                } else if (xmlr.isEndElement()) {
                	
                	if(xmlr.getLocalName() == "/configuration") {
                		currentConfiguration = null;
                	}
                	
                	if(xmlr.getLocalName() == "/configuration-component") {
                		currentComponent = null;
                	}
                	
                	if(xmlr.getLocalName() == "name") {
                		componentValue = null;
                	}
                	
                	if(xmlr.getLocalName() == "value") {
                		componentValue = null;
                	}
                	
                } else if (xmlr.hasText() && xmlr.getText().trim().length() > 0) {
                	
                	String text = xmlr.getText();
                	
                	if(currentComponent != null && componentValue.equals("name")) {
                		currentComponent.setName(text);
                	}
                	
                	if(currentComponent != null && componentValue.equals("value")) {
                		currentComponent.setValue(text);
                	}
                }
            }
		} catch (Exception e) {
			throw new ConfigException(e.getMessage(), e);
		} finally {
			if(xmlr != null) {
				try {
					xmlr.close();
				} catch (XMLStreamException e) {
					throw new ConfigException(e.getMessage(), e);
				}
			}
		}
	}

	public static Configurations createConfigurations(String filePath) throws ConfigException {
		
		if(configurationsInstance != null) {
			return null;
		}
		
		return configurationsInstance = new Configurations(filePath);
	}
	
	public static void removeConfigurations() {
		configurationsInstance = null;
	}
	
	public static Configurations getConfigurations() {
		return configurationsInstance;
	}
	
	public Set<String> getConfigurationsList() {
		
		if(configurationsSet.size() == 0) {
			return null;
		}
		
		return configurationsSet.keySet();
	}
	
	public Configuration getConfiguration(String configurationName) {
		return configurationsSet.get(configurationName);
	}
	
	public void saveConfigurations() throws ConfigException {
		
		XMLOutputFactory output = XMLOutputFactory.newInstance();
		XMLStreamWriter writer = null;
		
		try {
			writer = output.createXMLStreamWriter(new FileOutputStream(this.fullFilePath), "UTF-8");
			
			writer.writeStartElement("configuration-list");
			
			for(Map.Entry<String, Configuration> configuration : configurationsSet.entrySet()) {
				String configurationName = configuration.getKey();
				Configuration configurationObject = configuration.getValue();
				
				writer.writeStartElement("configuration");
				writer.writeAttribute("name", configurationName);
				
				for(String componentID : configurationObject.getComponentList()) {
					ConfigurationComponent component = configurationObject.getComponent(componentID);
					
					writer.writeStartElement("configuration-component");
					writer.writeAttribute("id", componentID);
					
					writer.writeStartElement("name");
					writer.writeCharacters(component.getName());
					writer.writeEndElement();
					
					writer.writeStartElement("value");
					writer.writeCharacters(component.getValue());
					writer.writeEndElement();
					
					writer.writeEndElement();
				}
				
				writer.writeEndElement();
			}
			
			writer.writeEndElement();
			writer.writeEndDocument();
			
			writer.flush();
			
		} catch (Exception e) {
			throw new ConfigException(e.getMessage());
		} finally {
			if(writer != null) {
				try {
					writer.close();
				} catch (XMLStreamException e) {
					throw new ConfigException(e.getMessage(), e);
				}
			}
		}
	}
	
	public static String getComponentValue(String configurationName, String componentID) {
		
		Configurations configurations = Configurations.getConfigurations();
		
		if(configurations == null) {
			return null;
		}
		
		Configuration configuration = configurations.getConfiguration(configurationName);
		
		if(configuration == null) {
			return null;
		}
		
		ConfigurationComponent component = configuration.getComponent(componentID);
		
		if(component == null) {
			return null;
		}
		
		return component.getValue();
	}
	
}

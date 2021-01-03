package central.builder.xml;

import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import org.w3c.dom.Document;
import org.w3c.dom.Element;

import central.HolidaysCollection;
import central.HolidaysRuntimeException;

public class HolidayXmlFile extends HolidayXmlBaseObject {

	String dateFormat;
	Set<String> languages;
	HolidayXmlCollection allDeclarations = new HolidayXmlCollection();
	Map<String, HolidayXmlBaseObject> declarations = new HashMap<>();
	HolidayXmlDefinitions allDefinitions = new HolidayXmlDefinitions();
	
	/**
	 * 
	 */
	public HolidayXmlFile() {
		setName(null);
	}
	
	@Override
	public void readFromXml(HolidayXmlBaseObject parent, Element element) {
		throw new HolidaysRuntimeException("No implementation here!");
	}

	/**
	 * parse the configuration file
	 * @param doc the document element
	 * @return the Xml data
	 */
	public HolidayXmlFile parse(Document doc) {
		Element root = doc.getDocumentElement();
		readConfiguration((Element)root.getElementsByTagName(HolidayXmlConstants.ELE_CONFIGURATION).item(0));
		allDeclarations.readFromXml(this, (Element)root.getElementsByTagName(HolidayXmlConstants.ELE_DECLARATIONS).item(0));
		allDeclarations.getAllDeclarations(declarations);
		allDefinitions.readFromXml(this, (Element)root.getElementsByTagName(HolidayXmlConstants.ELE_DEFINITIONS).item(0));
		allDefinitions.checkDeclarations(declarations.keySet());
		return this;
	}
	
	private void readConfiguration(Element eleConf) {
		String lang = ((Element)eleConf.getElementsByTagName(HolidayXmlConstants.ELE_LANGUAGES).item(0)).getTextContent();
		languages = Arrays.asList(lang.split(",")).stream().collect(Collectors.toSet());
		dateFormat = ((Element)eleConf.getElementsByTagName(HolidayXmlConstants.ELE_DATEFORMAT).item(0)).getTextContent();
	}

	/**
	 * 
	 * @param parameters
	 * @return
	 */
	public HolidaysCollection getHolidays(String language, Map<String, String> parameters) {
		Map<String, HolidayXmlDays> calculated = new HashMap<>();
		Map<String, String> usedCollections = new HashMap<>();
		String uses = parameters.get(HolidayXmlConstants.KEY_USE);
		Set<String> use = new HashSet<>();
		use.addAll(Arrays.asList(uses.split(",")));
		
		for (String name : use) {
			if (allDefinitions.contains(name)) {
				HolidayXmlRefSet refset = allDefinitions.get(name);
				usedCollections.put(refset.getName(), refset.getDescription(language));
				refset.callRef(hxr -> {
					if (declarations.containsKey(hxr.getName())) {
						for (HolidayXmlDays xmlDay : declarations.get(hxr.getName()).getDays()) {
							calculated.put(xmlDay.getName(), xmlDay);
						}
					} else {
						throw new HolidaysRuntimeException("Undefined declaration " + hxr.getName());
					}
				});
			} else {
				throw new HolidaysRuntimeException("Undefined definition " + name);
			}
		}
		return new HolidaysCollection(calculated.values()
				.stream()
				.map(hxmld -> hxmld.createCalculator(hxmld.getName(), language))
				.collect(Collectors.toList()), usedCollections);
		
	}

	public List<String> getPossibleCollectionKeys(Boolean standalone) {
		return allDefinitions.getPossibleCollectionKeys(standalone);
	}
	
	public DateTimeFormatter getDateTimeFormatter() {
		return DateTimeFormatter.ofPattern(dateFormat);
	}
	
	public Set<String> getLanguages() {
		return languages;
	}
	
}

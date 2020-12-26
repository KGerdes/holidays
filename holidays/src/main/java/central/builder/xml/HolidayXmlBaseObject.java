package central.builder.xml;

import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import central.HolidaysRuntimeException;

public class HolidayXmlBaseObject {
	
	private HolidayXmlBaseObject parent;	
	private String name;
	private Map<String, String> descriptions = new HashMap<>();

	/**
	 * 
	 * @return
	 */
	public String getName() {
		return name;
	}

	/**
	 * 
	 * @param name
	 */
	protected void setName(String name) {
		this.name = name;
	}
	
	public String getDescription(String lang) {
		return descriptions.get(lang);
	}
	
	public Map<String, String> getDescriptions() {
		return descriptions;
	}
		
	/**
	 * 
	 * @return
	 */
	public String getLongName() {
		HolidayXmlBaseObject bo = this;
		StringBuilder sb = new StringBuilder();
		while (bo != null) {
			if (bo.getName() != null && bo.getParent().getName() != null) {
				if (sb.length() > 0) {
					sb.insert(0, ".");
				}
				sb.insert(0,bo.getName());
			}
			bo = bo.getParent();
		}
		return sb.toString();
	}
	
	/**
	 * 
	 * @return
	 */
	public HolidayXmlBaseObject getParent() {
		return parent;
	}

	/**
	 * 
	 * @param parent
	 * @param element
	 */
	void readFromXml(HolidayXmlBaseObject parent, Element element) {
		this.parent = parent;
		if (parent != null) {
			setName(element.getAttribute(HolidayXmlConstants.ATTR_NAME));
		}
		if (hasDescriptions()) {
			Set<String> languages = getLanguages();
			List<Element> descs = getChildsByTagName(element.getChildNodes(), HolidayXmlConstants.ELE_DESCRIPTION);
			for (Element desc : descs) {
				String key = desc.getAttribute(HolidayXmlConstants.ATTR_LANG);
				String d = desc.getTextContent();
				if (key != null && d != null) {
					if (!descriptions.containsKey(key)) {
						descriptions.put(key, d);
					} else {
						throw new HolidaysRuntimeException("Language " + key + " not configured in element " + getLongName());
					}
				} else {
					throw new HolidaysRuntimeException("Can't read description in element " + getLongName() + ". Key and/or Description are empty");
				}
			}
			if (languages.size() != descriptions.size()) {
				throw new HolidaysRuntimeException("Missing expected languages in element " + getLongName());
			}
		}
	}
	
	protected List<Element> getChildsByTagName(NodeList nl, String tagname) {
		List<Element> result = new ArrayList<>();
		for (int i=0;i<nl.getLength();i++) {
			Node n = nl.item(i);
			if (n.getNodeType() == Node.ELEMENT_NODE) {
				if (tagname.equals(((Element)n).getTagName())) {
					result.add((Element)n);
				}
			}
		}
		return result;
	}

	protected List<HolidayXmlBaseObject> getChilds() {
		throw new HolidaysRuntimeException("not yet implemented");
	}

	public List<HolidayXmlDays> getDays() {
		throw new HolidaysRuntimeException("no implementation");
	}
	
	public DateTimeFormatter getDateTimeFormatter() {
		return parent.getDateTimeFormatter();
	}
	
	public Set<String> getLanguages() {
		return parent.getLanguages();
	}
	
	protected boolean hasDescriptions() {
		return false;
	}
	
}

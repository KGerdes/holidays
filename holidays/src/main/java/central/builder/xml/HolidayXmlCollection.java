package central.builder.xml;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

import central.HolidaysRuntimeException;

public class HolidayXmlCollection extends HolidayXmlBaseObject {

	private Map<String, HolidayXmlBaseObject> xmlObjects = new HashMap<>();
	
	@Override
	public void readFromXml(HolidayXmlBaseObject parent, Element defblock) {
		super.readFromXml(parent, defblock);
		readDeclarations(defblock);
	}
	
	/**
	 * read all declarations of an element
	 * @param declarations the parent element
	 */
	private void readDeclarations(Element declarations) {
		NodeList nlist = declarations.getChildNodes(); 
		for (Element defday : getChildsByTagName(nlist, HolidayXmlConstants.ELE_DEFINITION_DAYS)) {
			HolidayXmlDays days = new HolidayXmlDays();
			days.readFromXml(this, defday);
			if (xmlObjects.containsKey(days.getName())) {
				throw new HolidaysRuntimeException("Name " + days.getName() + " already defined");
			}
			xmlObjects.put(days.getName(), days);
		}
		
		for (Element defblock : getChildsByTagName(nlist, HolidayXmlConstants.ELE_DEFINITION_BLOCK)) {
			HolidayXmlCollection collection = new HolidayXmlCollection();
			collection.readFromXml(this, defblock);
			if (xmlObjects.containsKey(collection.getName())) {
				throw new HolidaysRuntimeException("Name " + collection.getName() + " already defined");
			}
			xmlObjects.put(collection.getName(), collection);
		}
	}

	public void getAllDeclarations(Map<String, HolidayXmlBaseObject> declarations) {
		List<HolidayXmlBaseObject> tmp = new ArrayList<>();
		for (HolidayXmlBaseObject hxbo : xmlObjects.values()) {
			tmp.addAll(hxbo.getChilds());
		}
		for (HolidayXmlBaseObject o : tmp) {
			if (declarations.containsKey(o.getName())) {
				throw new HolidaysRuntimeException("Definition " + o.getName() + " already defined!");
			}
			declarations.put(o.getName(), o);
		}
	}
	
	@Override
	protected List<HolidayXmlBaseObject> getChilds() {
		List<HolidayXmlBaseObject> tmp = new ArrayList<>();
		tmp.add(this);
		for (HolidayXmlBaseObject hxbo : xmlObjects.values()) {
			tmp.addAll(hxbo.getChilds());
		}
		return tmp;
	}

	public boolean contains(String name) {
		return get(name) != null;
	}
	
	public HolidayXmlBaseObject get(String name) {
		return xmlObjects.get(name);
	}
	
	@Override
	public List<HolidayXmlDays> getDays() {
		List<HolidayXmlDays> result = new ArrayList<>();
		for (HolidayXmlBaseObject hxbo : xmlObjects.values()) {
			result.addAll(hxbo.getDays());
		}
		return result;
	}

	

}

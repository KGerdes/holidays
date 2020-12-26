package central.builder.xml;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

import central.HolidaysRuntimeException;
import central.calc.CalcHolidays;
import central.calc.ICalcHolidays;

public class HolidayXmlDays extends HolidayXmlBaseObject {

	private static Map<String, Class<ICalcHolidays>> loadedClasses = new ConcurrentHashMap<>();
	private String type;
	Map<String, String> params = new HashMap<>();
	List<HolidayXmlRange> ranges = new ArrayList<>();
	
	@Override
	public void readFromXml(HolidayXmlBaseObject parent, Element defday) {
		super.readFromXml(parent, defday);
		type = defday.getElementsByTagName(HolidayXmlConstants.ELE_TYPE).item(0).getTextContent();
		NodeList nl = defday.getElementsByTagName("Param");
		for (int i=0;i<nl.getLength();i++) {
			Element ele = (Element)nl.item(i);
			String name = ele.getAttribute(HolidayXmlConstants.ATTR_NAME);
			if (params.containsKey(name)) {
				throw new HolidaysRuntimeException("Attribute " + name + " already defined");
			}
			params.put(name, ele.getTextContent());
		}
		nl = defday.getElementsByTagName("Range");
		for (int i=0;i<nl.getLength();i++) {
			HolidayXmlRange hxr = new HolidayXmlRange();
			hxr.readFromXml(this, (Element)nl.item(i));
			ranges.add(hxr);
		}
	}
	
	@Override
	protected List<HolidayXmlBaseObject> getChilds() {
		return Arrays.asList(this);
	}
	
	@Override
	public List<HolidayXmlDays> getDays() {
		return Arrays.asList(this);
	}
	
	@SuppressWarnings("unchecked")
	public ICalcHolidays createCalculator(String name, String language) {
		Class<ICalcHolidays> cl;
		if (loadedClasses.containsKey(type)) {
			cl = loadedClasses.get(type);
		} else {
			try {
				cl = (Class<ICalcHolidays>) getClass().getClassLoader().loadClass(type);
				loadedClasses.put(type, cl);
			} catch (ClassNotFoundException e) {
				throw new HolidaysRuntimeException("Cannot load class " + type, e);
			}
		}
		try {
			ICalcHolidays calcHolidays = cl.getConstructor(String.class).newInstance(getName());
			if (calcHolidays instanceof CalcHolidays) {
				((CalcHolidays)calcHolidays).initialize(language, getParams(params));
			}
			
			for (HolidayXmlRange hxr : ranges) {
				((CalcHolidays)calcHolidays).addRange(hxr.getRange());
			}
			return calcHolidays;
		} catch (Exception e) {
			throw new HolidaysRuntimeException("Cannot create instance of class " + cl.getSimpleName(), e);
		} 
	}

	private Map<String, String> getParams(Map<String, String> p) {
		Map<String, String> res = new HashMap<>();
		for (String key : p.keySet()) {
			res.put(key, p.get(key));
		}
		p = getDescriptions();
		if (p != null) {
			for (String key : p.keySet()) {
				res.put(key, p.get(key));
			}
		}
		return res;
	}
	
	@Override
	protected boolean hasDescriptions() {
		return true;
	}
	
	

}

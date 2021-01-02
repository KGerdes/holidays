package central.builder.xml;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

import central.HolidaysRuntimeException;

public class HolidayXmlDefinitions extends HolidayXmlBaseObject {

	
	
	private Map<String, HolidayXmlRefSet> allDefinitions = new HashMap<>();
	
	@Override
	public void readFromXml(HolidayXmlBaseObject parent, Element definitions) {
		super.readFromXml(parent , definitions);
		NodeList nlist = definitions.getChildNodes(); 
		
		for (Element defcoll : getChildsByTagName(nlist, HolidayXmlConstants.ELE_COLLECTION)) {
			HolidayXmlRefSet hxrs = new HolidayXmlRefSet();
			hxrs.readFromXml(this, defcoll, allDefinitions.values());
			if (allDefinitions.containsKey(hxrs.getName())) {
				throw new HolidaysRuntimeException("Definition " + hxrs.getName() + " already defined");
			}
			allDefinitions.put(hxrs.getName(), hxrs);
		}
		
	}

	public boolean contains(String name) {
		return get(name) != null;
	}
	
	public HolidayXmlRefSet get(String name) {
		return allDefinitions.get(name);
	}

	public void checkDeclarations(Set<String> keySet) {
		allDefinitions.values().stream().forEach(rs -> {
			rs.getKeySet().stream().forEach(key -> {
				if (!keySet.contains(key)) {
					throw new HolidaysRuntimeException("Undefined Reference " + key);
				}
			});
		});
		
	}

	public List<String> getPossibleCollectionKeys() {
		return allDefinitions.keySet().stream().sorted().collect(Collectors.toList());
	}
}

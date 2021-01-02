package central.builder.xml;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.function.Consumer;

import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

import central.HolidaysRuntimeException;


/**
 * 
 * @author Karsten
 *
 */
public class HolidayXmlRefSet extends HolidayXmlBaseObject {
	
	private boolean standalone = true;
	private Map<String, HolidayXmlRef> refmap = new HashMap<>();

	/**
	 * 
	 */
	@Override
	public void readFromXml(HolidayXmlBaseObject parent, Element element) {
		super.readFromXml(parent , element);
		String tmp = element.getAttribute("standalone").toLowerCase();
		if ("false".equals(tmp)) {
			standalone = false;
		}
		NodeList nlist = element.getChildNodes(); 
		for (Element ref : getChildsByTagName(nlist, HolidayXmlConstants.ELE_REF)) {
			HolidayXmlRef hxr = new HolidayXmlRef();
			hxr.readFromXml(this, ref);
			refmap.put(hxr.getName(), hxr);
		}
		
	}
	
	public void readFromXml(HolidayXmlBaseObject parent, Element element, Collection<HolidayXmlRefSet> refsets) {
		readFromXml(parent, element);
		NodeList nlist = element.getChildNodes(); 
		for (Element ref : getChildsByTagName(nlist, HolidayXmlConstants.ELE_REFCOLLECTION)) {
			HolidayXmlRef hxr = new HolidayXmlRef();
			hxr.readFromXml(this, ref);
			boolean found = false;
			for (HolidayXmlRefSet refset : refsets) {
				if (refset.getName().equals(hxr.getName())) {
					found = true;
					for (HolidayXmlRef r : refset.getReferences()) {
						refmap.put(r.getName(), r);
					}
				}
			}
			if (!found) {
				throw new HolidaysRuntimeException("Undefined collection reference " + hxr.getName());
			}
		}
	}
	
	public Collection<HolidayXmlRef> getReferences() {
		return refmap.values();
	}
	
	/**
	 * 
	 * @param consume
	 */
	public void callRef(Consumer<HolidayXmlRef> consume) {
		refmap.values().stream().forEach(consume::accept);
	}
	
	public Set<String> getKeySet() {
		return refmap.keySet();
	}
	
	@Override
	protected boolean hasDescriptions() {
		return true;
	}

	public boolean isStandalone() {
		return standalone;
	}
	
	
}

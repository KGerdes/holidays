package central.builder.xml;

import org.w3c.dom.Element;

import central.calc.Range;

public class HolidayXmlRange extends HolidayXmlBaseObject {

	private Range range;
	
	@Override
	public void readFromXml(HolidayXmlBaseObject parent, Element defblock) {
		super.readFromXml(parent, defblock);
		range = Range.createRange(defblock.getAttribute("start"), defblock.getAttribute("end"), defblock.getAttribute("scope"), getDateTimeFormatter());
	}
	
	public Range getRange() {
		return range;
	}
}

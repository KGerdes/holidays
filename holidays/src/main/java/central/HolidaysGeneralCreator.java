package central;

import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import central.builder.HolidaysCollectionBuilder;

public class HolidaysGeneralCreator {
	
	private static Map<String, HolidaysCollectionBuilder> creators = new ConcurrentHashMap<>();
	
	private HolidaysGeneralCreator() {
		
	}
	
	private static HolidaysCollectionBuilder getBuilder(Locale locale) {
		HolidaysCollectionBuilder hcb = creators.get(locale.getCountry());
		if (hcb == null) {
			hcb = new HolidaysCollectionBuilder(locale);
			creators.put(locale.getCountry(), hcb);
		}
		return hcb;
	}
	
	public static HolidaysCollection createHolidays(Locale locale, String params) {
		HolidaysCollectionBuilder hcb = getBuilder(locale);
		return hcb.createCollection(params);
	}
	
	public static List<String> getPossibleCollectionKeys(Locale locale) {
		return getBuilder(locale).getPossibleCollectionKeys();
	}




	
}

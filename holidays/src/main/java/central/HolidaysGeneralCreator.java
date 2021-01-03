package central;

import java.io.InputStream;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import central.builder.HolidaysCollectionBuilder;

/**
 * 
 * @author Karsten Gerdes
 *
 */
public class HolidaysGeneralCreator {
	
	private static Map<String, HolidaysCollectionBuilder> creators = new ConcurrentHashMap<>();
	
	/**
	 * 
	 */
	private HolidaysGeneralCreator() {
		
	}
	
	/**
	 * get the HolidaysCollectionBuilder referenced by locale
	 * @param locale the key
	 * @return existing or created HolidaysCollectionBuilder
	 */
	private static HolidaysCollectionBuilder getBuilder(Locale locale) {
		HolidaysCollectionBuilder hcb = creators.get(locale.getCountry());
		if (hcb == null) {
			hcb = new HolidaysCollectionBuilder(locale);
			creators.put(locale.getCountry(), hcb);
		}
		return hcb;
	}
	
	/**
	 * if you do not want to use the prebuild xml holidays definitions you can use your own one defined by strm
	 * @param locale the locale to create builder 
	 * @param strm the xml input strm
	 */
	public static void createBuilder(Locale locale, InputStream strm) {
		if (creators.containsKey(locale.getCountry())) {
			throw new HolidaysRuntimeException("Builder " + locale.getCountry() + " already exists");
		}
		creators.put(locale.getCountry(), new HolidaysCollectionBuilder(locale, strm));
	}
	
	/**
	 * creates a holiday collection for a given locale and uses params
	 * 
	 * @param locale key to find the correct HolidaysBuilder
	 * @param params for the initialization of specific holidays
	 * @return the HolidaysCollection
	 */
	public static HolidaysCollection createHolidays(Locale locale, String params) {
		HolidaysCollectionBuilder hcb = getBuilder(locale);
		return hcb.createCollection(params);
	}
	
	/**
	 * creates a HolidaysCollectionBuilder
	 * 
	 * @param locale the locale to create or get the builder
	 * @return the HolidaysCollectionBuilder
	 */
	public static HolidaysCollectionBuilder createBuilder(Locale locale) {
		return getBuilder(locale);
	}
	
	/**
	 * get all possible Collection of holidays in the builder referenced by locale
	 * 
	 * @param locale the key to find the builder
	 * @return a list of possible holiday collection names
	 */
	public static List<String> getPossibleCollectionKeys(Locale locale) {
		return getBuilder(locale).getPossibleCollectionKeys(null);
	}
	
	/**
	 * get all possible collection of holidays in the builder referenced by locale and the standalone value
	 * 
	 * @param locale the key to find the builder
	 * @param standalone collection type standalone true/false
	 * @return a list of possible holiday collection names
	 */
	public static List<String> getPossibleCollectionKeys(Locale locale, boolean standalone) {
		return getBuilder(locale).getPossibleCollectionKeys(standalone);
	}




	
}

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
public class HolidaysOfAllCountriesBuilder {
	
	
	/**
	 * 
	 */
	private HolidaysOfAllCountriesBuilder() {
		
	}
		
	/**
	 * if you do not want to use the prebuild xml holidays definitions you can use your own one defined by strm
	 * @param locale the locale to create builder 
	 * @param strm the xml input strm
	 */
	public static HolidaysCollectionBuilder createBuilder(Locale locale, InputStream strm) {
		return new HolidaysCollectionBuilder(locale, strm);
	}
	
	/**
	 * creates a holiday collection for a given locale and uses params
	 * 
	 * @param locale key to find the correct HolidaysBuilder
	 * @param params for the initialization of specific holidays
	 * @return the HolidaysCollection
	 */
	public static HolidaysCollection createHolidays(Locale locale, String params) {
		return createHolidays(locale, params, null);
	}
	
	/**
	 * creates a holiday collection for a given locale and uses params
	 * 
	 * @param locale key to find the correct HolidaysBuilder
	 * @param params for the initialization of specific holidays
	 * @param store if store is not null, the create collection builder is stored
	 * @return the HolidaysCollection
	 */
	public static HolidaysCollection createHolidays(Locale locale, String params, ObjectContainer<HolidaysCollectionBuilder> store) {
		HolidaysCollectionBuilder hcb = createBuilder(locale);
		if (store != null) {
			store.setTheObject(hcb);
		}
		return hcb.createCollection(params);
	}
	
	/**
	 * creates a HolidaysCollectionBuilder
	 * 
	 * @param locale the locale to create or get the builder
	 * @return the HolidaysCollectionBuilder
	 */
	public static HolidaysCollectionBuilder createBuilder(Locale locale) {
		return new HolidaysCollectionBuilder(locale);
	}
	
	/**
	 * get all possible Collection of holidays in the builder referenced by locale
	 * 
	 * @param locale the key to find the builder
	 * @return a list of possible holiday collection names
	 */
	public static List<String> getPossibleCollectionKeys(Locale locale) {
		return getPossibleCollectionKeys(locale, null);
	}
	
	/**
	 * get all possible collection of holidays in the builder referenced by locale and the standalone value
	 * 
	 * @param locale the key to find the builder
	 * @param standalone collection type standalone true/false
	 * @return a list of possible holiday collection names
	 */
	public static List<String> getPossibleCollectionKeys(Locale locale, Boolean standalone) {
		return getPossibleCollectionKeys(locale, standalone, null);
	}
	
	/**
	 * get all possible collection of holidays in the builder referenced by locale and the standalone value
	 * 
	 * @param locale the key to find the builder
	 * @param standalone collection type standalone true/false
	 * @param store if store is not null, the create collection builder is stored here for reuse
	 * @return a list of possible holiday collection names
	 */
	public static List<String> getPossibleCollectionKeys(Locale locale, Boolean standalone, ObjectContainer<HolidaysCollectionBuilder> store) {
		HolidaysCollectionBuilder hcb = createBuilder(locale);
		if (store != null) {
			store.setTheObject(hcb);
		}
		return hcb.getPossibleCollectionKeys(standalone);
	}
	
}

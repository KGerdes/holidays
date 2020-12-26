package central.builder;

import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Properties;

import javax.xml.XMLConstants;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.stream.StreamSource;
import javax.xml.validation.Schema;
import javax.xml.validation.SchemaFactory;
import javax.xml.validation.Validator;

import central.HolidaysCollection;
import central.HolidaysGeneralCreator;
import central.HolidaysRuntimeException;
import central.builder.xml.HolidayXmlFile;

public class HolidaysCollectionBuilder {

	private static final String KEY_LANG = "lang";
	
	private static final String PARM_SPLIT = ";";
	private static final String KEYVALUE_SPLIT = "=";
	private static final String KEY_LANG_EQUALSIGN = KEY_LANG + KEYVALUE_SPLIT;
	
	private static Map<String, String> classNames = new HashMap<>();
	
	private static Properties allCountryDefinitions;
	 
	private Locale locale;
	private String language;
	private HolidayXmlFile holidayXml;
	
	
	/**
	 * 
	 * @param locale
	 */
	public HolidaysCollectionBuilder(Locale locale) {
		this.locale = locale;
		holidayXml = readXml(locale);
	}
	
	/**
	 * 
	 * @return
	 */
	public String getLanguage() {
		return language;
	}
	
	/**
	 * split the parameter string and create the holiday collection
	 * 
	 * @param params the parameter string
	 * @return the holiday collection
	 */
	public HolidaysCollection createCollection(String params) {
		Map<String, String> pMap = new HashMap<>();
		String[] p = params.split("\\" + PARM_SPLIT);
		for (String pval : p) {
			String[] kv = pval.split(KEYVALUE_SPLIT);
			pMap.put(kv[0], kv[1]);
		}
		language = readLanguage(pMap, locale.getLanguage());
		return create(pMap);
	}
	
	/**
	 * 
	 * @param pMap
	 * @param defaultLanguage
	 * @return
	 */
	private String readLanguage(Map<String, String> pMap, String defaultLanguage) {
		return pMap.getOrDefault(KEY_LANG, locale.getLanguage());
	}
	
	/**
	 * 
	 * @param locale
	 * @return
	 */
	private HolidayXmlFile readXml(Locale locale) {
		String xmlFile = allCountryDefinitions.getProperty(locale.getCountry());
		
		InputStream xmlstrm = HolidaysGeneralCreator.class.getClassLoader().getResourceAsStream(xmlFile);
		InputStream xmlschema = HolidaysGeneralCreator.class.getClassLoader().getResourceAsStream("holidays.xsd");
		try {
			
			DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
			dbFactory.setNamespaceAware(true);
			SchemaFactory schemaFactory = SchemaFactory
				    .newInstance(XMLConstants.W3C_XML_SCHEMA_NS_URI);
			try {
			  Schema schema = schemaFactory.newSchema(new StreamSource(xmlschema));
			  dbFactory.setSchema(schema);
			  Validator validator = schema.newValidator();
			  validator.validate(new StreamSource(xmlstrm));
			} catch (Exception e) {
				throw new HolidaysRuntimeException(e.getMessage(), e);
			}
			
			DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
			xmlstrm = HolidaysGeneralCreator.class.getClassLoader().getResourceAsStream(xmlFile);
			HolidayXmlFile hxf = new HolidayXmlFile();
	    	return hxf.parse(dBuilder.parse(xmlstrm));
		} catch (Exception e) {
			throw new HolidaysRuntimeException(e.getMessage(), e);
		}
	}

	/**
	 * 
	 * @param country
	 * @return
	 */
	public static String getClassName(String country) {
		return classNames.get(country);
	}

	/**
	 * 
	 * @param params
	 * @param paramToAdd
	 * @return
	 */
	public static String addParam(String params, String paramToAdd) {
		StringBuilder sb = new StringBuilder(params);
		if (paramToAdd != null) {
			if (sb.length() > 0) {
				sb.append(";");
			}
			sb.append(KEY_LANG_EQUALSIGN).append(paramToAdd);
		}
		return sb.toString();
	}
	
	/**
	 * 
	 * @param parameters
	 * @return
	 */
	protected HolidaysCollection create(Map<String, String> parameters) {
		return holidayXml.getHolidays(language, parameters);
	}

	/*
	 * initialize the available builder classes
	 */
	static {
		allCountryDefinitions = new Properties();
		try {
			allCountryDefinitions.load(HolidaysCollectionBuilder.class.getClassLoader().getResourceAsStream("builderclasses.properties"));
			
		} catch (IOException e) {
			throw new HolidaysRuntimeException(e.getMessage(), e);
		}
	}

	public List<String> getPossibleCollectionKeys() {
		return holidayXml.getPossibleCollectionKeys();
	}
	 
	 
}

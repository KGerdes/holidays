package holidays;

import java.time.format.DateTimeFormatter;
import java.util.Locale;
import org.junit.Test;

import central.HolidaysCollection;
import central.HolidaysGeneralCreator;



public class DECollectionTest {
	
	

	private void logit(String str) {
		System.out.println(str);
	}

	@Test
	public void testHolidaysOfAYear() {
		DateTimeFormatter dtf = DateTimeFormatter.ofPattern("dd.MM.yyyy");
		HolidaysCollection hc = HolidaysGeneralCreator.createHolidays(Locale.GERMANY,"lang=de;use=BE");
		for (int i=2019;i<=2021;i++) {
			logit("\n* Feiertage " + i + " - " + hc.getAllUsedDescriptions() + " * * * * * * * ");
			hc.getHolidaysOfAYear(i).stream().forEach(entry -> {
				logit(String.format("%s : %s", entry.getKey().format(dtf), entry.getValue()));
			});
		}
	}
	
	@Test
	public void testGetKeys() {
		doit(Locale.GERMANY, 2020, 2020);
	}
	
	@Test
	public void testUK() {
		doit(Locale.UK, 2016, 2020);
	}
	
	private void doit(Locale locale, int fromYear, int toYear) {
		DateTimeFormatter dtf = DateTimeFormatter.ofPattern("EE dd.MM.yyyy");
		HolidaysGeneralCreator.getPossibleCollectionKeys(locale)
		.stream()
		.sorted()
		.filter(key -> !key.equals("Weekend"))
		.forEach(key -> {
			
			HolidaysCollection hc = HolidaysGeneralCreator.createHolidays(locale,"use=" + key);
			for (int year = fromYear;year <= toYear;year++) {
				logit("\n* Feiertage " + year + " - " + hc.getAllUsedDescriptions() + " * * * * * * * ");
				hc.getHolidaysOfAYear(year).stream().forEach(entry -> {
					logit(String.format("%s : %s", entry.getKey().format(dtf), entry.getValue()));
				});
			}
		});	
	}
}

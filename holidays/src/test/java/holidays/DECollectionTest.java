package holidays;

import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.InputStreamReader;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Locale;
import java.util.Map.Entry;
import java.util.stream.Collectors;

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
	
	private void doit(HolidaysCollection hc, int year) {
		DateTimeFormatter dtf = DateTimeFormatter.ofPattern("EE dd.MM.yyyy");
		logit("\n* Feiertage " + year + " - " + hc.getAllUsedDescriptions() + " * * * * * * * ");
		hc.getHolidaysOfAYear(year).stream().forEach(entry -> {
				logit(String.format("%s : %s", entry.getKey().format(dtf), entry.getValue()));
			});
			
		
	}
	
	private List<String> getPossibleUses(Locale locale) {
		return HolidaysGeneralCreator.getPossibleCollectionKeys(locale)
				.stream()
				.sorted()
				.collect(Collectors.toList());
	}
	
	@Test
	public void testInput() {
		Locale locale = Locale.GERMANY;
		List<String> possibleUses = getPossibleUses(locale);
		String use = possibleUses.get(0);
		HolidaysCollection hc = HolidaysGeneralCreator.createHolidays(locale,"use=" + use);
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		try {
			String res = "";
			do {
				System.out.print("> ");
				res = br.readLine();
				String[] keys = res.split(" ");
				try {
					switch (keys[0].toLowerCase()) {
					case "info":
						outln(locale.toString());
						possibleUses.stream().forEach(pu -> { System.out.println(pu); });
						outln("selected -> " + use);
						break;
					case "country":
						locale = new Locale(locale.getLanguage(), keys[1]);
						possibleUses = getPossibleUses(locale);
						use = possibleUses.size() > 0 ? possibleUses.get(0) : "";
						hc = getHolidays(locale, use);
						outln(locale.toString());
						break;
					case "lang":
						locale = new Locale(keys[1], locale.getCountry());
						possibleUses = getPossibleUses(locale);
						use = possibleUses.size() > 0 ? possibleUses.get(0) : "";
						hc = getHolidays(locale, use);
						outln(locale.toString());
						break;
					case "use":
						use = keys[1];
						hc = getHolidays(locale, use);
						break;
					case "all":
						locale = new Locale(keys[1], keys[2]);
						possibleUses = getPossibleUses(locale);
						use = keys[3];
						hc = getHolidays(locale, use);
						outln(locale.toString());
						break;
					case "h":
						doit(hc, Integer.parseInt(keys[1]));
						break;
					case "cmd":
						outln("all, info, country, lang, use, h, cmd");
						break;
					}
				} catch (Exception e) {
					System.out.println("#->Exception(" + e.getMessage() + ")");
				}
			} while (!res.equals("exit"));
			outln("Program terminated!");
		} catch (Exception e) {
			
		} finally {
			
		}
		
	}
	
	

	private HolidaysCollection getHolidays(Locale locale, String use) {
		return HolidaysGeneralCreator.createHolidays(locale,"lang=" + locale.getLanguage() + ";use=" + use);
		
	}

	private void outln(String value) {
		System.out.println(value);
		System.out.println("");
		System.out.flush();
	}
}

package central;

import java.time.LocalDate;
import java.util.AbstractMap;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import central.calc.ICalcHolidays;

public class HolidaysCollection {
	private List<ICalcHolidays> holidays = new ArrayList<>();
	private Map<String, String> usedCollections;
	
	public HolidaysCollection() {
		
	}
	
	public HolidaysCollection(List<ICalcHolidays> list, Map<String, String> usedCollections) {
		this.usedCollections = usedCollections;
		holidays.addAll(list);
	}
	
	public Set<String> getUsedKeys() {
		return usedCollections.keySet();
	}
	
	public String getUsedDescription(String key) {
		return usedCollections.get(key);
	}
	
	public String getAllUsedDescriptions() {
		StringBuilder sb = new StringBuilder();
		usedCollections.values().stream().sorted().forEach(udesc -> {
			if (sb.length() > 0) {
				sb.append(", ");
			}
			sb.append(udesc);
		});
		return sb.toString();
	}

	public boolean isHoliday(LocalDate ld) {
		return holidays.stream().anyMatch(h -> h.isHoliday(ld));
	}
	
	public boolean isHoliday(LocalDate ld, StringBuilder sb) {
		return holidays.stream().anyMatch(h -> h.isHoliday(ld, sb));
	}
	
	public List<Entry<LocalDate, String>> getHolidaysOfAYear(int year) {
		List<Entry<LocalDate, String>> result = new ArrayList<>();
		StringBuilder sb = new StringBuilder();
		LocalDate crawler = LocalDate.of(year, 1, 1);
		while (crawler.getYear() == year) {
			if (isHoliday(crawler, sb)) {
				result.add(new AbstractMap.SimpleEntry<>(crawler, sb.toString()));
				sb.setLength(0);
			}
			crawler = crawler.plusDays(1);
		}
		return result;
	}
}

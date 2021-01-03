package central;

import java.time.LocalDate;
import java.util.AbstractMap;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.stream.Collectors;
import java.util.Set;

import central.calc.ICalcHolidays;
import central.calc.SingleYearlyHolidays;

/**
 * a class to ask for holidays of a specific country and parameters (maybe regions)
 * 
 * @author Karsten
 *
 */
public class HolidaysCollection {
	private List<ICalcHolidays> holidays = new ArrayList<>();
	private Map<String, String> usedCollections;
	private Map<String, ICalcHolidays> namesAndHolidays = new HashMap<>();
	
	
	public HolidaysCollection(List<ICalcHolidays> list, Map<String, String> usedCollections) {
		this.usedCollections = usedCollections;
		holidays.addAll(list);
		list.stream().forEach(ic -> {
			if (!namesAndHolidays.containsKey(ic.getName())) {
				namesAndHolidays.put(ic.getName(), ic);
			} else {
				throw new HolidaysRuntimeException("name '" + ic.getName() + "' already exists");
			}
		});
	}
	
	public List<String> getAllNames() {
		return holidays.stream().map(ICalcHolidays::getName).sorted().collect(Collectors.toList());
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
		return getHolidaysOfARange(LocalDate.of(year, 1, 1), LocalDate.of(year, 12, 31));
	}
	
	public List<Entry<LocalDate, String>> getHolidaysOfARange(LocalDate start, LocalDate end) {
		List<Entry<LocalDate, String>> result = new ArrayList<>();
		StringBuilder sb = new StringBuilder();
		LocalDate crawler = start;
		while (!crawler.isAfter(end)) {
			if (isHoliday(crawler, sb)) {
				result.add(new AbstractMap.SimpleEntry<>(crawler, sb.toString()));
				sb.setLength(0);
			}
			crawler = crawler.plusDays(1);
		}
		return result;
	}

	public LocalDate getSingleHolidayOfAYear(String name, int year, StringBuilder sb) {
		LocalDate result = null;
		ICalcHolidays ch = namesAndHolidays.get(name);
		if (ch != null) {
			result = ch.calculateDateOfAYear(year);
			if (result != null) {
				boolean isH = sb != null ? ch.isHoliday(result, sb) : ch.isHoliday(result);
				if (!isH) {
					throw new HolidaysRuntimeException("should not happen!");
				}
			}
		}
		return result;
	}
}

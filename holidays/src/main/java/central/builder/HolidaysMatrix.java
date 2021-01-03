package central.builder;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.AbstractMap;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.stream.Collectors;

public class HolidaysMatrix {
	
	private class DateContainer {
		private LocalDate date;
		private String    name;
		private Set<String> keys = new HashSet<>();
		
		public DateContainer(String key, LocalDate date, String name) {
			keys.add(key);
			this.date = date;
			this.name = name;
		}

		public boolean hasKey(String key) {
			return keys.contains(key);
		}
		
		public void addKey(String key) {
			keys.add(key);
		}
		
	}

	private int year;
	Map<String, String> collections = new HashMap<>();
	Map<String, DateContainer> dates = new HashMap<>();
	
	/**
	 * constructor HolidaysMatrix with year parameter
	 * @param year the year to get the holidays for
	 */
	public HolidaysMatrix(int year) {
		this.year = year;
	}

	/**
	 * 
	 * @param key
	 * @param name
	 * @param res
	 */
	public void addHolidays(String key, String name, List<Entry<LocalDate, String>> res) {
		collections.put(key, name);
		res.stream().forEach(entry -> {
			LocalDate ld = entry.getKey();
			String mk = getDateKey(ld, entry.getValue());
			DateContainer dc = dates.get(mk);
			if (dc == null) {
				dc = new DateContainer(key, entry.getKey(), entry.getValue());
				dates.put(mk, dc);
			} else {
				dc.addKey(key);
			}
			
		});
	}
	
	private String getDateKey(LocalDate ld, String name) {
		return String.format("%-2d%-2d%-2d,%s", ld.getYear(), ld.getMonth().getValue(), ld.getDayOfMonth(), name);
	}
	
	public List<Entry<LocalDate, String>> getHolidays() {
		return dates.values().stream()
		.sorted((dc1, dc2) -> dc1.date.compareTo(dc2.date))
		.map(dc -> new AbstractMap.SimpleEntry<>(dc.date, dc.name))
		.collect(Collectors.toList());
	}
	
	public List<String> getHolidayCollections(LocalDate date, String name) {
		DateContainer dc = dates.get(getDateKey(date, name));
		return dc.keys.stream().sorted().collect(Collectors.toList());
	}
	
	@Override
	public String toString() {
		DateTimeFormatter df = DateTimeFormatter.ofPattern("EE dd.MM.yyyy");
		StringBuilder sb = new StringBuilder();
		List<Entry<LocalDate, String>> entries = getHolidays();
		List<String> sortc = collections.keySet().stream().sorted().collect(Collectors.toList());
		int len = 0;
		for (Entry<LocalDate, String> entry : entries) {
			if (entry.getValue().length() > len) {
				len = entry.getValue().length();
			}
		}
		String sepline = createSeparatorLine(len, sortc);
		String form = String.format("%%%ds", len);
		sb.append(sepline);
		createHeader(sb, form, len, sortc);
		sb.append(sepline);
		for (Entry<LocalDate, String> entry : entries) {
			sb.append("* ")
			.append(String.format(form, entry.getValue()))
			.append(" | ")
			.append(df.format(entry.getKey()))
			.append(" ");
			for (String key : sortc) {
				sb.append("| ");
				char ch = ' ';
				DateContainer dc = getDateContainer(entry);
				if (dc.hasKey(key)) {
					ch = 'X';
				}
				for (int i=0;i<key.length();i++) {
					sb.append(ch);
					ch = ' ';
				}
				sb.append(" ");
			}
			sb.append("*\n");
		}
		sb.append(sepline);
		sb.append("\n");
		sortc.stream().forEach(e -> {
			sb.append(e).append("=").append(collections.get(e)).append("\n");
		});
		return sb.toString();
	}

	private DateContainer getDateContainer(Entry<LocalDate, String> entry) {
		return dates.get(getDateKey(entry.getKey(), entry.getValue()));
	}

	private String createSeparatorLine(int len, List<String> sortc) {
		StringBuilder sb = new StringBuilder();
		sb.append("*-");
		for (int i=0;i<len;i++) {
			sb.append("-");
		}
		sb.append("-+---------------");
		for (String key : sortc) {
			sb.append("+");
			for (int i=0;i<key.length() + 2;i++) {
				sb.append("-");
			}
		}
		sb.append("*\n");
		return sb.toString();
	}

	private void createHeader(StringBuilder sb, String form, int len, List<String> sortc) {
		sb.append("* ")
			.append(String.format(form, String.valueOf(year)))
			.append(" |               ");
		sortc.stream().forEach(nm -> {
			sb.append(String.format("| %s ", nm));
		});
		sb.append("*\n");
	}

}

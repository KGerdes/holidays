package holidays;

import java.time.LocalDate;

public class TestUtilities {

	private static final String[] weekday = {"Mo", "Di", "Mi", "Do", "Fr", "Sa", "So"};
	
	
	public static String formatDate(LocalDate date) {
		return String.format("%s %02d.%02d.%04d", 
				weekday[date.getDayOfWeek().getValue() - 1],
				date.getDayOfMonth(), 
				date.getMonth().getValue(), 
				date.getYear());
	}
	
	public static String formatDateShort(LocalDate date) {
		return String.format("%02d.%02d.%04d", 
				date.getDayOfMonth(), 
				date.getMonth().getValue(), 
				date.getYear());
	}
}

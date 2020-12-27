package central.calc;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.util.Map;

public class IndexedWeekdayInMonthHoliday extends YearlyHolidays {

	private int index;
	private DayOfWeek weekday;
	
	public IndexedWeekdayInMonthHoliday(String name) {
		super(name);
		
	}

	@Override
	public void initialize(Map<String, String> params) {
		super.initialize(params);
		index = Integer.parseInt(getParameter(params, "index"));
		weekday = DayOfWeek.valueOf(getParameter(params, "weekday"));
	}

	@Override
	public boolean verifyHoliday(LocalDate ld) {
		LocalDate start = null;
		switch (index) {
		case 1:
		case 2:
		case 3:
		case 4:
			start = LocalDate.of(ld.getYear(), getMonth(), 1 + (index - 1) * 7);
			int offset = (weekday.getValue() - start.getDayOfWeek().getValue()  + 7) % 7;
			start = start.plusDays(offset);
			break;
		case 5:
			start = LocalDate.of(ld.getYear(), getMonth() + 1,1).plusDays(-1);
			offset = (start.getDayOfWeek().getValue() - weekday.getValue() + 7) % 7;
			start = start.plusDays(-offset);
			break;
		}
		return ld.equals(start);
	}
}

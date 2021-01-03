package central.calc;

import java.time.LocalDate;
import java.util.Map;

import central.HolidaysRuntimeException;

public class AlternativeWeekdayHolidays extends YearlyHolidays {

	private int[] offsets =  { 0, 0, 0, 0, 0, 0, 0 };
	
	public AlternativeWeekdayHolidays(String name) {
		super(name);
	}
	
	@Override
	public void initialize(Map<String, String> params) {
		super.initialize(params);
		String[] offs = getParameter(params, "offsets").split(",");
		if (offs.length != 7) {
			throwIt("7 int values expected");
		}
		for (int i=0;i<7;i++) {
			try {
				offsets[i] = Integer.parseInt(offs[i]);
			} catch (Exception e) {
				throwIt("int value expected");
			}
		}
	}
	
	@Override
	public LocalDate calculateDateOfAYear(int year) {
		LocalDate toCheck = LocalDate.of(year, getMonth(), getDay());
		int offs = offsets[toCheck.getDayOfWeek().getValue() - 1];
		if (offs != 0) {
			toCheck = toCheck.plusDays(offs);
		}
		return toCheck;
	}

}

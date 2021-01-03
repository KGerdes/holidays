package central.calc;

import java.time.LocalDate;
import java.util.Map;

public class YearlyHolidays extends SingleYearlyHolidays {

	private static final String PARAM_DAY = "day";
	private static final String PARAM_MONTH = "month";
	
	private int month;
	private int day;
	
	public YearlyHolidays(String name) {
		super(name);
	}
	
	@Override
	public void initialize(Map<String, String> params) {
		month = Integer.parseInt(getParameter(params, PARAM_MONTH));
		day = Integer.parseInt(getParameter(params, PARAM_DAY));
	}
	
	public int getDay() {
		return day;
	}
	
	public int getMonth() {
		return month;
	}

	@Override
	public LocalDate calculateDateOfAYear(int year) {
		return LocalDate.of(year, month, day);
	}

	

}

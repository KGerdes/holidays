package central.calc;

import java.time.LocalDate;
import java.util.Map;

public class YearlyHolidays extends CalcHolidays {

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

	@Override
	public boolean verifyHoliday(LocalDate ld) {
		return (day == ld.getDayOfMonth()) && (month == ld.getMonthValue());
	}
	
	public int getDay() {
		return day;
	}
	
	public int getMonth() {
		return month;
	}

	

}

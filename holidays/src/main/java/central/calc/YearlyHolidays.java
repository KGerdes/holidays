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
		month = Integer.parseInt(params.get(PARAM_MONTH));
		day = Integer.parseInt(params.get(PARAM_DAY));
	}

	@Override
	public boolean calculateHoliday(LocalDate ld) {
		return (day == ld.getDayOfMonth()) && (month == ld.getMonthValue());
	}

	

}

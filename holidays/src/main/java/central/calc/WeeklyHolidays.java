package central.calc;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.util.Map;

public class WeeklyHolidays extends CalcHolidays {

	
	

	private static final String PARAM_DAY = "day";
	
	private DayOfWeek dayOfWeek;
	
	public WeeklyHolidays(String name) {
		super(name);
	}
	
	@Override
	public void initialize(Map<String, String> params) {
		dayOfWeek = DayOfWeek.valueOf(params.get(PARAM_DAY));
	}

	public DayOfWeek getDayOfWeek() {
		return dayOfWeek;
	}

	@Override
	public boolean calculateHoliday(LocalDate ld) {
		return dayOfWeek.equals(ld.getDayOfWeek());
	}

}

package central.calc;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.util.Map;

public class AdventHolidays extends CalcHolidays {

	private int offset;
	
	public AdventHolidays(String name) {
		super(name);
	}

	@Override
	public boolean verifyHoliday(LocalDate ld) {
		
		return ld.equals(getFourthAdventDate(ld.getYear()));
	}

	private LocalDate getFourthAdventDate(int year) {
		LocalDate start = LocalDate.of(year, 12, 24);
		do {
			start = start.plusDays(-1);
		} while (!start.getDayOfWeek().equals(DayOfWeek.SUNDAY));
		return start.plusDays(offset);
	}

	@Override
	public void initialize(Map<String, String> params) {
		offset = Integer.parseInt(getParameter(params, "offset"));

	}

}

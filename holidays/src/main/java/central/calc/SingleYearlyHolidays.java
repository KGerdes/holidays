package central.calc;

import java.time.LocalDate;
import java.util.Map;

public abstract class SingleYearlyHolidays extends CalcHolidays {

	public SingleYearlyHolidays(String name) {
		super(name);
	}

	@Override
	public boolean verifyHoliday(LocalDate ld) {
		return ld.equals(calculateDateOfAYear(ld.getYear()));
	}

}

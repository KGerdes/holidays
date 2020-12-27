package central.calc;

import java.time.LocalDate;
import java.util.Map;

public class RangeHolidays extends CalcHolidays {

	public RangeHolidays(String name) {
		super(name);
	}

	@Override
	public boolean verifyHoliday(LocalDate ld) {
		/* holiday is defined through its range definitions */
		return true;
	}

	@Override
	public void initialize(Map<String, String> params) {
		/* nothing to do here */
	}

}

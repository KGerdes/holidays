package central.calc;

import java.time.LocalDate;
import java.util.Map;


public interface ICalcHolidays {

	public boolean isHoliday(LocalDate ld);
	public boolean isHoliday(LocalDate ld, StringBuilder sb);
}

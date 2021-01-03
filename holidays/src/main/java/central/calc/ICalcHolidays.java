package central.calc;

import java.time.LocalDate;
import java.util.Map;


public interface ICalcHolidays {

	public String  getName();
	public boolean isHoliday(LocalDate ld);
	public boolean isHoliday(LocalDate ld, StringBuilder sb);
	
	default LocalDate calculateDateOfAYear(int year) {
		return null;
	}
}

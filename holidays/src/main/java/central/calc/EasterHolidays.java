package central.calc;

import java.time.LocalDate;
import java.util.Map;
import com.aldaviva.easter4j.Easter4J;

public class EasterHolidays extends SingleYearlyHolidays {

	
	private static final String PARAM_OFFSET = "offset";
	
	
	private int offset;
	
	public EasterHolidays(String name) {
		super(name);
	}
	
	public static LocalDate getEasterDependentHoliday(int year, int offsetInDays)
    {
		return Easter4J.getEaster(year).plusDays(offsetInDays);
    }

	@Override
	public void initialize(Map<String, String> params) {
		offset = Integer.parseInt(getParameter(params, PARAM_OFFSET));

	}

	@Override
	public LocalDate calculateDateOfAYear(int year) {
		return getEasterDependentHoliday(year, offset);
	}

}

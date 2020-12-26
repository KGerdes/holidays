package central.calc;

import java.time.LocalDate;
import java.util.Map;
import com.aldaviva.easter4j.Easter4J;

public class EasterHolidays extends CalcHolidays {

	
	private static final String PARAM_OFFSET = "offset";
	
	
	private int offset;
	
	public EasterHolidays(String name) {
		super(name);
	}

	@Override
	public boolean calculateHoliday(LocalDate ld) {
		return getEasterDependentHoliday(ld.getYear(), offset).equals(ld);
	}
	
	public static LocalDate getEasterDependentHoliday(int year, int offsetInDays)
    {
		return Easter4J.getEaster(year).plusDays(offsetInDays);
    }

	@Override
	public void initialize(Map<String, String> params) {
		offset = Integer.parseInt(params.get(PARAM_OFFSET));

	}

}

package central.calc;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.util.Map;

/**
 * an advent based holiday. It is calculated by an offset on the fourth advent.
 * 
 * @author Karsten
 *
 */
public class AdventHolidays extends SingleYearlyHolidays {

	/*
	 * an offset of days to the fourth advent
	 */
	private int offset;
	
	/**
	 * Constructor called by the holidays builder with the given name
	 * 
	 * @param name
	 */
	public AdventHolidays(String name) {
		super(name);
	}
	
	/**
	 * a constructor for creating these holidays separately
	 * 
	 * @param name the name of this holiday
	 * @param description the description
	 * @param offset the offset of days
	 */
	public AdventHolidays(String name, String description, int offset) {
		super(name);
		setDescription(description);
		this.offset = offset;
	}

	/**
	 * calculate the date of the fourth advent in a specific year
	 * @param year
	 * @return
	 */
	private LocalDate getFourthAdventDate(int year) {
		LocalDate start = LocalDate.of(year, 12, 24);
		while (!start.getDayOfWeek().equals(DayOfWeek.SUNDAY)) {
			start = start.plusDays(-1);
		}
		return start.plusDays(offset);
	}

	@Override
	public void initialize(Map<String, String> params) {
		offset = Integer.parseInt(getParameter(params, "offset"));

	}

	@Override
	public LocalDate calculateDateOfAYear(int year) {
		return getFourthAdventDate(year);
	}

}

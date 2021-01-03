package central.calc;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import central.HolidaysRuntimeException;


public abstract class CalcHolidays implements ICalcHolidays {

	private String name;
	private String description;
	List<Range> ranges = null;
	
	public CalcHolidays(String name) {
		this.name = name;
	}
	
	
	// @Override
	public void initialize(String language, Map<String, String> params) {
		initialize(params);
		setDescription(params.get(language));
	}
	
	protected void throwIt(String msg) {
		throw new HolidaysRuntimeException(String.format("%s in %s.\"%s\"",msg, getClass().getName(), name));
	}
	
	protected String getParameter(Map<String, String> params, String pname) {
		String pvalue = params.get(pname);
		if (pvalue == null) {
			throwIt(String.format("Missing parameter '%s'",pname));
		}
		return pvalue;
	}
	
	public void addRange(Range r) {
		if (ranges == null) {
			ranges = new ArrayList<>();
		}
		ranges.add(r);
	}
	
	/**
	 * 
	 */
	@Override
	public String getName() {
		return name;
	}
	
	public String getDescription() {
		return description;
	}
	
	public void  setDescription(String desc) {
		description = (desc != null) ? desc : name;
	}

	public boolean isHoliday(LocalDate ld, StringBuilder sb) {
		boolean res = isHoliday(ld);
		if (res) {
			sb.append(getDescription());
		}
		return res;
	}
	
	public boolean isHoliday(LocalDate ld) {
		return (verifyHoliday(ld) && rangeCheck(ld));
	}

	private boolean rangeCheck(LocalDate ld) {
		if (ranges != null) {
			for (Range r : ranges) {
				if (!r.isDateIn(ld)) {
					return false;
				}
			}
		}
		return true;
	}
	
	public abstract boolean verifyHoliday(LocalDate ld);
	public abstract void initialize(Map<String, String> params);
	
	

}

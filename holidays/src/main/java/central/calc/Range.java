package central.calc;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import central.HolidaysRuntimeException;

public class Range {

	
	private enum Scope {
		INNER(),
		OUTER();
		
		
	}
	
	private LocalDate start;
	private LocalDate end;
	private Scope scope;
	
	public Range(LocalDate start, LocalDate end, Scope scope) {
		this.start = start;
		this.end = end;
		this.scope = scope;
	}
	
	public boolean isDateIn(LocalDate ld) {
		if (scope == Scope.INNER) {
			return inner(ld);
		} 
		return outer(ld);
	}
	
	private boolean outer(LocalDate ld) {
		return !inner(ld);
	}

	private boolean inner(LocalDate ld) {
		if (start != null && ld.isBefore(start)) {
			return false;
		}
		if (end != null && ld.isAfter(end)) {
			return false;
		}
		return true;
	}

	public static Range createRange(String startDate, String endDate, String scopeStr, DateTimeFormatter dtf) {
		LocalDate st = null;
		LocalDate en = null;
		if (startDate != null) {
			if (startDate.length() > 0) {
				st = LocalDate.parse(startDate,dtf); 
			}
		}
		if (endDate != null) {
			if (endDate.length() > 0) {
				en = LocalDate.parse(endDate,dtf); 
			}
		}
		Scope sco = Scope.valueOf((scopeStr != null && scopeStr.length() > 0) ? scopeStr.toUpperCase() : Scope.INNER.name());
		if (st == null && en == null) {
			throw new HolidaysRuntimeException("Missing range dates");
		}
		if (st != null && en != null && st.isAfter(en)) {
			throw new HolidaysRuntimeException("start range date is after end range date");
		}
		return new Range(st, en, sco);
	}


}

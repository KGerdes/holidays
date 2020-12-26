package central;

public class HolidaysRuntimeException extends RuntimeException {

	/**
	 * 
	 */
	private static final long serialVersionUID = -952549757027721125L;

	public HolidaysRuntimeException(String incident) {
		super(incident);
	}
	
	public HolidaysRuntimeException(String incident, Throwable t) {
		super(incident, t);
	}
}

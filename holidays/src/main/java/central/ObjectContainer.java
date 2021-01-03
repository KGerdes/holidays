package central;

/**
 * 
 * @author Karsten
 *
 * @param <T> class of the reference object
 */
public class ObjectContainer<T> {
	
	private T theObject;
	
	
	/**
	 * gets the referenced object
	 * @return
	 */
	public T getTheObject() {
		return theObject;
	}

	/**
	 * sets the object to reference
	 * @param theObject the object to reference
	 */
	public void setTheObject(T theObject) {
		this.theObject = theObject;
	}
	
	
}

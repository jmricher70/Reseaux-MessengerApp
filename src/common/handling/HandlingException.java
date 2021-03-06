package common.handling;



/**
 * Handling-related exception.
 * @author etudiant
 * @see Handler
 */
public class HandlingException extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = -43377267113416448L;

	public HandlingException() {
		super();
	}
	
	public HandlingException(String message) {
		super(message);
	}
}

package ee.ut.math.tvt.bartersmart.domain.exception;

/**
 * Thrown when domain controller's verification fails.
 */
public class DuplicateStockItemNameException extends Exception {

	private static final long serialVersionUID = 1L;

	/**
	 * Constructs new <code>VerificationFailedException</code>.
	 */
	public DuplicateStockItemNameException() {
		super();
	}
	
	/**
	 * Constructs new <code>VerificationFailedException</code> with  with the specified detail message.
	 * @param message the detail message.
	 */
	public DuplicateStockItemNameException(final String message) {
		super(message);
	}
}

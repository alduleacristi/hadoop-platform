package ro.unitbv.fmi.tmis.platform.exception;

public class FailedToDeleteException extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = 2555620147472516799L;

	public FailedToDeleteException(String msg, Throwable cause) {
		super(msg, cause);
	}
}

package ro.unitbv.fmi.tmis.platform.exception;

public class FailedToSaveException extends Exception{

	/**
	 * 
	 */
	private static final long serialVersionUID = -3539548895161002754L;

	public FailedToSaveException(String msg, Throwable cause){
		super(msg, cause);
	}
}

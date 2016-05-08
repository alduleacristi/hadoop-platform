package ro.unitbv.fmi.tmis.platform.hive.exceptions;

public class FailedToCreateException extends Exception{

	/**
	 * 
	 */
	private static final long serialVersionUID = 5334474123452613911L;
	
	public FailedToCreateException(String msg){
		super(msg);
	}
	
	public FailedToCreateException(){
		super();
	}
	
	public FailedToCreateException(String msg,Throwable cause){
		super(msg,cause);
	}

}

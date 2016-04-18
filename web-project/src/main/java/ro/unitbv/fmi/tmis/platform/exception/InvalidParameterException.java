package ro.unitbv.fmi.tmis.platform.exception;

import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

public class InvalidParameterException extends WebApplicationException {
	private static final long serialVersionUID = -3650112154224886531L;

	public InvalidParameterException(String message) {
		super(Response.status(Response.Status.BAD_REQUEST).entity(message)
				.type(MediaType.TEXT_PLAIN).build());
	}
}

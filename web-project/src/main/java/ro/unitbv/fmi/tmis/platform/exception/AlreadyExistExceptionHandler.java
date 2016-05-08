package ro.unitbv.fmi.tmis.platform.exception;

import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

@Provider
public class AlreadyExistExceptionHandler implements
		ExceptionMapper<AlreadyExistException> {

	@Override
	public Response toResponse(AlreadyExistException exception) {
		return Response.status(Status.BAD_REQUEST)
				.entity(exception.getMessage()).build();
	}

}

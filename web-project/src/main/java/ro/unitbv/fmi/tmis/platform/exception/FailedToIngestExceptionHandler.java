package ro.unitbv.fmi.tmis.platform.exception;

import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

@Provider
public class FailedToIngestExceptionHandler implements
		ExceptionMapper<FailedToIngestException> {

	@Override
	public Response toResponse(FailedToIngestException exception) {
		return Response.status(Status.INTERNAL_SERVER_ERROR)
				.entity(exception.getMessage()).build();
	}

}

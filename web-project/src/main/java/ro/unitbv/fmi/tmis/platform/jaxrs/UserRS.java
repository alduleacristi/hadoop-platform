package ro.unitbv.fmi.tmis.platform.jaxrs;

import java.util.List;

import javax.inject.Inject;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import ro.unitbv.fmi.tmis.platform.dao.UserDAO;
import ro.unitbv.fmi.tmis.platform.model.User;

@Path("/api")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class UserRS {
	@Inject
	private UserDAO userDAO;

	@GET
	@Path("/user")
	public List<User> getAllUsers() {
		return userDAO.getAllUsers();
	}
}

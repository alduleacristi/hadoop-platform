package ro.unitbv.fmi.tmis.platform.security;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import javax.inject.Inject;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.Consumes;
import javax.ws.rs.FormParam;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import org.apache.commons.codec.binary.Base64;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import ro.unitbv.fmi.tmis.platform.dao.UserDAO;
import ro.unitbv.fmi.tmis.platform.dto.TokenDTO;
import ro.unitbv.fmi.tmis.platform.model.Role;
import ro.unitbv.fmi.tmis.platform.model.User;

@Path("/api/auth")
public class AuthorizationRS {
	@Context
	private HttpServletRequest servletRequest;
	@Inject
	private UserDAO userDAO;

	@POST
	@Path("/authentication")
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	public String authenticate(User user) throws ServletException,
			NoSuchAlgorithmException, JsonProcessingException {
		try {
			System.out.println("User [" + user.getEmail()
					+ "] try to authenticate");
			servletRequest.login(user.getEmail(), user.getPassword());

			User userObj = userDAO.getUserByEmail(user.getEmail());
			Set<String> roles = new HashSet<>();
			for (Role role : userObj.getRoles()) {
				roles.add(role.getRole());
			}

			TokenDTO tokenDTO = new TokenDTO();
			tokenDTO.setClientId(SecurityUtil.encodeUser(user.getEmail()));
			tokenDTO.setRoles(roles);
			tokenDTO.setGeneratedDate(new Date());

			ObjectMapper objMapper = new ObjectMapper();
			String strToken = objMapper.writeValueAsString(tokenDTO);

			// byte[] bytesEncoded = Base64.encodeBase64(strToken.getBytes());

			// return new String(bytesEncoded);
			return strToken;
		} catch (ServletException se) {
			se.printStackTrace();
			throw se;
		}
	}

	@POST
	@Path("/logout")
	public void logut() {
		servletRequest.getSession().invalidate();
	}

	@GET
	@Path("/permission")
	public Response getPermission() throws NoSuchAlgorithmException,
			JsonProcessingException {
		String user = servletRequest.getRemoteUser();
		if (user == null) {
			return Response.status(Response.Status.UNAUTHORIZED)
					.entity("You are not authenticated")
					.type(MediaType.TEXT_PLAIN).build();
		}

		User userObj = userDAO.getUserByEmail(user);
		Set<String> roles = new HashSet<>();
		for (Role role : userObj.getRoles()) {
			roles.add(role.getRole());
		}

		TokenDTO tokenDTO = new TokenDTO();
		tokenDTO.setClientId(SecurityUtil.encodeUser(user));
		tokenDTO.setRoles(roles);
		tokenDTO.setGeneratedDate(new Date());

		ObjectMapper objMapper = new ObjectMapper();
		String strToken = objMapper.writeValueAsString(tokenDTO);

		// byte[] bytesEncoded = Base64.encodeBase64(strToken.getBytes());

		return Response.status(Response.Status.OK).entity(strToken)
				.type(MediaType.TEXT_PLAIN).build();
	}

	@GET
	@Path("/view/message")
	public void message() {
		System.out.println("Service was called...");
	}

	@GET
	@Path("/notAuthenticated")
	public Response notAuthenticated() {
		return Response.status(Response.Status.UNAUTHORIZED)
				.entity("You are not authenticated").type(MediaType.TEXT_PLAIN)
				.build();
	}

	@GET
	@Path("/failedToAuthenticate")
	public Response failedToAuthenticate() {
		return Response.status(Response.Status.UNAUTHORIZED)
				.entity("Failed to authenticate").type(MediaType.TEXT_PLAIN)
				.build();
	}
}

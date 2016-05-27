package ro.unitbv.fmi.tmis.platform.security;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Date;

import javax.inject.Inject;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.FormParam;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import ro.unitbv.fmi.tmis.platform.dao.UserDAO;
import ro.unitbv.fmi.tmis.platform.dto.TokenDTO;

@Path("/api")
public class AuthorizationRS {
	@Context
	private HttpServletRequest servletRequest;
	@Inject
	private UserDAO userDAO;

	private String encodeUser(String username) throws NoSuchAlgorithmException {
		MessageDigest md = MessageDigest.getInstance("SHA-256");
		md.update(username.getBytes());

		byte byteData[] = md.digest();

		// convert the byte to hex format method 1
		StringBuffer sb = new StringBuffer();
		for (int i = 0; i < byteData.length; i++) {
			sb.append(Integer.toString((byteData[i] & 0xff) + 0x100, 16)
					.substring(1));
		}

		System.out.println("Hex format : " + sb.toString());

		// convert the byte to hex format method 2
		StringBuffer hexString = new StringBuffer();
		for (int i = 0; i < byteData.length; i++) {
			String hex = Integer.toHexString(0xff & byteData[i]);
			if (hex.length() == 1)
				hexString.append('0');
			hexString.append(hex);
		}

		return hexString.toString();
	}

	@POST
	@Path("/authentication")
	@Produces(MediaType.APPLICATION_JSON)
	public String authenticate(@FormParam("username") String user,
			@FormParam("password") String password) throws ServletException,
			NoSuchAlgorithmException, JsonProcessingException {

		servletRequest.login(user, password);
		TokenDTO tokenDTO = new TokenDTO();
		tokenDTO.setClientId(encodeUser(user));
		tokenDTO.setRoles(userDAO.getUserByEmail(user).getRoles());
		tokenDTO.setGeneratedDate(new Date());

		ObjectMapper objMapper = new ObjectMapper();
		String strToken = objMapper.writeValueAsString(tokenDTO);

		return strToken;
	}

	@GET
	@Path("/test/token")
	public String getToken() {
		return "token";
	}
}

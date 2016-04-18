package ro.unitbv.fmi.tmis.platform.jaxrs;

import java.util.List;

import javax.inject.Inject;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import ro.unitbv.fmi.tmis.platform.dao.RegionDAO;
import ro.unitbv.fmi.tmis.platform.model.Region;

@Path("/api")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class RegionRS {
	@Inject
	private RegionDAO regionDAO;

	@GET
	@Path("/region")
	public List<Region> getTemperature() {
		return regionDAO.getAllRegions();
	}

	@POST
	@Path("/region")
	@Consumes(MediaType.APPLICATION_JSON)
	public void addRegion(Region region) throws JsonProcessingException {
		System.out.println("Try to save a new region");

		String str = (new ObjectMapper()).writeValueAsString(region);
		System.out.println("Json object -> " + str);
		// regionDAO.saveRegion(region);
		// System.out.println("Region added with success");
	}
}

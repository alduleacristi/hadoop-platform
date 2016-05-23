package ro.unitbv.fmi.tmis.platform.jaxrs;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;

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
	public List<Region> getRegion(@QueryParam("regionId") Long regionId) {
		if (regionId == null) {
			return regionDAO.getAllRegions();
		} else {
			List<Region> regions = new ArrayList<>();
			Region region = regionDAO.getRegionById(regionId);
			if (region != null) {
				regions.add(region);
			}

			return regions;
		}
	}
}

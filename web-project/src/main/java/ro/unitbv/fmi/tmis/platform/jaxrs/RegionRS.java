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
import ro.unitbv.fmi.tmis.platform.exception.InvalidParameterException;
import ro.unitbv.fmi.tmis.platform.model.Region;

@Path("/api")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class RegionRS {
	@Inject
	private RegionDAO regionDAO;

	private boolean validateOffsetAndLimit(Integer offset, Integer limit) {
		if (offset < 0) {
			throw new InvalidParameterException("Offset must be positive");
		}
		if (limit < 0) {
			throw new InvalidParameterException("Limit must be positive");
		}
		return true;
	}

	@GET
	@Path("/region")
	public List<Region> getRegion(@QueryParam("regionId") Long regionId,
			@QueryParam("offset") Integer offset,
			@QueryParam("limit") Integer limit) {
		if (regionId != null) {
			List<Region> regions = new ArrayList<>();
			Region region = regionDAO.getRegionById(regionId);
			if (region != null) {
				regions.add(region);
			}

			return regions;
		} else if (offset != null && limit != null) {
			if (validateOffsetAndLimit(offset, limit)) {
				return regionDAO.getPaginatedResult(offset, limit);
			}

			return null;
		} else {
			return regionDAO.getAllRegions();
		}
	}
}

package ro.unitbv.fmi.tmis.platform.jaxrs;

import java.io.IOException;
import java.util.List;

import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import ro.unitbv.fmi.tmis.platform.dao.LatitudeDAO;
import ro.unitbv.fmi.tmis.platform.model.Latitude;
import ucar.ma2.InvalidRangeException;

@Path("/api")
public class LatitudeRS {
	@Inject
	private LatitudeDAO latitudeDAO;

	@GET
	@Path("/latitude")
	@Produces(MediaType.TEXT_PLAIN)
	public String insertLatitude() throws IOException, InvalidRangeException {
		/*NetCdfInstance cdf = new NetCdfInstance("");
		List<ro.unitbv.fmi.tmis.netcdf.dto.Latitude> latitudes = cdf
				.getLatitudes();
		
		System.out.println("#### Before for -> "+latitudes.size());
		for (ro.unitbv.fmi.tmis.netcdf.dto.Latitude lat : latitudes) {
			System.out.println("### In for");
			Latitude latitude = new Latitude(lat.getId(), lat.getLatitude());
			latitudeDAO.insertLatitude(latitude);
		}*/

		return "Latitudes was inserted with success";
	}
}

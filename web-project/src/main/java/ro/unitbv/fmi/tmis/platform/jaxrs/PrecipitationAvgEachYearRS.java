package ro.unitbv.fmi.tmis.platform.jaxrs;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;
import javax.validation.constraints.NotNull;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;

import ro.unitbv.fmi.tmis.platform.dao.PrecipitationAvgEachYearDAO;
import ro.unitbv.fmi.tmis.platform.dto.PrecipitationAvgEachYearDTO;
import ro.unitbv.fmi.tmis.platform.model.PrecipitationAvgEachYear;

@Path("/api")
public class PrecipitationAvgEachYearRS {
	@Inject
	private PrecipitationAvgEachYearDAO precipitationAvgEachYearDAO;

	@GET
	@Path("/results/precipitations/avgEachYears")
	@Produces(MediaType.APPLICATION_JSON)
	public List<PrecipitationAvgEachYearDTO> getAvgsEachYears(
			@NotNull(message = "Region id must not be null") @QueryParam("regionId") Long regionId) {
		List<PrecipitationAvgEachYear> values = precipitationAvgEachYearDAO
				.getPrecipitationAvgEachYearByRegionId(regionId);

		List<PrecipitationAvgEachYearDTO> dtos = new ArrayList<>();
		for (PrecipitationAvgEachYear p : values) {
			PrecipitationAvgEachYearDTO dto = new PrecipitationAvgEachYearDTO(
					regionId, p.getYear(), p.getMonth(), p.getAvg(), p.getMax());
			dtos.add(dto);
		}

		return dtos;
	}
}

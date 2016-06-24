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

import ro.unitbv.fmi.tmis.platform.dao.TempMinAvgEachYearDAO;
import ro.unitbv.fmi.tmis.platform.dto.TempMinAvgEachYearDTO;
import ro.unitbv.fmi.tmis.platform.model.TempMinAvgEachYear;

@Path("/api")
public class TempMinAvgEachyearsRS {
	@Inject
	private TempMinAvgEachYearDAO tempMinAvgEachYearDAO;

	@GET
	@Path("/results/tempMin/avgEachYears")
	@Produces(MediaType.APPLICATION_JSON)
	public List<TempMinAvgEachYearDTO> getAvgsEachYears(
			@NotNull(message = "Region id must not be null") @QueryParam("regionId") Long regionId) {
		List<TempMinAvgEachYear> values = tempMinAvgEachYearDAO
				.getTempMinAvgEachYearByRegionId(regionId);

		List<TempMinAvgEachYearDTO> dtos = new ArrayList<>();
		for (TempMinAvgEachYear p : values) {
			TempMinAvgEachYearDTO dto = new TempMinAvgEachYearDTO(
					regionId, p.getYear(), p.getMonth(), p.getAvg(), p.getMin());
			dtos.add(dto);
		}

		return dtos;
	}
}

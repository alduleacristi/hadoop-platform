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

import ro.unitbv.fmi.tmis.platform.dao.TempMaxAvgEachYearDAO;
import ro.unitbv.fmi.tmis.platform.dto.TempMaxAvgEachYearDTO;
import ro.unitbv.fmi.tmis.platform.model.TempMaxAvgEachYear;

@Path("/api")
public class TempMaxAvgEachyearsRS {
	@Inject
	private TempMaxAvgEachYearDAO tempMaxAvgEachYearDAO;

	@GET
	@Path("/results/tempMax/avgEachYears")
	@Produces(MediaType.APPLICATION_JSON)
	public List<TempMaxAvgEachYearDTO> getAvgsEachYears(
			@NotNull(message = "Region id must not be null") @QueryParam("regionId") Long regionId) {
		List<TempMaxAvgEachYear> values = tempMaxAvgEachYearDAO
				.getTempMaxAvgEachYearByRegionId(regionId);

		List<TempMaxAvgEachYearDTO> dtos = new ArrayList<>();
		for (TempMaxAvgEachYear p : values) {
			TempMaxAvgEachYearDTO dto = new TempMaxAvgEachYearDTO(
					regionId, p.getYear(), p.getMonth(), p.getAvg(), p.getMax());
			dtos.add(dto);
		}

		return dtos;
	}
}

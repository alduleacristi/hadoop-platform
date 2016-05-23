package ro.unitbv.fmi.tmis.platform.jaxrs;

import java.util.List;

import javax.inject.Inject;
import javax.validation.constraints.NotNull;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;

import ro.unitbv.fmi.tmis.platform.dao.QueryDAO;
import ro.unitbv.fmi.tmis.platform.dao.QueryUsedDAO;
import ro.unitbv.fmi.tmis.platform.model.Query;
import ro.unitbv.fmi.tmis.platform.model.UsedQuery;

@Path("/api")
public class HiveQueryRS {
	@Inject
	private QueryDAO queryDAO;
	@Inject
	private QueryUsedDAO queryUsedDAO;

	@GET
	@Path("/hive/query")
	@Produces(MediaType.APPLICATION_JSON)
	public List<Query> getAllQuerys() {
		return queryDAO.getAllQuerys();
	}

	@GET
	@Path("/hive/usedQuery")
	@Produces(MediaType.APPLICATION_JSON)
	public List<UsedQuery> getAllUsedQueryForRegion(
			@NotNull(message = "Region id must not be null") @QueryParam("regionId") Long regionId) {
		return queryUsedDAO.getUsedQueryForRegion(regionId);
	}
}

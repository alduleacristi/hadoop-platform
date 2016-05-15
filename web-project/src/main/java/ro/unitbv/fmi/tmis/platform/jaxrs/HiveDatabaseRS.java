package ro.unitbv.fmi.tmis.platform.jaxrs;

import java.sql.SQLException;

import javax.inject.Inject;
import javax.validation.constraints.NotNull;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.QueryParam;

import ro.unitbv.fmi.tmis.platform.dao.RegionDAO;
import ro.unitbv.fmi.tmis.platform.hive.dao.DbDAO;
import ro.unitbv.fmi.tmis.platform.hive.dao.PrecipitationDAO;
import ro.unitbv.fmi.tmis.platform.hive.exceptions.FailedToCreateException;
import ro.unitbv.fmi.tmis.platform.model.Region;

@Path("/api")
public class HiveDatabaseRS {
	private final String DB_NAME = "turism";

	@Inject
	private DbDAO dbDAO;
	@Inject
	private PrecipitationDAO precipitationDAO;
	@Inject
	private RegionDAO regionDAO;

	@PUT
	@Path("/db/createDb")
	public void createDatabase(
			@NotNull(message = "Database name must not be null") @QueryParam("dbName") String dbName) {
		System.out.println("Try to create a database with name: [" + dbName
				+ "]");

		try {
			dbDAO.createDb(dbName);
		} catch (FailedToCreateException e) {
			e.printStackTrace();
			// throw new Fai
		}
	}

	@PUT
	@Path("/db/createPrecipitationTable")
	public void createPrecipitationTable(
			@NotNull(message = "Database name must not be null") @QueryParam("dbName") String dbName) {
		System.out
				.println("Try to create a table with name: [precipitation] in database ["
						+ dbName + "]");
		try {
			precipitationDAO.createTable(dbName);
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	@POST
	@Path("/db/loadPrecipitationData")
	public void loadPrecipitationData(
			@NotNull(message = "Region id must not be null") @QueryParam("regionId") Long regionId) {
		System.out.println("Try to load data into hive for region with id ["
				+ regionId + "]");
		try {
			Region region = regionDAO.getRegionById(regionId);

			precipitationDAO.loadDataIntoTable(
					DB_NAME,
					"/user/root/extracted-data/precipitations/"
							+ region.getName(), regionId);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@GET
	@Path("/db/precipitations/month/avg")
	public void getAvgPerMonth(
			@NotNull(message = "Region id must not be null") @QueryParam("regionId") Long regionId,
			@NotNull(message = "Each year option must not be null") @QueryParam("eachYear") Boolean eachYear) {

		Region region = regionDAO.getRegionById(regionId);
		System.out.println("Region name: " + region.getName());
		// TO DO Get start and nd year...
		// try {
		if (eachYear) {
			// precipitationDAO
			// .getAveragePerMonthEachYear(DB_NAME, 2015, 2016);
		} else {
			// precipitationDAO
			// .getAveragePerMonthAllYears(DB_NAME, 2015, 2017);
		}
		// } catch (SQLException e) {
		// // TODO Auto-generated catch block
		// e.printStackTrace();
		// }
	}
}

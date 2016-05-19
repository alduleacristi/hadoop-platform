package ro.unitbv.fmi.tmis.platform.jaxrs;

import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Calendar;

import javax.inject.Inject;
import javax.validation.constraints.NotNull;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.QueryParam;

import ro.unitbv.fmi.tmis.platform.dao.PrecipitationAvgEachYearDAO;
import ro.unitbv.fmi.tmis.platform.dao.RegionDAO;
import ro.unitbv.fmi.tmis.platform.hive.dao.DbDAO;
import ro.unitbv.fmi.tmis.platform.hive.dao.PrecipitationDAO;
import ro.unitbv.fmi.tmis.platform.hive.dto.PrecipitationAvgEachYearDTO;
import ro.unitbv.fmi.tmis.platform.hive.exceptions.FailedToCreateException;
import ro.unitbv.fmi.tmis.platform.model.PrecipitationAvgEachYear;
import ro.unitbv.fmi.tmis.platform.model.Region;

@Path("/api")
public class HivePrecipitationsRS {
	@Inject
	private DbDAO dbDAO;
	@Inject
	private PrecipitationDAO precipitationDAO;
	@Inject
	private RegionDAO regionDAO;
	@Inject
	private PrecipitationAvgEachYearDAO precipitationAvgEachYearDAO;

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
		}
	}

	@DELETE
	@Path("/db/precipitationTable")
	public void deletePrecipitationTable(
			@NotNull(message = "Database name must not be null") @QueryParam("dbName") String dbName) {
		System.out
				.println("Try to create a table with name: [precipitation] in database ["
						+ dbName + "]");
		try {
			precipitationDAO.dropTable(dbName);
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	@PUT
	@Path("/db/precipitationTable")
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
			@NotNull(message = "Region id must not be null") @QueryParam("regionId") Long regionId,
			@NotNull(message = "Database name must not be null") @QueryParam("dbName") String dbName) {
		System.out.println("Try to load data into hive for region with id ["
				+ regionId + "]");
		try {
			Region region = regionDAO.getRegionById(regionId);

			precipitationDAO.loadDataIntoTable(
					dbName,
					"/user/root/extracted-data/precipitations/"
							+ region.getName(), regionId);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	private void extractPrecipitationAvgEachYear(String dbName, Region region,
			int startYear, int endYear) throws SQLException {
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
		Calendar startMonth = Calendar.getInstance();
		startMonth.set(startYear, 0, 1);
		Calendar endMonth = Calendar.getInstance();
		endMonth.set(startYear, 0, 31);

		for (int year = startYear; year <= endYear; year++) {
			for (int month = 1; month <= 12; month++) {
				System.out
						.println("Try to extract precipitation average for month with number ["
								+ month + "] in year [" + year + "]");

				PrecipitationAvgEachYearDTO precDTO = precipitationDAO
						.getAveragePerMonthEachYear(region.getIdRegion(),
								dbName,
								dateFormat.format(startMonth.getTime()),
								dateFormat.format(endMonth.getTime()));

				PrecipitationAvgEachYear entity = new PrecipitationAvgEachYear(
						year, month, precDTO.getAvg(), precDTO.getMax(), region);
				precipitationAvgEachYearDAO
						.insertPrecipitationAvgEachYear(entity);
				startMonth.add(Calendar.MONTH, 1);
				endMonth.add(Calendar.MONTH, 1);
			}
		}
	}

	@GET
	@Path("/db/precipitations/month/avg")
	public void getAvgPerMonth(
			@NotNull(message = "Database name must not be null") @QueryParam("dbName") String dbName,
			@NotNull(message = "Region id must not be null") @QueryParam("regionId") Long regionId,
			@NotNull(message = "Each year option must not be null") @QueryParam("eachYear") Boolean eachYear) {

		Region region = regionDAO.getRegionById(regionId);
		try {
			if (eachYear) {
				extractPrecipitationAvgEachYear(dbName, region,
						region.getStartYear(), region.getEndYear());
			} else {
				// precipitationDAO.getAveragePerMonthAllYears(DB_NAME,
				// region.getStartYear(), region.getEndYear());
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}

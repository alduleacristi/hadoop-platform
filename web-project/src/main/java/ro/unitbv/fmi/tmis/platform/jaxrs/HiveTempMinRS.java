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

import ro.unitbv.fmi.tmis.platform.dao.RegionDAO;
import ro.unitbv.fmi.tmis.platform.dao.TempMinAvgEachYearDAO;
import ro.unitbv.fmi.tmis.platform.hive.dao.TempMinDAO;
import ro.unitbv.fmi.tmis.platform.model.Region;
import ro.unitbv.fmi.tmis.platform.model.TempMinAvgEachYear;

@Path("/api")
public class HiveTempMinRS {
	@Inject
	private TempMinDAO tempMinDAO;
	@Inject
	private RegionDAO regionDAO;
	@Inject
	private TempMinAvgEachYearDAO tempMinAvgEachYearDAO;

	@DELETE
	@Path("/db/tempMinTable")
	public void deleteTempMinTable(
			@NotNull(message = "Database name must not be null") @QueryParam("dbName") String dbName) {
		System.out
				.println("Try to delete a table with name: [temp_min] in database ["
						+ dbName + "]");
		try {
			tempMinDAO.dropTable(dbName);
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	@PUT
	@Path("/db/tempMinTable")
	public void createTempMinTable(
			@NotNull(message = "Database name must not be null") @QueryParam("dbName") String dbName) {
		System.out
				.println("Try to create a table with name: [temp_min] in database ["
						+ dbName + "]");
		try {
			tempMinDAO.createTable(dbName);
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	@POST
	@Path("/db/loadTempMinData")
	public void loadTempMinData(
			@NotNull(message = "Region id must not be null") @QueryParam("regionId") Long regionId,
			@NotNull(message = "Database name must not be null") @QueryParam("dbName") String dbName) {
		System.out.println("Try to load data into hive for region with id ["
				+ regionId + "]");
		try {
			Region region = regionDAO.getRegionById(regionId);

			tempMinDAO.loadDataIntoTable(dbName,
					"/user/root/extracted-data/temp-min/" + region.getName(),
					regionId);
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
						.println("Try to extract average for month with number ["
								+ month + "] in year [" + year + "]");
				double avgResult = tempMinDAO.getAveragePerMonthEachYear(
						region.getIdRegion(), dbName,
						dateFormat.format(startMonth.getTime()),
						dateFormat.format(endMonth.getTime()));

				TempMinAvgEachYear entity = new TempMinAvgEachYear(year, month,
						avgResult, region);
				tempMinAvgEachYearDAO.insertTempMinAvgEachYear(entity);
				startMonth.add(Calendar.MONTH, 1);
				endMonth.add(Calendar.MONTH, 1);
			}
		}
	}

	@GET
	@Path("/db/tempMin/month/avg")
	public void getAvgPerMonth(
			@NotNull(message = "Region id must not be null") @QueryParam("regionId") Long regionId,
			@NotNull(message = "Database name must not be null") @QueryParam("dbName") String dbName,
			@NotNull(message = "Each year option must not be null") @QueryParam("eachYear") Boolean eachYear) {

		Region region = regionDAO.getRegionById(regionId);
		System.out.println("Region name: " + region.getName());
		// TO DO Get start and nd year...
		try {
			if (eachYear) {
				extractPrecipitationAvgEachYear(dbName, region,
						region.getStartYear(), region.getEndYear());
			} else {
//				precipitationDAO.getAveragePerMonthAllYears(DB_NAME,
//						region.getStartYear(), region.getEndYear());
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}

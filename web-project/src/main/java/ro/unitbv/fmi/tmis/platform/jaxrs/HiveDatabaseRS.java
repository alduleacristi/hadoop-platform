package ro.unitbv.fmi.tmis.platform.jaxrs;

import java.sql.SQLException;

import javax.inject.Inject;
import javax.validation.constraints.NotNull;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.QueryParam;

import ro.unitbv.fmi.tmis.platform.hive.dao.DbDAO;
import ro.unitbv.fmi.tmis.platform.hive.dao.PrecipitationDAO;
import ro.unitbv.fmi.tmis.platform.hive.exceptions.FailedToCreateException;

@Path("/api")
public class HiveDatabaseRS {
	private final String DB_NAME = "turism";

	@Inject
	private DbDAO dbDAO;
	@Inject
	private PrecipitationDAO precipitationDAO;

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
			@NotNull(message = "Region name must not be null") @QueryParam("regionName") String regionName) {
		System.out.println("Try to load ");
		try {
			precipitationDAO.loadDataIntoTable(DB_NAME,
					"/user/root/extracted-data/precipitations/Brasov", 8);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}

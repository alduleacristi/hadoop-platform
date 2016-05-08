package ro.unitbv.fmi.tmis.platform.jaxrs;

import java.sql.SQLException;

import javax.inject.Inject;
import javax.validation.constraints.NotNull;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.QueryParam;

import ro.unitbv.fmi.tmis.platform.hive.dao.DbDAO;
import ro.unitbv.fmi.tmis.platform.hive.dao.PrecipitationDAO;
import ro.unitbv.fmi.tmis.platform.hive.exceptions.FailedToCreateException;

@Path("/api")
public class HiveDatabaseRS {
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
			precipitationDAO.loadDataIntoTable(dbName,
					"/user/root/extracted-data/precipitations/Brasov", 1);
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
}

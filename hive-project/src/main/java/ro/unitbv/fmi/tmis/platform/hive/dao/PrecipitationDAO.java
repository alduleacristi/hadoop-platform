package ro.unitbv.fmi.tmis.platform.hive.dao;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.inject.Named;

import ro.unitbv.fmi.tmis.platform.hive.utils.HiveConnection;

@Named
@Stateless
public class PrecipitationDAO {
	@Inject
	private HiveConnection hiveConnection;

	public void createTable(String dbName) throws SQLException {
		Connection con = hiveConnection.getConnection();

		Statement createTable = con.createStatement();
		createTable.execute("USE " + dbName);

		String sql = "CREATE EXTERNAL TABLE precipitation"
				+ " (time DATE,lat DOUBLE,lon DOUBLE, prec DOUBLE) PARTITIONED BY (regionId BIGINT) ROW FORMAT DELIMITED FIELDS TERMINATED BY ','";
		createTable.execute(sql);

		con.close();
	}

	public void loadDataIntoTable(String dbName, String path, long regionId)
			throws SQLException {
		Connection con = hiveConnection.getConnection();

		Statement loadData = con.createStatement();
		loadData.execute("USE " + dbName);

		String sql = "ALTER TABLE precipitation" + " ADD PARTITION (regionId = "
				+ regionId + ") LOCATION '" + path + "'";

		loadData.execute(sql);
		con.close();
	}
}

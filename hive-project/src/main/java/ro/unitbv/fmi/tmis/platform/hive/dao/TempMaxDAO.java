package ro.unitbv.fmi.tmis.platform.hive.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.Calendar;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.inject.Named;

import ro.unitbv.fmi.tmis.platform.hive.utils.HiveConnection;

@Named
@Stateless
public class TempMaxDAO {
	@Inject
	private HiveConnection hiveConnection;

	public void createTable(String dbName) throws SQLException {
		Connection con = hiveConnection.getConnection();
		try {
			Statement createTable = con.createStatement();
			createTable.execute("USE " + dbName);

			String sql = "CREATE EXTERNAL TABLE temp_max"
					+ " (time DATE,lat DOUBLE,lon DOUBLE, temp_max DOUBLE) PARTITIONED BY (regionId BIGINT) ROW FORMAT DELIMITED FIELDS TERMINATED BY ','";
			createTable.execute(sql);
		} finally {
			con.close();
		}
	}

	public void dropTable(String dbName) throws SQLException {
		Connection con = hiveConnection.getConnection();
		try {
			Statement createTable = con.createStatement();
			createTable.execute("USE " + dbName);

			String sql = "DROP TABLE temp_max";
			createTable.execute(sql);
		} finally {
			con.close();
		}
	}

	public void loadDataIntoTable(String dbName, String path, long regionId)
			throws SQLException {
		Connection con = hiveConnection.getConnection();
		try {
			Statement loadData = con.createStatement();
			loadData.execute("USE " + dbName);

			String sql = "ALTER TABLE temp_max" + " ADD PARTITION (regionId = "
					+ regionId + ") LOCATION '" + path + "'";

			loadData.execute(sql);

		} finally {
			con.close();
		}
	}

	public double getAveragePerMonthEachYear(long regionId, String dbName,
			String startDate, String endDate) throws SQLException {
		Connection con = hiveConnection.getConnection();
		try {
			System.out.println("Try to execute query...");

			String sql = " select avg(temp_max) from temp_max p where regionId="
					+ regionId + " and time>? and time<?";

			Statement stmt = con.createStatement();
			stmt.execute("USE " + dbName);
			PreparedStatement pstmt = con.prepareStatement(sql);

			pstmt.setString(1, startDate);
			pstmt.setString(2, endDate);

			ResultSet result = pstmt.executeQuery();
			result.next();

			double avgResult = result.getDouble(1);

			return avgResult;
		} finally {
			con.close();
		}
	}

//	public void getAveragePerMonthAllYears(String dbName, int startYear,
//			int endYear) throws SQLException {
//		Connection con = hiveConnection.getConnection();
//
//		try {
//			System.out.println("Try to execute query...");
//
//			SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
//			Statement stmt = con.createStatement();
//			stmt.execute("USE " + dbName);
//			stmt = con.createStatement();
//
//			for (int i = 1; i <= 12; i++) {
//				String sql = "select avg(prec) from precipitation p where regionId=2 and (";
//
//				Calendar startMonth = Calendar.getInstance();
//				startMonth.set(startYear, i - 1, 1);
//				Calendar endMonth = Calendar.getInstance();
//				endMonth.set(startYear, i - 1, 31);
//				for (int year = startYear; year <= endYear; year++) {
//					if (year - startYear > 0) {
//						sql += " or ";
//					}
//
//					sql += "time>'" + dateFormat.format(startMonth.getTime())
//							+ "' and time<'"
//							+ dateFormat.format(endMonth.getTime()) + "'";
//					startMonth.add(Calendar.YEAR, 1);
//					endMonth.add(Calendar.YEAR, 1);
//				}
//				sql += ")";
//				System.out
//						.println("Try to extract average for month with number ["
//								+ i + "]");
//				System.out.println(sql);
//
//				ResultSet result = stmt.executeQuery(sql);
//				result.next();
//
//				System.out.println("Result: " + result.getDouble(1));
//				startMonth.add(Calendar.MONTH, 1);
//				endMonth.add(Calendar.MONTH, 1);
//			}
//		} finally {
//			con.close();
//		}
//	}

}

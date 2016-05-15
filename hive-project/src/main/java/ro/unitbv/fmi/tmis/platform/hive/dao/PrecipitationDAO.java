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

import org.apache.hive.jdbc.HiveStatement;

import ro.unitbv.fmi.tmis.platform.hive.utils.HiveConnection;

@Named
@Stateless
public class PrecipitationDAO {
	@Inject
	private HiveConnection hiveConnection;

	public void createTable(String dbName) throws SQLException {
		Connection con = hiveConnection.getConnection();
		try {
			Statement createTable = con.createStatement();
			createTable.execute("USE " + dbName);

			String sql = "CREATE EXTERNAL TABLE precipitation"
					+ " (time DATE,lat DOUBLE,lon DOUBLE, prec DOUBLE) PARTITIONED BY (regionId BIGINT) ROW FORMAT DELIMITED FIELDS TERMINATED BY ','";
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

			String sql = "ALTER TABLE precipitation"
					+ " ADD PARTITION (regionId = " + regionId + ") LOCATION '"
					+ path + "'";

			loadData.execute(sql);

		} finally {
			con.close();
		}
	}

	public void getAveragePerMonthEachYear(String dbName, int startYear,
			int endYear) throws SQLException {
		Connection con = hiveConnection.getConnection();

		try {
			System.out.println("Try to execute query...");

			SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
			Calendar startMonth = Calendar.getInstance();
			startMonth.set(startYear, 0, 1);
			Calendar endMonth = Calendar.getInstance();
			endMonth.set(startYear, 0, 31);
			String sql = " select avg(prec) from precipitation p where regionId=2 and time>? and time<?";

			Statement stmt = con.createStatement();
			stmt.execute("USE " + dbName);
			PreparedStatement pstmt = con.prepareStatement(sql);

			for (int year = startYear; year <= endYear; year++) {
				for (int i = 1; i <= 12; i++) {
					System.out
							.println("Try to extract average for month with number ["
									+ i + "] in year [" + year + "]");
					pstmt.setString(1, dateFormat.format(startMonth.getTime()));
					pstmt.setString(2, dateFormat.format(endMonth.getTime()));

					ResultSet result = pstmt.executeQuery();
					result.next();

					System.out.println("Result: " + result.getDouble(1));
					startMonth.add(Calendar.MONTH, 1);
					endMonth.add(Calendar.MONTH, 1);
				}
				startMonth.add(Calendar.YEAR, 1);
				endMonth.add(Calendar.YEAR, 1);
			}
		} finally {
			con.close();
		}
	}

	public void getAveragePerMonthAllYears(String dbName, int startYear,
			int endYear) throws SQLException {
		Connection con = hiveConnection.getConnection();

		try {
			System.out.println("Try to execute query...");

			SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
			Statement stmt = con.createStatement();
			stmt.execute("USE " + dbName);
			stmt = con.createStatement();

			for (int i = 1; i <= 12; i++) {
				String sql = "select avg(prec) from precipitation p where regionId=2 and (";

				Calendar startMonth = Calendar.getInstance();
				startMonth.set(startYear, i - 1, 1);
				Calendar endMonth = Calendar.getInstance();
				endMonth.set(startYear, i - 1, 31);
				for (int year = startYear; year <= endYear; year++) {
					if (year - startYear > 0) {
						sql += " or ";
					}

					sql += "time>'" + dateFormat.format(startMonth.getTime())
							+ "' and time<'"
							+ dateFormat.format(endMonth.getTime()) + "'";
					startMonth.add(Calendar.YEAR, 1);
					endMonth.add(Calendar.YEAR, 1);
				}
				sql += ")";
				System.out
						.println("Try to extract average for month with number ["
								+ i + "]");
				System.out.println(sql);

				ResultSet result = stmt.executeQuery(sql);
				result.next();

				System.out.println("Result: " + result.getDouble(1));
				startMonth.add(Calendar.MONTH, 1);
				endMonth.add(Calendar.MONTH, 1);
			}
		} finally {
			con.close();
		}
	}
}

package ro.unitbv.fmi.tmis.platform.hive.dao;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class Main {
	private static String driverName = "org.apache.hive.jdbc.HiveDriver";

	public static void main(String[] args) throws SQLException {
		try {
			Class.forName(driverName);
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			System.exit(1);
		}
		// replace "hive" here with the name of the user the queries should run
		// as
		Connection con = DriverManager.getConnection(
				"jdbc:hive2://localhost:10000/default", "", "");
		Statement stmt = con.createStatement();

		stmt.executeQuery("CREATE DATABASE test2");
		System.out.println("Database userdb created successfully.");

		con.close();
	}
}

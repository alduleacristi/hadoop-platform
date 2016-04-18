package ro.unitbv.fmi.tmis.platform.utils;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class HiveConnection {
	private static String driverName = "org.apache.hive.jdbc.HiveDriver";
	
	public HiveConnection() throws SQLException{
		 try {
		      Class.forName(driverName);
		    } catch (ClassNotFoundException e) {
		      // TODO Auto-generated catch block
		      e.printStackTrace();
		      System.exit(1);
		    }
		    //replace "hive" here with the name of the user the queries should run as
		    //Connection con = DriverManager.getConnection("jdbc:hive2://localhost:10000", "", "");
		 Connection con = DriverManager.getConnection("jdbc:hive2://localhost:10000/test", "", "");
		    
		    /*Statement stmt = con.createStatement();
		    String tableName = "testHiveDriverTable";
		    stmt.execute("drop table if exists " + tableName);
		    stmt.execute("create table " + tableName + " (key int, value string)");
		    // show tables
		    String sql = "show tables '" + tableName + "'";
		    System.out.println("Running: " + sql);
		    ResultSet res = stmt.executeQuery(sql);
		    if (res.next()) {
		      System.out.println(res.getString(1));
		    }
		       // describe table
		    sql = "describe " + tableName;
		    System.out.println("Running: " + sql);
		    res = stmt.executeQuery(sql);
		    while (res.next()) {
		      System.out.println(res.getString(1) + "\t" + res.getString(2));
		    }*/
	}
}

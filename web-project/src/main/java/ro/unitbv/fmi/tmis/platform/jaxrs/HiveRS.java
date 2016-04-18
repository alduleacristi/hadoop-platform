package ro.unitbv.fmi.tmis.platform.jaxrs;

import java.sql.SQLException;

import javax.ws.rs.GET;
import javax.ws.rs.Path;

import ro.unitbv.fmi.tmis.platform.utils.HiveConnection;

@Path("/api")
public class HiveRS {
	
	@GET
	@Path("/hive/test")
	public void hive() throws SQLException{
		System.out.println("Try to connect to hive...");
		
		HiveConnection hiveConn = new HiveConnection();
	}
}

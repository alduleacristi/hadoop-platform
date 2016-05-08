package ro.unitbv.fmi.tmis.platform.hive.utils;

import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Properties;

import javax.annotation.PostConstruct;
import javax.ejb.Singleton;
import javax.inject.Named;

import org.apache.commons.dbcp.BasicDataSource;

@Named
@Singleton
public class HiveConnection {
	private Properties properties;

	/*static {
		try {
			Class.forName(driverName);
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
	}*/

	private BasicDataSource ds;

	private void readConfFile() throws IOException {
		InputStream in = this.getClass().getClassLoader()
				.getResourceAsStream("hiveConf/conf.properties");

		properties = new Properties();
		properties.load(in);
	}

	@PostConstruct
	private void initialize() throws IOException {
		readConfFile();
		System.out
				.println("### In hive connection postconstruct..."
						+ properties.getProperty(HiveConfigKey.HIVE_HOST
								.getKeyValue()));
		ds = new BasicDataSource();

		ds.setDriverClassName(properties.getProperty(HiveConfigKey.HIVE_DRIVER
				.getKeyValue()));
		ds.setUrl("jdbc:hive2://localhost:10000");
		ds.setUsername("");
		ds.setPassword("");
	}

	public Connection getConnection() throws SQLException {
		return ds.getConnection();
	}
}

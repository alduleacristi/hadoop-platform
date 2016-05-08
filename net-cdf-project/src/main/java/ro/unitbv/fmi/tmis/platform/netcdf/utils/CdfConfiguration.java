package ro.unitbv.fmi.tmis.platform.netcdf.utils;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import javax.annotation.PostConstruct;
import javax.ejb.Singleton;
import javax.inject.Named;

@Singleton
@Named
public class CdfConfiguration {
	private Properties properties;

	@PostConstruct
	private void readConfigurationFile() throws IOException {
		InputStream in = this.getClass().getClassLoader()
				.getResourceAsStream("cdfConf/conf.properties");

		properties = new Properties();
		properties.load(in);
	}

	public Object getProperty(String key) {
		return properties.get(key);
	}
}

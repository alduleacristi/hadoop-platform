package ro.unitbv.fmi.tmis.platform.utils;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import javax.annotation.PostConstruct;
import javax.inject.Named;
import javax.inject.Singleton;

@Singleton
@Named
public class Configuration {
	private Properties properties;

	@PostConstruct
	private void readConfigurationFile() throws IOException {
		InputStream in = this.getClass().getClassLoader()
				.getResourceAsStream("conf/conf.properties");

		properties = new Properties();
		properties.load(in);
	}

	public Object getProperty(String key) {
		return properties.get(key);
	}
}

package ro.unitbv.fmi.tmis.platform.service;

import java.io.File;
import java.net.URISyntaxException;
import java.util.Set;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.inject.Named;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.MultivaluedMap;
import javax.ws.rs.core.Response;

import ro.unitbv.fmi.tmis.platform.utils.ConfigKey;
import ro.unitbv.fmi.tmis.platform.utils.Configuration;

@Named
@Stateless
public class HdfsServiceRest {
	@Inject
	Configuration config;

	public void createDirectory(String path) {
		String host = (String) config.getProperty(ConfigKey.HDFS_HOST
				.getKeyValue());
		String port = (String) config.getProperty(ConfigKey.HDFS_PORT
				.getKeyValue());
		String user = (String) config.getProperty(ConfigKey.HDFS_USER
				.getKeyValue());

		Client client = ClientBuilder.newClient();
		WebTarget target = client.target(host + ":" + port)
				.path("/webhdfs/v1/user/" + user + "/" + path)
				.queryParam("user.name", user).queryParam("op", "MKDIRS");
		System.out.println("Try to create a new folder in hdfs -> user/" + user
				+ "/" + path);
		target.request().put(null);
	}

	public void uploadFile(String path, File file) throws URISyntaxException {
		String host = (String) config.getProperty(ConfigKey.HDFS_HOST
				.getKeyValue());
		String port = (String) config.getProperty(ConfigKey.HDFS_PORT
				.getKeyValue());
		String user = (String) config.getProperty(ConfigKey.HDFS_USER
				.getKeyValue());

		createDirectory(path);

		Client client = ClientBuilder.newClient();
		WebTarget target = client.target(host + ":" + port)
				.path("/webhdfs/v1/user/" + user + "/" + path + file.getName())
				.queryParam("user.name", user).queryParam("op", "CREATE")
				.queryParam("overwrite", true);
		Response response = target.request().put(null);

		String location = null;
		MultivaluedMap<String, Object> headers = response.getMetadata();
		Set<String> headersName = headers.keySet();
		for (String name : headersName) {
			if (name.equals("Location")) {
				location = (String) headers.get(name).get(0);
			}
		}

		System.out.println("Try to save file in [" + location + "]");

		Client client2 = ClientBuilder.newClient();
		response = client2.target(location).request()
				.put(Entity.entity(file, MediaType.APPLICATION_OCTET_STREAM));
		int status = response.getStatus();
		System.out.println("Status: " + status);
	}
}

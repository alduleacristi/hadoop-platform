package ro.unitbv.fmi.tmis.platform.jaxrs;

import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import ro.unitbv.fmi.tmis.platform.utils.ConfigKey;
import ro.unitbv.fmi.tmis.platform.utils.Configuration;

@Path("/api/cluster")
public class StatusRS {
	@Inject
	Configuration config;

	@GET
	@Path("/info")
	@Produces(MediaType.APPLICATION_JSON)
	public Response getClusterInfo() {
		String host = (String) config.getProperty(ConfigKey.YARN_HOST
				.getKeyValue());
		String port = (String) config.getProperty(ConfigKey.YARN_PORT
				.getKeyValue());

		Client client = ClientBuilder.newClient();
		Response response = client.target(host + ":" + port)
				.path("ws/v1/cluster/info").request().get();
		return Response.ok(response.readEntity(Object.class)).build();
	}

	@GET
	@Path("/metrics")
	@Produces(MediaType.APPLICATION_JSON)
	public Response getClusterMetrics() {
		String host = (String) config.getProperty(ConfigKey.YARN_HOST
				.getKeyValue());
		String port = (String) config.getProperty(ConfigKey.YARN_PORT
				.getKeyValue());

		Client client = ClientBuilder.newClient();
		Response response = client.target(host + ":" + port)
				.path("ws/v1/cluster/metrics").request().get();
		return Response.ok(response.readEntity(Object.class)).build();
	}

	@GET
	@Path("/nodes")
	@Produces(MediaType.APPLICATION_JSON)
	public Response getClusterNodes() {
		String host = (String) config.getProperty(ConfigKey.YARN_HOST
				.getKeyValue());
		String port = (String) config.getProperty(ConfigKey.YARN_PORT
				.getKeyValue());

		Client client = ClientBuilder.newClient();
		Response response = client.target(host + ":" + port)
				.path("ws/v1/cluster/nodes").request().get();
		return Response.ok(response.readEntity(Object.class)).build();
	}

	@GET
	@Path("/nodes/{nodeId}")
	@Produces(MediaType.APPLICATION_JSON)
	public Response getClusterNode(@PathParam("nodeId") String nodeId) {
		String host = (String) config.getProperty(ConfigKey.YARN_HOST
				.getKeyValue());
		String port = (String) config.getProperty(ConfigKey.YARN_PORT
				.getKeyValue());

		Client client = ClientBuilder.newClient();
		Response response = client.target(host + ":" + port)
				.path("ws/v1/cluster/nodes/" + nodeId).request().get();
		return Response.ok(response.readEntity(Object.class)).build();
	}
}

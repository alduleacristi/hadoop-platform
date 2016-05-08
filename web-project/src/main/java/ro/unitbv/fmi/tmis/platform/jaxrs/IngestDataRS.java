package ro.unitbv.fmi.tmis.platform.jaxrs;

import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;
import java.text.ParseException;

import javax.inject.Inject;
import javax.validation.constraints.NotNull;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import org.codehaus.jackson.JsonGenerationException;
import org.codehaus.jackson.map.JsonMappingException;
import org.codehaus.jackson.map.ObjectMapper;

import ro.unitbv.fmi.tmis.platform.dao.RegionDAO;
import ro.unitbv.fmi.tmis.platform.exception.AlreadyExistException;
import ro.unitbv.fmi.tmis.platform.exception.FailedToSaveException;
import ro.unitbv.fmi.tmis.platform.exception.InvalidParameterException;
import ro.unitbv.fmi.tmis.platform.model.Points;
import ro.unitbv.fmi.tmis.platform.model.Points.Point;
import ro.unitbv.fmi.tmis.platform.model.Region;
import ro.unitbv.fmi.tmis.platform.netcdf.DataExtractor;
import ro.unitbv.fmi.tmis.platform.netcdf.exceptions.VarNotFoundException;
import ro.unitbv.fmi.tmis.platform.netcdf.utils.DataType;
import ro.unitbv.fmi.tmis.platform.service.HdfsServiceRest;
import ro.unitbv.fmi.tmis.platform.utils.ConfigKey;
import ro.unitbv.fmi.tmis.platform.utils.Configuration;
import ro.unitbv.fmi.tmis.platform.utils.Constants;
import ucar.ma2.InvalidRangeException;

@Path("/api")
public class IngestDataRS {
	@Inject
	private Constants constants;
	@Inject
	private Configuration conf;
	@Inject
	private HdfsServiceRest hdfsService;
	@Inject
	private DataExtractor precipitationExtractor;
	@Inject
	private RegionDAO regionDAO;

	private String getPathInHdfs(DataType dataType) {
		switch (dataType) {
		case PRECIPITATION: {
			return (String) conf.getProperty(ConfigKey.HDFS_PRECIPITATIONS_PATH
					.getKeyValue());
		}
		case MAX_TEMP: {
			return (String) conf.getProperty(ConfigKey.HDFS_MAX_TEMP_PATH
					.getKeyValue());
		}
		case MIN_TEMP: {
			return (String) conf.getProperty(ConfigKey.HDFS_MIN_TEMP_PATH
					.getKeyValue());
		}
		}

		return null;
	}

	private File extractDataAndUploadInHdfs(int minLat, int maxLat, int minLon,
			int maxLon, int year, String regionName, DataType dataType)
			throws InvalidRangeException, IOException, ParseException,
			VarNotFoundException, URISyntaxException {
		System.out.println("Try to extract [" + dataType.toString()
				+ "] for year [" + year + "]");
		File file = precipitationExtractor.extractAndWriteData(minLat, maxLat,
				minLon, maxLon, year, regionName, dataType);

		hdfsService.uploadFile(
				getPathInHdfs(dataType) + "/" + regionName + "/", file);
		// file.delete();

		return file;
	}

	private void ingest(int startYear, int endYear, String regionName,
			int minLat, int maxLat, int minLon, int maxLon) throws Exception {
		for (int year = startYear; year <= endYear; year++) {
			extractDataAndUploadInHdfs(minLat, maxLat, minLon, maxLon, year,
					regionName, DataType.PRECIPITATION);
			extractDataAndUploadInHdfs(minLat, maxLat, minLon, maxLon, year,
					regionName, DataType.MAX_TEMP);
			extractDataAndUploadInHdfs(minLat, maxLat, minLon, maxLon, year,
					regionName, DataType.MIN_TEMP);
		}
	}

	private int getMinLatId(double lat) {
		if (lat < -90 || lat > 90) {
			throw new InvalidParameterException(
					"Min lat must be between -90 and 90");
		}

		if (lat >= 0) {
			double rez = (lat - 0.125) / 0.25;

			if (rez < 0) {
				System.out.println("Min lat: " + -0.125);
				return constants.getIdOfLatitude(-0.125);
			} else {
				int intRez = (int) rez;
				System.out.println("Partea intreaga: " + intRez);
				double minLat = intRez * 0.25 + 0.125;
				System.out.println("Min lat: " + minLat);
				return constants.getIdOfLatitude(minLat);
			}
		} else {
			double rez = (lat + 0.125) / 0.25;

			int intRez = (int) rez;

			if (rez - intRez < 0) {
				intRez--;
			}
			System.out.println("Partea intreaga: " + intRez);
			double minLat = intRez * 0.25 - 0.125;
			System.out.println("Min lat: " + minLat);
			return constants.getIdOfLatitude(minLat);
		}
	}

	private int getMaxLatId(double lat) {
		if (lat < -90 || lat > 90) {
			throw new InvalidParameterException(lat
					+ "Max lat must be between -90 and 90");
		}

		if (lat >= 0) {
			double rez = (lat - 0.125) / 0.25;

			if (rez < 0) {
				System.out.println("Max lat: " + 0.125);
				return constants.getIdOfLatitude(0.125);
			} else {
				int intRez = (int) rez;
				System.out.println("Partea intreaga: " + intRez);
				double maxLat = intRez * 0.25 + 0.125;

				if (rez - intRez > 0) {
					maxLat += 0.25;
				}
				System.out.println("Max lat: " + maxLat);
				return constants.getIdOfLatitude(maxLat);
			}
		} else {
			double rez = (lat + 0.125) / 0.25;

			int intRez = (int) rez;

			if (rez - intRez > 0) {
				intRez++;
			}
			System.out.println("Partea intreaga: " + intRez);
			double maxLat = intRez * 0.25 - 0.125;
			System.out.println("Max lat: " + maxLat);
			return constants.getIdOfLatitude(maxLat);
		}
	}

	private int getMinLonId(double lon) {
		if (lon < 0 || lon > 360) {
			throw new InvalidParameterException(
					"Min lon must be between 0 and 360");
		}

		double rez = (lon - 0.125) / 0.25;
		int intRez = (int) rez;
		System.out.println("Partea intreaga: " + intRez);
		double minLon = intRez * 0.25 + 0.125;
		System.out.println("Min lon: " + minLon);
		return constants.getIdOfLongitude(minLon);
	}

	private int getMaxLonId(double lon) {
		if (lon < 0 || lon > 360) {
			throw new InvalidParameterException(
					"Max lon must be between 0 and 360");
		}

		double rez = (lon - 0.125) / 0.25;
		int intRez = (int) rez;
		double maxLon = intRez * 0.25 + 0.125;

		if (rez - intRez > 0) {
			maxLon += 0.25;
		}
		System.out.println("Max lon: " + maxLon);

		return constants.getIdOfLongitude(maxLon);
	}

	private Region saveRegion(int startYear, int endYear, double minLat,
			double maxLat, double minLon, double maxLon, String regionName)
			throws JsonGenerationException, JsonMappingException, IOException,
			FailedToSaveException, AlreadyExistException {
		Region region = new Region();
		region.setName(regionName);
		region.setStartYear(startYear);
		region.setEndYear(endYear);
		region.setMaxLat(maxLat);
		region.setMinLat(minLat);
		region.setMinLon(minLon);
		region.setMaxLon(maxLon);

		return regionDAO.saveRegion(region);
	}

	@POST
	@Path("/ingest/region")
	@Produces(MediaType.TEXT_PLAIN)
	public Response ingestData(
			@NotNull(message = "Min Lat param must not be null") @QueryParam("minLat") Double minLat,
			@NotNull(message = "Max Lat param must not be null") @QueryParam("maxLat") Double maxLat,
			@NotNull(message = "Min Lon param must not be null") @QueryParam("minLon") Double minLon,
			@NotNull(message = "Max Lat param must not be null") @QueryParam("maxLon") Double maxLon,
			@NotNull(message = "Region name must not be null") @QueryParam("regionName") String regionName,
			@QueryParam("year") int year) throws AlreadyExistException {
		System.out.println("Ingestion job was called...");

		String nrOfYears = (String) conf
				.getProperty(ConfigKey.TURISM_REGIONS_NR_OF_YEARS.getKeyValue());
		Integer yearI = Integer.valueOf(nrOfYears);
		Region region = null;

		try {
			region = saveRegion(year - yearI / 2, year + yearI / 2, minLat,
					maxLat, minLon, maxLon, regionName);

			if (year == 0) {
				ingest(yearI, yearI, regionName, getMinLatId(minLat),
						getMaxLatId(maxLat), getMinLonId(minLon),
						getMaxLonId(maxLon));
				System.out.println("!!! After ingest method in if");
			} else {
				if (year < 0 || year < 1950 || year > 2099) {
					throw new InvalidParameterException(
							"The year must be between 1950 and 2099");
				} else {
					ingest(year - yearI / 2, year + yearI / 2, regionName,
							getMinLatId(minLat), getMaxLatId(maxLat),
							getMinLonId(minLon), getMaxLonId(maxLon));
					System.out.println("!!! After ingest method in else");
				}
			}
		} catch (AlreadyExistException aex) {
			aex.printStackTrace();
			throw aex;
		} catch (Exception e) {
			e.printStackTrace();
			try {
				if (region != null) {
					regionDAO.deleteRegion(region.getId());
				}
			} catch (Exception exc) {
				exc.printStackTrace();
			}
		}

		return Response.ok().build();
	}
}

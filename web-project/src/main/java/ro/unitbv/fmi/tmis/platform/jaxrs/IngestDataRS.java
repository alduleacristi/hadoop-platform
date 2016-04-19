package ro.unitbv.fmi.tmis.platform.jaxrs;

import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;
import java.text.ParseException;

import javax.inject.Inject;
import javax.validation.constraints.NotNull;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.QueryParam;

import ro.unitbv.fmi.tmis.platform.exception.InvalidParameterException;
import ro.unitbv.fmi.tmis.platform.netcdf.NetCdfUtils;
import ro.unitbv.fmi.tmis.platform.service.HdfsService;
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
	private HdfsService hdfsService;

	private void ingest(int startYear, int endYear, String regionName,
			int minLat, int maxLat, int minLon, int maxLon) throws IOException,
			InvalidRangeException, URISyntaxException, ParseException {
		for (int year = startYear; year <= endYear; year++) {
			NetCdfUtils netCdfUtils = new NetCdfUtils(year, regionName);

			System.out.println("MinLat: " + minLat);
			System.out.println("MaxLat: " + maxLat);
			System.out.println("MinLon: " + minLon);
			System.out.println("MaxLon: " + maxLon);
			File outputFile = netCdfUtils.writePrecipitation(minLat, maxLat,
			 minLon, maxLon);
			//File file = new File(
			//		"/root/Dizertatie/Software/wildfly-9.0.2.Final/data/extracted/test-region2/precipitatii/2016.csv");
			//hdfsService.uploadFile("turism/" + regionName + "/precipitatii",
			//		file);
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
			throw new InvalidParameterException(
					"Max lat must be between -90 and 90");
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

	@POST
	@Path("/ingest/region")
	public String ingestData(
			@NotNull(message = "Min Lat param must not be null") @QueryParam("minLat") Double minLat,
			@NotNull(message = "Max Lat param must not be null") @QueryParam("maxLat") Double maxLat,
			@NotNull(message = "Min Lon param must not be null") @QueryParam("minLon") Double minLon,
			@NotNull(message = "Max Lat param must not be null") @QueryParam("maxLon") Double maxLon,
			@NotNull(message = "Region name must not be null") @QueryParam("regionName") String regionName,
			@QueryParam("year") int year) throws IOException,
			InvalidRangeException, URISyntaxException, ParseException {
		System.out.println("Ingestion job was called...");

		// constants.getLatitudes();
		if (year == 0) {
			Integer yearI = (Integer) conf
					.getProperty(ConfigKey.TURISM_REGIONS_NR_OF_YEARS
							.getKeyValue());

			ingest(yearI, yearI, regionName, getMinLatId(minLat),
					getMaxLatId(maxLat), getMinLonId(minLon),
					getMaxLonId(maxLon));
		} else {
			if (year < 0 || year < 1950 || year > 2099) {
				throw new InvalidParameterException(
						"The year must be between 1950 and 2099");
			} else {
				ingest(year, year, regionName, getMinLatId(minLat),
						getMaxLatId(maxLat), getMinLonId(minLon),
						getMaxLonId(maxLon));
			}
		}

		return "Data was ingested with success";
	}
}

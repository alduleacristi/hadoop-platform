package ro.unitbv.fmi.tmis.platform.netcdf.utils;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import ucar.ma2.Array;
import ucar.ma2.InvalidRangeException;
import ucar.ma2.Range;
import ucar.nc2.NetcdfFile;
import ucar.nc2.Variable;

public class NetCdfUtils {
	//private final String SERVER_LOCATION;

	/*private NetcdfFile cdfFile;
	private int year;
	private String regionName;*/

	/*public NetCdfUtils(int year, String regionName) throws IOException {
		this.year = year;
		this.regionName = regionName;
		// SERVER_LOCATION = System.getProperty("jboss.server.home.dir");
		SERVER_LOCATION = "/root/Disertatie/Software/wildfly-9.0.2.Final";

		String fileLocation = SERVER_LOCATION
				+ "/data/nasa/precipitatii/pr_day_BCSD_rcp45_r1i1p1_ACCESS1-0_"
				+ year + ".nc";
		System.out.println("!!! FileLocation :" + fileLocation + ":");
		cdfFile = NetcdfFile.open(fileLocation);
	}*/

	public static double getTime(NetcdfFile cdfFile, int id)
			throws InvalidRangeException, IOException {
		Variable timeVar = cdfFile.findVariable("time");

		List<Range> ranges = new ArrayList<Range>();
		ranges.add(new Range(id, id));
		Array results = timeVar.read(ranges);

		return results.getDouble(0);
	}

	public static double getLat(NetcdfFile cdfFile, int id)
			throws InvalidRangeException, IOException {
		Variable timeVar = cdfFile.findVariable("lat");

		List<Range> ranges = new ArrayList<Range>();
		ranges.add(new Range(id, id));
		Array results = timeVar.read(ranges);

		return results.getDouble(0);
	}

	public static double getLon(NetcdfFile cdfFile, int id)
			throws InvalidRangeException, IOException {
		Variable timeVar = cdfFile.findVariable("lon");

		List<Range> ranges = new ArrayList<Range>();
		ranges.add(new Range(id, id));
		Array results = timeVar.read(ranges);

		return results.getDouble(0);
	}

	/*public void convertDate() throws ParseException {
		double val = 36532.5;
		int nrOfYears = (int) val / 365;
		int nrOfDays = (int) val % 365;
		System.out.println(nrOfDays);

		Calendar calendar = Calendar.getInstance();
		DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
		Date oldDate = dateFormat.parse("1850-01-01");
		calendar.setTime(oldDate);
		calendar.add(Calendar.YEAR, nrOfYears);
		calendar.add(Calendar.DAY_OF_YEAR, nrOfDays);

		System.out.println(dateFormat.format(calendar.getTime()));
	}*/
}

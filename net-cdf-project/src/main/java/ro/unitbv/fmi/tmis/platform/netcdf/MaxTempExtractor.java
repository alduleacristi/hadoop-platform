package ro.unitbv.fmi.tmis.platform.netcdf;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import ro.unitbv.fmi.tmis.platform.netcdf.utils.NetCdfUtils;
import ucar.ma2.Array;
import ucar.ma2.Index;
import ucar.ma2.InvalidRangeException;
import ucar.ma2.Range;
import ucar.nc2.NetcdfFile;
import ucar.nc2.Variable;

public class MaxTempExtractor {
	private String SERVER_LOCATION;

	private NetcdfFile cdfFile;
	private int year;
	private String regionName;

	public MaxTempExtractor(int year, String regionName)
			throws IOException {
		this.year = year;
		this.regionName = regionName;
		SERVER_LOCATION = System.getProperty("jboss.server.home.dir");

		String fileLocation = SERVER_LOCATION
				+ "/data/nasa/temp-max/pr_day_BCSD_rcp45_r1i1p1_ACCESS1-0_"
				+ year + ".nc";
		System.out.println("!!! FileLocation :" + fileLocation + ":");
		cdfFile = NetcdfFile.open(fileLocation);
	}

	public File writePrecipitation(int latMin, int latMax, int lonMin,
			int lonMax) throws InvalidRangeException, IOException,
			ParseException {

		String outputFolderLocation = SERVER_LOCATION + "/data/extracted/"
				+ "precipitations/" + regionName;
		File outputFolder = new File(outputFolderLocation);
		outputFolder.mkdirs();
		File outputFile = new File(outputFolder, year + ".csv");

		Variable prVar = cdfFile.findVariable("pr");
		int shapes[] = prVar.getShape();

		List<Range> ranges = new ArrayList<Range>();
		Range rangeTime = new Range(0, shapes[0] - 1);
		Range rangeLat = new Range(latMin, latMax);
		Range rangeLon = new Range(lonMin, lonMax);
		ranges.add(rangeTime);
		ranges.add(rangeLat);
		ranges.add(rangeLon);

		Array results = prVar.read(ranges);
		Index index = results.getIndex();

		double startDay = NetCdfUtils.getTime(cdfFile, 0);
		double currentDay = startDay;
		Calendar calendar = Calendar.getInstance();
		DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
		Date startDate = dateFormat.parse(year + "-01-01");
		calendar.setTime(startDate);

		try (BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(
				new FileOutputStream(outputFile)))) {
			for (int i = 0; i < shapes[0]; i++) {
				for (int j = 0; j < latMax - latMin + 1; j++) {
					for (int k = 0; k < lonMax - lonMin + 1; k++) {
						index.set(i, j, k);
						double val = results.getDouble(index);
						String valString = String.format("%.15f",
								val * 86400 * 1000 / 1000);

						double nextDay = NetCdfUtils.getTime(cdfFile, i);
						if (nextDay - currentDay > 0) {
							calendar.add(Calendar.DAY_OF_YEAR,
									(int) (nextDay - currentDay));
							currentDay = nextDay;
						}
						String line = dateFormat.format(calendar.getTime())
								+ "," + NetCdfUtils.getLat(cdfFile, j + latMin)
								+ "," + NetCdfUtils.getLon(cdfFile, k + lonMin)
								+ "," + valString;

						bw.write(line + "\n");
					}
				}
			}
		}

		return outputFile;
	}
}

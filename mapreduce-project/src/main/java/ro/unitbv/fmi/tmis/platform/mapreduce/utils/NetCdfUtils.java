package ro.unitbv.fmi.tmis.platform.mapreduce.utils;

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

import org.apache.hadoop.mapreduce.Mapper.Context;

import ro.unitbv.fmi.tmis.platform.mapreduce.model.Precipitation;
import ucar.ma2.Array;
import ucar.ma2.Index;
import ucar.ma2.InvalidRangeException;
import ucar.ma2.Range;
import ucar.nc2.NetcdfFile;
import ucar.nc2.Variable;

public class NetCdfUtils {
	private NetcdfFile cdfFile;
	private int year;
	//private String regionName;

	public NetCdfUtils(String fileLocation) throws IOException {
		System.out.println("!!! In NetCdfUitls constructor");

		cdfFile = NetcdfFile.open(fileLocation);
		System.out.println("!!! Before exit the constructor...");
	}

	public double getTime(NetcdfFile cdfFile, int id)
			throws InvalidRangeException, IOException {
		Variable timeVar = cdfFile.findVariable("time");

		List<Range> ranges = new ArrayList<Range>();
		ranges.add(new Range(id, id));
		Array results = timeVar.read(ranges);

		return results.getDouble(0);
	}

	public double getLat(NetcdfFile cdfFile, int id)
			throws InvalidRangeException, IOException {
		Variable timeVar = cdfFile.findVariable("lat");

		List<Range> ranges = new ArrayList<Range>();
		ranges.add(new Range(id, id));
		Array results = timeVar.read(ranges);

		return results.getDouble(0);
	}

	public double getLon(NetcdfFile cdfFile, int id)
			throws InvalidRangeException, IOException {
		Variable timeVar = cdfFile.findVariable("lon");

		List<Range> ranges = new ArrayList<Range>();
		ranges.add(new Range(id, id));
		Array results = timeVar.read(ranges);

		return results.getDouble(0);
	}

	public void convertDate() throws ParseException {
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
	}

	public void writePrecipitation(int latMin, int latMax, int lonMin,
			int lonMax, Context ctx) throws InvalidRangeException, IOException,
			ParseException, InterruptedException {

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

		double startDay = getTime(cdfFile, 0);
		double currentDay = startDay;
		Calendar calendar = Calendar.getInstance();
		DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
		Date startDate = dateFormat.parse(year + "-01-01");
		calendar.setTime(startDate);

		for (int i = 0; i < shapes[0]; i++) {
			for (int j = 0; j < latMax - latMin + 1; j++) {
				for (int k = 0; k < lonMax - lonMin + 1; k++) {
					index.set(i, j, k);
					double val = results.getDouble(index);
					val = val * 86400 * 1000 / 1000;
					String valString = String.format("%.15f",
							val * 86400 * 1000 / 1000);

					double nextDay = getTime(cdfFile, i);
					if (nextDay - currentDay > 0) {
						calendar.add(Calendar.DAY_OF_YEAR,
								(int) (nextDay - currentDay));
						currentDay = nextDay;
					}

					Precipitation prec = new Precipitation(calendar.getTime(),
							getLat(cdfFile, j + latMin), getLon(cdfFile, k
									+ lonMin), val);

					ctx.write(null, prec);
				}
			}
		}
	}
}

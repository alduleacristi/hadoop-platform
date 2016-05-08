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

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.inject.Named;

import ro.unitbv.fmi.tmis.platform.netcdf.exceptions.VarNotFoundException;
import ro.unitbv.fmi.tmis.platform.netcdf.utils.CdfConfigKey;
import ro.unitbv.fmi.tmis.platform.netcdf.utils.CdfConfiguration;
import ro.unitbv.fmi.tmis.platform.netcdf.utils.DataType;
import ro.unitbv.fmi.tmis.platform.netcdf.utils.NetCdfUtils;
import ucar.ma2.Array;
import ucar.ma2.Index;
import ucar.ma2.InvalidRangeException;
import ucar.ma2.Range;
import ucar.nc2.NetcdfFile;
import ucar.nc2.Variable;

@Named
@Stateless
public class DataExtractor {
	@Inject
	private CdfConfiguration cdfConf;

	private String serverLocation;

	private NetcdfFile cdfFile;

	private File initialize(int year, String regionName, DataType dataType)
			throws IOException {
		serverLocation = System.getProperty("jboss.server.home.dir");

		String inputPath = null;
		String outPath = null;
		switch (dataType) {
		case PRECIPITATION: {
			inputPath = (String) cdfConf
					.getProperty(CdfConfigKey.CDF_FILE_PREC_IN_PATH
							.getKeyValue());
			outPath = (String) cdfConf
					.getProperty(CdfConfigKey.CDF_FILE_PREC_OUT_PATH
							.getKeyValue());
			break;
		}
		case MAX_TEMP: {
			inputPath = (String) cdfConf
					.getProperty(CdfConfigKey.CDF_FILE_MAX_TEMP_IN_PATH
							.getKeyValue());
			outPath = (String) cdfConf
					.getProperty(CdfConfigKey.CDF_FILE_MAX_TEMP_OUT_PATH
							.getKeyValue());
			break;
		}
		case MIN_TEMP: {
			inputPath = (String) cdfConf
					.getProperty(CdfConfigKey.CDF_FILE_MIN_TEMP_IN_PATH
							.getKeyValue());
			outPath = (String) cdfConf
					.getProperty(CdfConfigKey.CDF_FILE_MIN_TEMP_OUT_PATH
							.getKeyValue());
			break;
		}
		}

		String fileLocation = serverLocation + "/" + inputPath + year + ".nc";
		System.out.println("!!! FileLocation :" + fileLocation + ":");
		cdfFile = NetcdfFile.open(fileLocation);

		String outputFolderLocation = serverLocation + "/" + outPath + "/"
				+ regionName;
		File outputFolder = new File(outputFolderLocation);
		outputFolder.mkdirs();
		File outputFile = new File(outputFolder, year + ".csv");

		return outputFile;
	}

	private String getVariableType(DataType dataType) {
		switch (dataType) {
		case PRECIPITATION: {
			return "pr";
		}
		case MAX_TEMP: {
			return "tasmax";
		}
		case MIN_TEMP: {
			return "tasmin";
		}
		}

		return null;
	}

	private double processExtractedData(DataType dataType, double val) {
		switch (dataType) {
		case PRECIPITATION: {
			return val * 86400 * 1000 / 1000;
		}
		case MAX_TEMP: {
			return val - 273.15;
		}
		case MIN_TEMP: {
			return val - 273.15;
		}
		}

		return 0.0;
	}

	public File extractAndWriteData(int latMin, int latMax, int lonMin,
			int lonMax, int year, String regionName, DataType dataType)
			throws InvalidRangeException, IOException, ParseException,
			VarNotFoundException {

		File outputFile = initialize(year, regionName, dataType);

		String varName = getVariableType(dataType);
		Variable var = null;

		if (varName == null) {
			throw new VarNotFoundException("Unknown type to extract");
		} else {
			var = cdfFile.findVariable(varName);
		}
		int shapes[] = var.getShape();

		List<Range> ranges = new ArrayList<Range>();
		Range rangeTime = new Range(0, shapes[0] - 1);
		Range rangeLat = new Range(latMin, latMax);
		Range rangeLon = new Range(lonMin, lonMax);
		ranges.add(rangeTime);
		ranges.add(rangeLat);
		ranges.add(rangeLon);

		Array results = var.read(ranges);
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
								processExtractedData(dataType, val));

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

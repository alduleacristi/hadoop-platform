package ro.unitbv.fmi.tmis.platform.netcdf;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import ucar.ma2.InvalidRangeException;

public class Main {
	public static void main(String[] args) throws IOException,
			InvalidRangeException, ParseException {
		/*
		 * NetcdfFile ncfile = null;
		 * 
		 * try { ncfile = NetcdfFile.open(
		 * "F:/Disertatie/Date/Nasa/pr_day_BCSD_historical_r1i1p1_bcc-csm1-1_1950.nc"
		 * ); //ncfile = NetcdfFile.open(
		 * "http://nasanex.s3.amazonaws.com/NEX-GDDP/BCSD/historical/day/atmos/pr/r1i1p1/v1.0/pr_day_BCSD_historical_r1i1p1_CCSM4_1950.nc"
		 * ); String varName = "time"; String precipation = "pr"; Variable v =
		 * ncfile.findVariable(varName); Variable precipitationVar =
		 * ncfile.findVariable(precipation); if (precipitationVar == null) {
		 * System.out.println("->Lat variable is null"); } else {
		 * System.out.println("->Lat variable is not null");
		 * System.out.println(v.read().getSize()); // Array array =
		 * v.read("0:10"); // Array array2 =
		 * precipitationVar.read("0:2, 0:1, 0:1");
		 * 
		 * int[] origin = new int[] { 2, 0, 0 }; int[] size = new int[] { 1, 2,
		 * 2 }; Array array3 = precipitationVar.read(origin, size).reduce(0);
		 * 
		 * List<Range> ranges = new ArrayList<>(); ranges.add(new
		 * ucar.ma2.Range(0, 0)); ranges.add(new ucar.ma2.Range(0, 3));
		 * ranges.add(new ucar.ma2.Range(0, 0)); Array array4 =
		 * precipitationVar.read(ranges); int[] shape = array4.getShape(); Index
		 * index = array4.getIndex();
		 * 
		 * for (int i = 0; i < shape[0]; i++) { for (int j = 0; j < shape[1];
		 * j++) { for (int k = 0; k < shape[2]; k++) { double dval =
		 * array4.getDouble(index.set(i, j, k)); double dvalT = dval / 1000;
		 * System.out.println(String.format("%d,%d,%d,%f", i, j, k,dvalT));
		 * System.out.println("Shape 1 -> " + shape[1]);
		 * //System.out.println("Max double -> "+String.format("%f",
		 * Double.MAX_VALUE)); } } }
		 * 
		 * // NCdumpW.printArray(array4, precipation, new //
		 * PrintWriter(System.out), null); }
		 * System.out.println("->The file was opened"); } catch (IOException e)
		 * { e.printStackTrace(); }
		 */

		//NetCdfUtils cdfInstance = new NetCdfUtils(2015, "test-region");

		File outputFile = new File(
				"/root/Desktop/2010.csv");
		//outputFile.mkdirs();
		//BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(
		//		new FileOutputStream(outputFile)));
		//cdfInstance.writePrecipitation(212, 222, 95, 104);
		//bw.close();

		/*
		 * Configuration conf = new Configuration(); Path out = new
		 * Path("/user/root/test-regions/extracted/precipitatii"); FileSystem fs
		 * = FileSystem.get(conf);
		 * 
		 * if (!fs.exists(out)) { fs.mkdirs(out); }
		 * 
		 * fs.copyFromLocalFile( new Path(
		 * "/root/Dizertatie/Software/wildfly-9.0.2.Final/data/extracted/precipitatii/2010.csv"
		 * ), out);
		 */
	}
}

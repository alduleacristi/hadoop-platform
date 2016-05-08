package ro.unitbv.fmi.tmis.platform.mapreduce.map;

import java.io.IOException;
import java.text.ParseException;

import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Mapper;

import ro.unitbv.fmi.tmis.platform.mapreduce.model.Precipitation;
import ro.unitbv.fmi.tmis.platform.mapreduce.utils.NetCdfUtils;
import ucar.ma2.InvalidRangeException;

public class ExtractDataMap extends
		Mapper<LongWritable, Text, Text, Precipitation> {
	private Text outputValue = new Text();
	private final static Text ONE = new Text();

	@Override
	protected void map(LongWritable key, Text value, Context context)
			throws IOException, InterruptedException {
		String[] words = value.toString().split(",");
		// outputValue.set(words[1]);
		// Precipitation prec = new Precipitation(25.6, 27.3, 100.25);
		// context.write(null, prec);

		System.out.println("Path: :"+words[1]+":");
		NetCdfUtils utils = new NetCdfUtils(words[1]);
		try {
			utils.writePrecipitation(212, 222, 95, 104, context);
		} catch (InvalidRangeException e) {
			e.printStackTrace();
			throw new RuntimeException("Failed ingestion job", e);
		} catch (ParseException e) {
			e.printStackTrace();
			throw new RuntimeException("Failed ingestion job", e);
		}
	}
}

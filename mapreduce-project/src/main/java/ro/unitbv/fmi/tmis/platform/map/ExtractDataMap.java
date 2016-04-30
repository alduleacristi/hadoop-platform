package ro.unitbv.fmi.tmis.platform.map;

import java.io.IOException;

import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Mapper;

public class ExtractDataMap extends Mapper<LongWritable, Text, Text, Text> {
	private Text outputValue = new Text();
	private final static Text ONE = new Text();

	@Override
	protected void map(LongWritable key, Text value, Context context)
			throws IOException, InterruptedException {
		String[] words = value.toString().split(",");
		outputValue.set(words[1]);
		context.write(outputValue, ONE);
	}
}

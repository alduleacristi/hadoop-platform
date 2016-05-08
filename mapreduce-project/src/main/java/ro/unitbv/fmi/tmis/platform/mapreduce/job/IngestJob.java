package ro.unitbv.fmi.tmis.platform.mapreduce.job;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.conf.Configured;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.JobID;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.apache.hadoop.mapreduce.lib.input.TextInputFormat;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;
import org.apache.hadoop.mapreduce.lib.output.TextOutputFormat;
import org.apache.hadoop.util.Tool;
import org.apache.hadoop.util.ToolRunner;

import ro.unitbv.fmi.tmis.platform.mapreduce.map.ExtractDataMap;

public class IngestJob extends Configured implements Tool {

	@Override
	public int run(String[] args) throws Exception {
		Job job = Job.getInstance(getConf(), "Ingest Job");
		Configuration conf = job.getConfiguration();

		//conf.set("mapreduce.jobtracker.staging.root.dir", "/tmp");
		//conf.set("fs.defaultFS", "hdfs://namenode:8020");
		//conf.set("mapreduce.framework.name", "yarn");
		//conf.set("yarn.resourcemanager.adress", "localhost:8050");
		//conf.set("yarn.resourcemanager.adress", "localhost");

		job.setJarByClass(getClass());

		Path in = new Path(args[0]);
		String outPath = args[1] + "/" + args[2] + "/" + args[3];
		System.out.println("!!! Out path -> " + outPath);
		Path out = new Path(outPath);
		out.getFileSystem(conf).delete(out, true);
		// out.getFileSystem(conf).delete(out, true);
		FileInputFormat.setInputPaths(job, in);
		FileOutputFormat.setOutputPath(job, out);

		job.setMapperClass(ExtractDataMap.class);
		// job.setReducerClass(WordCountReducer.class);

		job.setInputFormatClass(TextInputFormat.class);
		job.setOutputFormatClass(TextOutputFormat.class);

		job.setMapOutputKeyClass(Text.class);
		job.setMapOutputValueClass(Text.class);
		// job.setOutputKeyClass(Text.class);
		// job.setOutputValueClass(IntWritable.class);
		job.setNumReduceTasks(0);

		int status = job.waitForCompletion(true) ? 0 : 1;
		JobID jobID = job.getJobID();
		System.out.println(jobID.getJtIdentifier());
		System.out.println(job.getJobState().toString());
		System.out.println(job.getStatus().toString());

		return status;
	}

	public static void main(String[] args) {
		int result = 0;
		try {
			String vars[] = { "/user/root/input", "/user/root/output",
					"test-region", "prec" };

			result = ToolRunner.run(new Configuration(), new IngestJob(), args);
		} catch (Exception e) {
			e.printStackTrace();
		}
		System.exit(result);
	}

}

package ro.unitbv.fmi.tmis.platform.mapreduce.model;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.apache.hadoop.io.DoubleWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.io.Writable;

public class Precipitation implements Writable {
	private DoubleWritable lat, lon, prec;
	private Text time;

	public Precipitation(Date time, double lat, double lon, double prec) {
		this.lat = new DoubleWritable(lat);
		this.lon = new DoubleWritable(lon);
		this.prec = new DoubleWritable(prec);

		DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
		this.time = new Text(dateFormat.format(time));
	}

	@Override
	public void write(DataOutput out) throws IOException {
		lat.write(out);
		lon.write(out);
		prec.write(out);
		time.write(out);
	}

	@Override
	public void readFields(DataInput in) throws IOException {
		lat.readFields(in);
		lon.readFields(in);
		prec.readFields(in);
		time.readFields(in);
	}

	public DoubleWritable getLat() {
		return lat;
	}

	public void setLat(DoubleWritable lat) {
		this.lat = lat;
	}

	public DoubleWritable getLon() {
		return lon;
	}

	public void setLon(DoubleWritable lon) {
		this.lon = lon;
	}

	public DoubleWritable getPrec() {
		return prec;
	}

	public void setPrec(DoubleWritable prec) {
		this.prec = prec;
	}

	public Text getTime() {
		return time;
	}

	public void setTime(Text time) {
		this.time = time;
	}

	@Override
	public String toString() {
		return time.toString() + "," + lat + "," + lon + "," + prec;
	}
}

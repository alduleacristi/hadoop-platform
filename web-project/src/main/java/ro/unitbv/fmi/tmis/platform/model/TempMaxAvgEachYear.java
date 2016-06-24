package ro.unitbv.fmi.tmis.platform.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.validation.constraints.NotNull;

@Entity(name = "temp_max_avg_each_year")
public class TempMaxAvgEachYear {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long idAvg;
	private int year, month;
	private double avg, max;

	@ManyToOne
	@JoinColumn(name = "idRegion")
	@NotNull
	private Region region;

	public TempMaxAvgEachYear() {

	}

	public TempMaxAvgEachYear(int year, int month, double avg, double max,
			Region region) {
		super();
		this.year = year;
		this.month = month;
		this.avg = avg;
		this.region = region;
		this.max = max;
	}

	public long getIdAvg() {
		return idAvg;
	}

	public void setIdAvg(long idAvg) {
		this.idAvg = idAvg;
	}

	public int getYear() {
		return year;
	}

	public void setYear(int year) {
		this.year = year;
	}

	public int getMonth() {
		return month;
	}

	public void setMonth(int month) {
		this.month = month;
	}

	public double getAvg() {
		return avg;
	}

	public void setAvg(double avg) {
		this.avg = avg;
	}

	public Region getRegion() {
		return region;
	}

	public void setRegion(Region region) {
		this.region = region;
	}

	public double getMax() {
		return max;
	}

	public void setMax(double max) {
		this.max = max;
	}
}

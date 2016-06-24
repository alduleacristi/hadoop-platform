package ro.unitbv.fmi.tmis.platform.dto;

public class TempMinAvgEachYearDTO {
	private long regionId;
	private int year, month;
	private double avg, min;

	public TempMinAvgEachYearDTO(long regionId, int year, int month,
			double avg, double min) {
		super();
		this.regionId = regionId;
		this.year = year;
		this.month = month;
		this.avg = avg;
		this.min = min;
	}

	public long getRegionId() {
		return regionId;
	}

	public void setRegionId(long regionId) {
		this.regionId = regionId;
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

	public double getMin() {
		return min;
	}

	public void setMin(double min) {
		this.min = min;
	}

}

package ro.unitbv.fmi.tmis.platform.netcdf.utils;

public enum DataType {
	PRECIPITATION("precipitation"), MAX_TEMP("maxTemp"), MIN_TEMP("minTemp");

	DataType(String type) {
		this.type = type;
	}

	private String type;

	public String getType() {
		return type;
	}
}

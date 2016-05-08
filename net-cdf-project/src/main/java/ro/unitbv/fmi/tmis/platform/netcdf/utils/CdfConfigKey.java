package ro.unitbv.fmi.tmis.platform.netcdf.utils;

public enum CdfConfigKey {
	CDF_FILE_PREC_IN_PATH("cdf.prec.in.path"), CDF_FILE_PREC_OUT_PATH(
			"cdf.prec.out.path"), CDF_FILE_MAX_TEMP_IN_PATH(
			"cdf.max.temp.in.path"), CDF_FILE_MAX_TEMP_OUT_PATH(
			"cdf.max.temp.out.path"), CDF_FILE_MIN_TEMP_IN_PATH(
			"cdf.min.temp.in.path"), CDF_FILE_MIN_TEMP_OUT_PATH(
			"cdf.min.temp.out.path");

	CdfConfigKey(String keyValue) {
		this.keyValue = keyValue;
	}

	private String keyValue;

	public String getKeyValue() {
		return keyValue;
	}

}

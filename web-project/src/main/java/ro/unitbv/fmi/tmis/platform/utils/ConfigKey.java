package ro.unitbv.fmi.tmis.platform.utils;

public enum ConfigKey {
	TURISM_REGIONS_NR_OF_YEARS("turismRegions.numberOfYears"), HDFS_HOST(
			"hdfs.host"), HDFS_PORT("hdfs.port"), HDFS_USER("hdfs.user"), HDFS_PRECIPITATIONS_PATH(
			"hdfs.precipitations.path"), HDFS_MAX_TEMP_PATH(
			"hdfs.max.temp.path"), HDFS_MIN_TEMP_PATH("hdfs.min.temp.path"), YARN_HOST(
			"yarn.host"), YARN_PORT("yarn.port");
	;

	ConfigKey(String keyValue) {
		this.keyValue = keyValue;
	}

	private String keyValue;

	public String getKeyValue() {
		return keyValue;
	}

}

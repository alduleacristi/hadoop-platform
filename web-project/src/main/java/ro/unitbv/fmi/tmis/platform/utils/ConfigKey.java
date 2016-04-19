package ro.unitbv.fmi.tmis.platform.utils;

public enum ConfigKey {
	TURISM_REGIONS_NR_OF_YEARS("turismRegions.numberOfYears"), HDFS_HOST(
			"hdfs.host"), HDFS_PORT("hdfs.port"), HDFS_USER("hdfs.user");

	ConfigKey(String keyValue) {
		this.keyValue = keyValue;
	}

	private String keyValue;

	public String getKeyValue() {
		return keyValue;
	}

}

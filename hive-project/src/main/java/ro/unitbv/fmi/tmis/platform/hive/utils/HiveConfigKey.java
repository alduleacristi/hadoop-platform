package ro.unitbv.fmi.tmis.platform.hive.utils;

public enum HiveConfigKey {
	HIVE_HOST("hive.host"), HIVE_PORT("hive.port"), HIVE_USER("hdfs.user"), HIVE_PASS(
			""), HIVE_DRIVER("hive.driver");

	HiveConfigKey(String keyValue) {
		this.keyValue = keyValue;
	}

	private String keyValue;

	public String getKeyValue() {
		return keyValue;
	}

}

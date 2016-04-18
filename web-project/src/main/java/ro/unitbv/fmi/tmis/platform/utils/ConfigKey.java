package ro.unitbv.fmi.tmis.platform.utils;

public enum ConfigKey {
	TURISM_REGIONS_NR_OF_YEARS("turismRegions.numberOfYears");

	ConfigKey(String keyValue) {
		this.keyValue = keyValue;
	}

	private String keyValue;

	public String getKeyValue() {
		return keyValue;
	}
	
	
}

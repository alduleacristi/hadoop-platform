package ro.unitbv.fmi.tmis.platform.utils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import javax.annotation.PostConstruct;
import javax.inject.Named;
import javax.inject.Singleton;

@Singleton
@Named
public class Constants {
	private Map<Double, Integer> lats, lons;

	private void readLatitudes() throws IOException {
		InputStream in = this.getClass().getClassLoader()
				.getResourceAsStream("meta-lat.csv");

		try (BufferedReader bf = new BufferedReader(new InputStreamReader(in))) {
			String line = null;
			while ((line = bf.readLine()) != null) {
				String parts[] = line.split(",");
				lats.put(Double.parseDouble(parts[1]),
						Integer.parseInt(parts[0]));
			}

		}
	}

	private void readLongitudes() throws IOException {
		InputStream in = this.getClass().getClassLoader()
				.getResourceAsStream("meta-lon.csv");

		try (BufferedReader bf = new BufferedReader(new InputStreamReader(in))) {
			String line = null;
			while ((line = bf.readLine()) != null) {
				String parts[] = line.split(",");
				lons.put(Double.parseDouble(parts[1]),
						Integer.parseInt(parts[0]));
			}

		}
	}

	@PostConstruct
	private void readFiles() throws IOException {
		lats = new HashMap<Double, Integer>();
		lons = new HashMap<Double, Integer>();

		readLatitudes();
		readLongitudes();
	}

	public int getIdOfLatitude(Double lat) {
		return lats.get(lat);
	}

	public Integer getIdOfLongitude(Double lon) {
		return lons.get(lon);
	}
}

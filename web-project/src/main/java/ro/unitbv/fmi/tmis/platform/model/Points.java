package ro.unitbv.fmi.tmis.platform.model;

import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;

public class Points {
	public static class Point {
		public Point() {

		}

		public Point(double latitude, double longitude) {
			this.latitude = latitude;
			this.longitude = longitude;
		}

		private double latitude;
		private double longitude;

		public double getLatitude() {
			return latitude;
		}

		public void setLatitude(double latitude) {
			this.latitude = latitude;
		}

		public double getLongitude() {
			return longitude;
		}

		public void setLongitude(double longitude) {
			this.longitude = longitude;
		}
	}
	
	public Points() {
		points = new ArrayList<>();
	}

	@JsonProperty
	private List<Point> points;

	public List<Point> getPoints() {
		return points;
	}

	public void setPoints(List<Point> points) {
		this.points = points;
	}

}

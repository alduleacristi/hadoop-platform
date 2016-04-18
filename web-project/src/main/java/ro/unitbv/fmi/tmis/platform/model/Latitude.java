package ro.unitbv.fmi.tmis.platform.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class Latitude {
	public Latitude(){
		
	}
	
	public Latitude(long id, double latitude){
		this.id = id;
		this.latitude = latitude;
	}
	
	private long id;
	private double latitude;
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	public double getLatitude() {
		return latitude;
	}
	public void setLatitude(double latitude) {
		this.latitude = latitude;
	}
	
	
}

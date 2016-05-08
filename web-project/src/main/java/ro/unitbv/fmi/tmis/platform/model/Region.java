package ro.unitbv.fmi.tmis.platform.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class Region {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(unique = true)
	private String name;
	@Column
	private double minLat;
	@Column
	private double maxLat;
	@Column
	private double minLon;
	@Column
	private double maxLon;
	@Column
	private int startYear;
	@Column
	private int endYear;

	/*
	 * @Column(name = "points", columnDefinition = "json")
	 * 
	 * @Convert(converter = PointsConverter.class)
	 * 
	 * @JsonProperty private Points coords;
	 */

	// private Country country;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public double getMinLat() {
		return minLat;
	}

	public void setMinLat(double minLat) {
		this.minLat = minLat;
	}

	public double getMaxLat() {
		return maxLat;
	}

	public void setMaxLat(double maxLat) {
		this.maxLat = maxLat;
	}

	public double getMinLon() {
		return minLon;
	}

	public void setMinLon(double minLon) {
		this.minLon = minLon;
	}

	public double getMaxLon() {
		return maxLon;
	}

	public void setMaxLon(double maxLon) {
		this.maxLon = maxLon;
	}

	public int getStartYear() {
		return startYear;
	}

	public void setStartYear(int startYear) {
		this.startYear = startYear;
	}

	public int getEndYear() {
		return endYear;
	}

	public void setEndYear(int endYear) {
		this.endYear = endYear;
	}

	/*
	 * public Points getCoords() { return coords; }
	 * 
	 * public void setCoords(Points coords) { this.coords = coords; }
	 */

	/*
	 * @ManyToOne
	 * 
	 * @JoinColumn(name = "id_country") public Country getCountry() { return
	 * country; }
	 * 
	 * public void setCountry(Country country) { this.country = country; }
	 */
}

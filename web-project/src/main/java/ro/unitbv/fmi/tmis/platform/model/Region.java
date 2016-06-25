package ro.unitbv.fmi.tmis.platform.model;

import java.util.List;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;

import com.fasterxml.jackson.annotation.JsonManagedReference;

@Entity
public class Region {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "idRegion")
	private Long idRegion;

	@Column(unique = true)
	private String name;
	@Column
	private Double minLat;
	@Column
	private Double maxLat;
	@Column
	private Double minLon;
	@Column
	private Double maxLon;
	@Column
	private Integer startYear;
	@Column
	private Integer endYear;
	@Column
	private String type;

	@OneToMany(mappedBy = "region", fetch = FetchType.EAGER)
	@JsonManagedReference
	private List<PrecipitationAvgEachYear> precipitationAvgEachYear;

	@ManyToMany(fetch = FetchType.EAGER)
	@JoinTable(name = "region_query", joinColumns = @JoinColumn(name = "idRegion", referencedColumnName = "idRegion"), inverseJoinColumns = @JoinColumn(name = "idQuery", referencedColumnName = "idQuery"))
	@JsonManagedReference
	private Set<Query> querys;

	/*
	 * @Column(name = "points", columnDefinition = "json")
	 * 
	 * @Convert(converter = PointsConverter.class)
	 * 
	 * @JsonProperty private Points coords;
	 */

	// private Country country;

	public Long getIdRegion() {
		return idRegion;
	}

	public void setIdRegion(Long idRegion) {
		this.idRegion = idRegion;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public List<PrecipitationAvgEachYear> getPrecipitationAvgEachYear() {
		return precipitationAvgEachYear;
	}

	public void setPrecipitationAvgEachYear(
			List<PrecipitationAvgEachYear> precipitationAvgEachYear) {
		this.precipitationAvgEachYear = precipitationAvgEachYear;
	}

	public Set<Query> getQuerys() {
		return querys;
	}

	public void setQuerys(Set<Query> querys) {
		this.querys = querys;
	}

	public Double getMinLat() {
		return minLat;
	}

	public void setMinLat(Double minLat) {
		this.minLat = minLat;
	}

	public Double getMaxLat() {
		return maxLat;
	}

	public void setMaxLat(Double maxLat) {
		this.maxLat = maxLat;
	}

	public Double getMinLon() {
		return minLon;
	}

	public void setMinLon(Double minLon) {
		this.minLon = minLon;
	}

	public Double getMaxLon() {
		return maxLon;
	}

	public void setMaxLon(Double maxLon) {
		this.maxLon = maxLon;
	}

	public Integer getStartYear() {
		return startYear;
	}

	public void setStartYear(Integer startYear) {
		this.startYear = startYear;
	}

	public Integer getEndYear() {
		return endYear;
	}

	public void setEndYear(Integer endYear) {
		this.endYear = endYear;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
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

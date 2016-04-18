package ro.unitbv.fmi.tmis.platform.model;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.Convert;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import com.fasterxml.jackson.annotation.JsonProperty;

import ro.unitbv.fmi.tmis.platform.converter.PointsConverter;

@Entity
public class Region {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column
	private String name;

	@Column(name = "points", columnDefinition = "json")
	@Convert(converter = PointsConverter.class)
	@JsonProperty
	private Points coords;

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

	public Points getCoords() {
		return coords;
	}

	public void setCoords(Points coords) {
		this.coords = coords;
	}

	/*
	 * @ManyToOne
	 * 
	 * @JoinColumn(name = "id_country") public Country getCountry() { return
	 * country; }
	 * 
	 * public void setCountry(Country country) { this.country = country; }
	 */
}

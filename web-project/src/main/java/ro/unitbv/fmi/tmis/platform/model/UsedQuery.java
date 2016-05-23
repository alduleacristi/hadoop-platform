package ro.unitbv.fmi.tmis.platform.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

@Entity(name = "used_query")
public class UsedQuery {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id_used_query")
	private long idUsedQuery;

	@Column(name = "running")
	private Boolean running;
	@Column(name = "successed")
	private Boolean successed;
	@Column(name = "time_duration")
	private Long timeDuration;

	@ManyToOne
	@JoinColumn(name = "id_region")
	private Region region;

	@ManyToOne
	@JoinColumn(name = "id_query")
	private Query query;

	public Long getTimeDuration() {
		return timeDuration;
	}

	public void setTimeDuration(Long timeDuration) {
		this.timeDuration = timeDuration;
	}

	public Boolean getRunning() {
		return running;
	}

	public void setRunning(Boolean running) {
		this.running = running;
	}

	public Boolean getSuccessed() {
		return successed;
	}

	public void setSuccessed(Boolean successed) {
		this.successed = successed;
	}

	public Region getRegion() {
		return region;
	}

	public void setRegion(Region region) {
		this.region = region;
	}

	public Query getQuery() {
		return query;
	}

	public void setQuery(Query query) {
		this.query = query;
	}

	public long getIdUsedQuery() {
		return idUsedQuery;
	}

	public void setIdUsedQuery(long idUsedQuery) {
		this.idUsedQuery = idUsedQuery;
	}
}

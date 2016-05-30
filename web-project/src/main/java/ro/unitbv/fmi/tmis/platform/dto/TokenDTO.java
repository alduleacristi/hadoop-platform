package ro.unitbv.fmi.tmis.platform.dto;

import java.util.Date;
import java.util.Set;

import ro.unitbv.fmi.tmis.platform.model.Role;

public class TokenDTO {
	private String clientId;
	private Set<String> roles;
	private Date generatedDate;

	public String getClientId() {
		return clientId;
	}

	public void setClientId(String clientId) {
		this.clientId = clientId;
	}

	public Date getGeneratedDate() {
		return generatedDate;
	}

	public void setGeneratedDate(Date generatedDate) {
		this.generatedDate = generatedDate;
	}

	public Set<String> getRoles() {
		return roles;
	}

	public void setRoles(Set<String> roles) {
		this.roles = roles;
	}
}

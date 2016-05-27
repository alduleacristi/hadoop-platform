package ro.unitbv.fmi.tmis.platform.dto;

import java.util.Date;
import java.util.Set;

import ro.unitbv.fmi.tmis.platform.model.Role;

public class TokenDTO {
	private String clientId;
	private Set<Role> roles;
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

	public Set<Role> getRoles() {
		return roles;
	}

	public void setRoles(Set<Role> roles) {
		this.roles = roles;
	}
}

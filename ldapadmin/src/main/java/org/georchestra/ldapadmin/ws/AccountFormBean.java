/**
 * 
 */
package org.georchestra.ldapadmin.ws;

import java.io.Serializable;

/**
 * This model maintains the account form data.
 * 
 * @author Mauricio Pazos
 *
 */
public class AccountFormBean {

	public String uid;
	public String name;
	public String org;
	public String role;
	public String geographicArea;
	public String email;
	public String phone;
	public String details;
	public String getUid() {
		return uid;
	}
	public void setUid(String uid) {
		this.uid = uid;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getOrg() {
		return org;
	}
	public void setOrg(String org) {
		this.org = org;
	}
	public String getRole() {
		return role;
	}
	public void setRole(String role) {
		this.role = role;
	}
	public String getGeographicArea() {
		return geographicArea;
	}
	public void setGeographicArea(String geographicArea) {
		this.geographicArea = geographicArea;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getPhone() {
		return phone;
	}
	public void setPhone(String phone) {
		this.phone = phone;
	}
	public String getDetails() {
		return details;
	}
	public void setDetails(String details) {
		this.details = details;
	}
	
	@Override
	public String toString() {
		return "AccountFormBean [uid=" + uid + ", name=" + name + ", org="
				+ org + ", role=" + role + ", geographicArea=" + geographicArea
				+ ", email=" + email + ", phone=" + phone + ", details="
				+ details + "]";
	}
	
	


}

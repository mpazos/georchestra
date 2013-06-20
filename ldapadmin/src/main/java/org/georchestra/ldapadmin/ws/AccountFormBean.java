/**
 * 
 */
package org.georchestra.ldapadmin.ws;

import java.util.List;


/**
 * This model maintains the account form data.
 * 
 * @author Mauricio Pazos
 *
 */
public class AccountFormBean {

	private String uid;
	private String name;
	private String org;
	private String role;
	private String geographicArea;
	private String email;
	private String phone;
	private String details;
	private String password;
	private String confirmPassword;
	private List<String> roleList;
	
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
	
	public void setRoleList(List<String> roleList) {
		
		this.roleList = roleList;
	}
	
	public List<String>  getRoleList() {
		
		return this.roleList;
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

	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getConfirmPassword() {
		return confirmPassword;
	}
	public void setConfirmPassword(String confirmPassword) {
		this.confirmPassword = confirmPassword;
	}
	
	@Override
	public String toString() {
		return "AccountFormBean [uid=" + uid + ", name=" + name + ", org="
				+ org + ", role=" + role + ", geographicArea=" + geographicArea
				+ ", email=" + email + ", phone=" + phone + ", details="
				+ details + ", password=" + password + ", confirmPassword="
				+ confirmPassword + "]";
	}
	
}

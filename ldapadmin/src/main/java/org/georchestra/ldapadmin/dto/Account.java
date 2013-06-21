/**
 * 
 */
package org.georchestra.ldapadmin.dto;

import java.io.Serializable;

/**
 * Account this is a Data transfer Object. 
 *  
 *  
 * @author Mauricio Pazos
 *
 */
public class Account implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -8022496448991887664L;
	
	public String uid; //
	public String name; // cn
	public String org; // o
	public String role; // 
	public String geographicArea; // TODO new field in ldap is required
	public String email;// mail
	public String phone;// telephoneNumber 
	public String details; // description
	private String password; // userPassword

	private String newPassword;


	
	@Override
	public String toString() {
		return "Account [uid=" + uid + ", name=" + name + ", org=" + org
				+ ", role=" + role + ", geographicArea=" + geographicArea
				+ ", email=" + email + ", phone=" + phone + ", details="
				+ details + ", password=" + password + ", newPassword="
				+ newPassword + "]";
	}
	
	public void setUid(String uid) {
		this.uid = uid;
	}
	public String getUid(){
		return this.uid;
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

	public void setPassword(String password) {
		this.password = password;
	}

	public String getPassword() {
		return password;
	}

	public void setNewPassword(String newPassword) {
		this.newPassword = newPassword;
		
	}
	public String getNewPassword() {
		return this.newPassword;
		
	}
	
	
	
	
	
}

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
	
	
	// main data
	private String uid; // uid
	private String commonName; // cn
	private String surname; // sn
	private String org; // o
	private String role; // 
	private String geographicArea; // TODO new field in ldap is required
	private String email;// mail
	private String phone;// telephoneNumber 
	private String details; // description
	private String password; // userPassword
	private String newPassword;

	// user details
	// sn, givenName, o, title, postalAddress, postalCode, registeredAddress, postOfficeBox, physicalDeliveryOfficeName
	private String givenName; // givenName
	private String title; // title
	private String postalAddress; //postalAddress
	private String postalCode; // postalCode
	private String registeredAddress; //registeredAddress 
	private String postOfficeBox; // postOfficeBox
	private String physicalDeliveryOfficeName; //physicalDeliveryOfficeName
	
	@Override
	public String toString() {
		return "Account [uid=" + uid + ", name=" + commonName + ", org=" + org
				+ ", role=" + role + ", geographicArea=" + geographicArea
				+ ", email=" + email + ", phone=" + phone + ", details="
				+ details + ", password=" + password + ", newPassword="
				+ newPassword + ", surname=" + surname + ", givenName="
				+ givenName + ", title=" + title + ", postalAddress="
				+ postalAddress + ", postalCode=" + postalCode
				+ ", registeredAddress=" + registeredAddress
				+ ", postOfficeBox=" + postOfficeBox
				+ ", physicalDeliveryOfficeName=" + physicalDeliveryOfficeName
				+ "]";
	}
	
	public void setUid(String uid) {
		this.uid = uid;
	}
	public String getUid(){
		return this.uid;
	}
	
	public String getCommonName() {
		return commonName;
	}
	public void setCommonName(String name) {
		this.commonName = name;
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

	public String getSurname() {
		return surname;
	}

	public void setSurname(String surname) {
		this.surname = surname;
	}

	public String getGivenName() {
		return givenName;
	}

	public void setGivenName(String givenName) {
		this.givenName = givenName;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getPostalAddress() {
		return postalAddress;
	}

	public void setPostalAddress(String postalAddress) {
		this.postalAddress = postalAddress;
	}

	public String getPostalCode() {
		return postalCode;
	}

	public void setPostalCode(String postalCode) {
		this.postalCode = postalCode;
	}

	public String getRegisteredAddress() {
		return registeredAddress;
	}

	public void setRegisteredAddress(String registeredAddress) {
		this.registeredAddress = registeredAddress;
	}

	public String getPostOfficeBox() {
		return postOfficeBox;
	}

	public void setPostOfficeBox(String postOfficeBox) {
		this.postOfficeBox = postOfficeBox;
	}

	public String getPhysicalDeliveryOfficeName() {
		return physicalDeliveryOfficeName;
	}

	public void setPhysicalDeliveryOfficeName(String physicalDeliveryOfficeName) {
		this.physicalDeliveryOfficeName = physicalDeliveryOfficeName;
	}
}

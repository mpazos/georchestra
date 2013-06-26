package org.georchestra.ldapadmin.dto;

/**
 * Account data transfer object.
 * 
 * @author Mauricio Pazos
 *
 */
public interface Account {

	public abstract void setUid(String uid);

	public abstract String getUid();

	public abstract String getCommonName();

	public abstract void setCommonName(String name);

	public abstract String getOrg();

	public abstract void setOrg(String org);

	public abstract String getRole();

	public abstract void setRole(String role);

	public abstract String getEmail();

	public abstract void setEmail(String email);

	public abstract String getPhone();

	public abstract void setPhone(String phone);

	public abstract String getDetails();

	public abstract void setDetails(String details);

	public abstract void setPassword(String password);

	public abstract String getPassword();

	public abstract void setNewPassword(String newPassword);

	public abstract String getNewPassword();

	public abstract String getSurname();

	public abstract void setSurname(String surname);

	public abstract String getGivenName();

	public abstract void setGivenName(String givenName);

	public abstract String getTitle();

	public abstract void setTitle(String title);

	public abstract String getPostalAddress();

	public abstract void setPostalAddress(String postalAddress);

	public abstract String getPostalCode();

	public abstract void setPostalCode(String postalCode);

	public abstract String getRegisteredAddress();

	public abstract void setRegisteredAddress(String registeredAddress);

	public abstract String getPostOfficeBox();

	public abstract void setPostOfficeBox(String postOfficeBox);

	public abstract String getPhysicalDeliveryOfficeName();

	public abstract void setPhysicalDeliveryOfficeName(
			String physicalDeliveryOfficeName);

}
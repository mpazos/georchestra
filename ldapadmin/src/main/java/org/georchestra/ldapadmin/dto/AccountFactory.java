package org.georchestra.ldapadmin.dto;

import java.util.UUID;

public class AccountFactory {

	private AccountFactory(){}
	
	public static Account createMock(){
		
		Account a = new AccountImpl();
		
		a.setUid("testeditor");
		
		a.setDetails("editor");
		a.setSurname("surname");
		a.setOrg("georchestra");

		a.setGivenName("given name");
		a.setPhysicalDeliveryOfficeName("Physical Delivery Office");
		a.setPostalAddress("Postal Address");
		a.setPostalCode("Postal Code");
		a.setPostOfficeBox("PostOffice Box");
		a.setRegisteredAddress("Registered Address");
		a.setTitle("Title");

		return a;

	}
//	
//	public static Account createDefault(){
//		
//		Account a = new AccountImpl();
//		
//		a.setUid("");
//		
//		a.setDetails("");
//		a.setSurname("");
//		a.setOrg("");
//
//		a.setGivenName("");
//		a.setPhysicalDeliveryOfficeName("");
//		a.setPostalAddress("");
//		a.setPostalCode("");
//		a.setPostOfficeBox("");
//		a.setRegisteredAddress("");
//		a.setTitle("");
//
//		return a;
//
//	}

	public static Account createDetails(
			String uid, 
			String givenName,
			String org, 
			String physicalDeliveryOfficeName,
			String postalAddress, 
			String postalCode, 
			String postOfficeBox,
			String registeredAddress, 
			String surname, 
			String title) {
		
		Account a = new AccountImpl();
		
		a.setUid(uid);
		
		a.setSurname(surname);
		a.setOrg(org);

		a.setGivenName(givenName);
		a.setPhysicalDeliveryOfficeName(physicalDeliveryOfficeName);
		a.setPostalAddress(postalAddress);
		a.setPostalCode(postalCode);
		a.setPostOfficeBox(postOfficeBox);
		
		a.setRegisteredAddress(registeredAddress);
		a.setTitle(title);

		return a;
	}

	public static Account create(final String uid) {
		Account a = new AccountImpl();
		
		a.setUid(uid);
		
		return a;
	}

	public static Account create(
			String password,
			String firstName, String surname, 
			String email, String phone,
			String org, String details) {
		
		Account account = new AccountImpl();
		
		account.setUid(UUID.randomUUID().toString());
		account.setPassword(password);

		account.setGivenName(firstName);
		account.setSurname(surname);

		account.setCommonName(firstName + " " + surname);

		account.setEmail(email);
		account.setPhone(phone);
		account.setOrg(org);
		account.setDetails(details);
		
		return account;
	}

	public static Account create(
			String role, 
			String uid,
			String cn, 
			String sn,
			String givenName, 
			String mail,
			String o, 
			String title,
			String postalAddress, 
			String postalCode,
			String registeredAddress, 
			String postOfficeBox,
			String physicalDeliveryOfficeName) {
		
		Account account = new AccountImpl();
		
		account.setRole(role);
		
		account.setUid(uid);
		
		account.setCommonName(cn);
		account.setSurname(sn);
		account.setGivenName(givenName);

		account.setEmail(mail);
		account.setOrg(o);
		account.setTitle(title);
		
		account.setPostalAddress(postalAddress);
		account.setPostalCode(postalCode);
		account.setRegisteredAddress(registeredAddress);
		account.setPostOfficeBox(postOfficeBox);
		account.setPhysicalDeliveryOfficeName(physicalDeliveryOfficeName);
		
		
		return account;
	}

}

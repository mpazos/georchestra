package org.georchestra.ldapadmin.dto;

public class AccountFactory {

	private AccountFactory(){}
	
	public static Account createMock(){
		
		Account a = new Account();
		
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
	
	public static Account createDefault(){
		
		Account a = new Account();
		
		a.setUid("");
		
		a.setDetails("");
		a.setSurname("");
		a.setOrg("");

		a.setGivenName("");
		a.setPhysicalDeliveryOfficeName("");
		a.setPostalAddress("");
		a.setPostalCode("");
		a.setPostOfficeBox("");
		a.setRegisteredAddress("");
		a.setTitle("");

		return a;

	}

}

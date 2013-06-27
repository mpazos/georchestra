package org.georchestra.ldapadmin.dto;


/**
 * Account factory. 
 * 
 * <p>
 * This factory provide the convenient account object (data transfer object) used by this application.
 * </p>
 * 
 * @author Mauricio Pazos
 *
 */
public class AccountFactory {

	private AccountFactory(){}

	public static Account createDetails(
			String uid, 
			String givenName,
			String surname, 
			String org, 
			String physicalDeliveryOfficeName,
			String postalAddress, 
			String postalCode, 
			String postOfficeBox,
			String registeredAddress, 
			String title) {
		
		Account a = new AccountImpl();
		
		a.setUid(uid);
		
		a.setGivenName(givenName);
		a.setSurname(surname);
		a.setCommonName(formatCommonName(givenName, surname) );

		a.setOrg(org);

		a.setPhysicalDeliveryOfficeName(physicalDeliveryOfficeName);
		a.setPostalAddress(postalAddress);
		a.setPostalCode(postalCode);
		a.setPostOfficeBox(postOfficeBox);
		
		a.setRegisteredAddress(registeredAddress);
		a.setTitle(title);

		return a;
	}

	private static String formatCommonName(String givenName, String surname) {
		return givenName + " " + surname;
	}

	public static Account create(final String uid) {
		Account a = new AccountImpl();
		
		a.setUid(uid);
		
		return a;
	}

	public static Account create(
			String uid,
			String password,
			String firstName, String surname, 
			String email, String phone,
			String org, String details) {
		
		Account account = new AccountImpl();
		
		account.setUid(uid);
		account.setPassword(password);

		account.setGivenName(firstName);
		account.setSurname(surname);

		account.setCommonName(formatCommonName(firstName ,surname));

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

	public static Account create(
			String role, 
			String uid,
			String cn, 
			String surname,
			String givenName, 
			String email,
			String org, 
			String title,
			String phone, 
			String description,
			String pwd, 
			String postalAddress,
			String postalCode, 
			String registeredAddress ,
			String postOfficeBox, 
			String physicalDeliveryOfficeName) {
		
		Account a = new AccountImpl();
		
		a.setRole(role);
		
		a.setUid(uid);
		a.setCommonName(cn);
		a.setGivenName(givenName);
		a.setSurname(surname);
		a.setEmail(email);

		a.setOrg(org);
		a.setTitle(title);

		a.setPhone(phone);
		a.setDetails(description);
		
		a.setPassword(pwd);

		a.setPostalAddress(postalAddress);
		a.setPostalCode(postalCode);
		a.setRegisteredAddress(registeredAddress);
		a.setPostOfficeBox(postOfficeBox);
		a.setPhysicalDeliveryOfficeName(physicalDeliveryOfficeName);
		
		
		return a;
	}

}

/**
 * 
 */
package org.georchestra.ldapadmin.ds;

import java.util.Date;
import java.util.List;

import javax.naming.Name;

import org.georchestra.ldapadmin.dto.Account;
import org.georchestra.ldapadmin.dto.AccountFactory;
import org.springframework.ldap.core.ContextMapper;
import org.springframework.ldap.core.DirContextAdapter;
import org.springframework.ldap.core.DirContextOperations;
import org.springframework.ldap.core.DistinguishedName;
import org.springframework.ldap.core.LdapTemplate;
import org.springframework.ldap.filter.EqualsFilter;

/**
 * This class is responsible of maintaining the user accounts (CRUD operations). 
 * 
 * @author Mauricio Pazos
 */
public final class AccountDaoImpl implements AccountDao{
	
	private LdapTemplate ldapTemplate;
	private GroupDao groupDao;

	public LdapTemplate getLdapTemplate() {
		return ldapTemplate;
	}

	public void setLdapTemplate(LdapTemplate ldapTemplate) {
		this.ldapTemplate = ldapTemplate;
	}

	public GroupDao getGroupDao() {
		return groupDao;
	}

	public void setGroupDao(GroupDao groupDao) {
		this.groupDao = groupDao;
	}
	
	/**
	 * Returns all users' account.
	 * 
	 * @return List of accounts
	 */
	@Override
	public List<Account> findAll() throws DataServiceException{
		
		EqualsFilter filter = new EqualsFilter("objectClass", "person");
		return ldapTemplate.search(DistinguishedName.EMPTY_PATH, filter.encode(), getContextMapper());
	}
	

	@Override
	public void create(final Account account, final String groupID) throws DataServiceException, DuplicatedEmailException, RequiredFiedException{
	
		assert account != null;
		
		checkMandatoryFields(account);
		
		try {
			Name dn = buildDn( account.getUid() );

			DirContextAdapter context = new DirContextAdapter(dn);
			mapToContext(account, context);

			this.ldapTemplate.bind(dn, context, null);

			this.groupDao.addUser( groupID, account.getUid() );

		} catch (NotFoundException e) {
			throw new DataServiceException(e);
		}
	}



	@Override
	public void update(final Account account) throws DataServiceException, DuplicatedEmailException, RequiredFiedException{

		// checks mandatory fields
		if( account.getUid().length() == 0) {
			throw new RequiredFiedException("uid is required");
		}
		if( account.getSurname().length()== 0 ){
			throw new RequiredFiedException("surname is required");
		}
		if( account.getCommonName().length()== 0 ){
			throw new RequiredFiedException("common name is required");
		}
		if( account.getGivenName().length()== 0 ){
			throw new RequiredFiedException("given name is required");
		}
		
		 // update the entry in the ldap tree
		Name dn = buildDn(account.getUid());
		DirContextOperations context = ldapTemplate.lookupContext(dn);

		mapDetailsToContext(account, context);
		
		ldapTemplate.modifyAttributes(context);
	}


	@Override
	public void delete(final Account account) throws DataServiceException, NotFoundException{
		ldapTemplate.unbind(buildDn(account.getUid()));
	}

	@Override
	public Account findByUID(final String uid) throws DataServiceException, NotFoundException{

		DistinguishedName dn = buildDn(uid);
		Account a = (Account) ldapTemplate.lookup(dn, getContextMapper());
		
		if(a == null){
			throw new NotFoundException("There is not a user with this email: " + uid);
		}
		
		return  a;
		
	}

	@Override
	public Account findByEmail(final String email) throws DataServiceException, NotFoundException {

		DistinguishedName dn = new DistinguishedName();
		dn.add("email", email);
		dn.add("ou", "users");
		
		Account a = (Account) ldapTemplate.lookup(dn, getContextMapper());
		
		if(a == null){
			throw new NotFoundException("There is not a user with this email: " + email);
		}
		return  a;
	}
	
	
	private ContextMapper getContextMapper() {
		return new AccountContextMapper();
	}
	
	
	

	/**
	 * Create an ldap entry for the user 
	 * 
	 * @param uid user id
	 * @return
	 */
	private DistinguishedName buildDn(String  uid) {
		DistinguishedName dn = new DistinguishedName();
				
		dn.add("ou", "users");
		dn.add("uid", uid);
		
		return dn;
	}
	
	/**
	 * Checks that  mandatory fields are present in the {@link Account}
	 */
	private void checkMandatoryFields( Account a ) throws RequiredFiedException{

		// required by the account entry
		if( a.getUid().length() <= 0 ){
			throw new  RequiredFiedException("uid is requird");
		}
		
		// required field in Person object
		if( a.getGivenName().length() <= 0 ){
			throw new  RequiredFiedException("Given name (cn) is requird");
		}
		if( a.getSurname().length() <= 0){
			throw new RequiredFiedException("surname name (sn) is requird");
		}
		if( a.getPassword().length() <= 0){
			throw new RequiredFiedException("password is requird");
		}
		if( a.getEmail().length() <= 0){
			throw new RequiredFiedException("mail is requird");
		}
		
	}

	 		
	/**
	 * Maps the following the account object to the following LDAP entry schema:
	 *
	 * <pre>
	 * dn: uid=anUid,ou=users,dc=georchestra,dc=org
	 * sn: aSurname
	 * objectClass: organizationalPerson
	 * objectClass: person 
	 * objectClass: inetOrgPerson
	 * objectClass: top
	 * mail: aMail
	 * uid: anUid
	 * cn: aCommonName
	 * description: description
	 * userPassword: secret
	 * </pre>
	 * 
	 * 
	 * @param account
	 * @param context
	 * @param createEntry
	 */
	private void mapToContext(Account account, DirContextOperations context) {
		
		context.setAttributeValues("objectclass", new String[] { "top", "person", "organizationalPerson", "inetOrgPerson" });

		// person attributes
		context.setAttributeValue("sn", account.getSurname());

		context.setAttributeValue("cn", account.getCommonName());
		
		if( !isNullValue(account.getDetails())){
			context.setAttributeValue("description", account.getDetails());
		}

		if( !isNullValue(account.getPhone()) ){
			context.setAttributeValue("telephoneNumber", account.getPhone());
		}

		context.setAttributeValue("userPassword", account.getPassword());

		// organizationalPerson attributes
		// any attribute is set right now (when the account is created)
		
		// inetOrgPerson attributes
		if( !isNullValue(account.getGivenName()) ){
			context.setAttributeValue("givenName", account.getGivenName());
		}
		
		context.setAttributeValue("uid", account.getUid());

		context.setAttributeValue("mail", account.getEmail());
		
		// additional
		if( !isNullValue(account.getOrg()) ){
			context.setAttributeValue("o", account.getOrg());
		}

		if( !isNullValue(account.getTitle()) ){
			context.setAttributeValue("title", account.getTitle());
		}
		if( !isNullValue(account.getPostalAddress()) ){
			context.setAttributeValue("postalAddress", account.getPostalAddress());
		}
		if( !isNullValue(account.getPostalCode()) ){
			context.setAttributeValue("postalCode", account.getPostalCode());
		}
		if( !isNullValue(account.getRegisteredAddress()) ){
			context.setAttributeValue("registeredAddress", account.getRegisteredAddress());
		}
		if( !isNullValue(account.getPostOfficeBox()) ){
			context.setAttributeValue("postOfficeBox", account.getPostOfficeBox());
		}
		if( !isNullValue(account.getPhysicalDeliveryOfficeName()) ){
			context.setAttributeValue("physicalDeliveryOfficeName", account.getPhysicalDeliveryOfficeName());
		}
	}
	
	private void mapDetailsToContext(Account account, DirContextOperations context){
		
		context.setAttributeValues("objectclass", new String[] { "top", "person", "organizationalPerson", "inetOrgPerson" });

		// person attributes
		context.setAttributeValue("sn", account.getSurname());

		context.setAttributeValue("cn", account.getCommonName() );
		
		if( !isNullValue(account.getDetails())){
			context.setAttributeValue("description", account.getDetails());
		}

		if( !isNullValue(account.getPhone()) ){
			context.setAttributeValue("telephoneNumber", account.getPhone());
		}

		// organizationalPerson attributes
		// any attribute is set right now (when the account is created)
		
		// inetOrgPerson attributes
		if( !isNullValue(account.getGivenName()) ){
			context.setAttributeValue("givenName", account.getGivenName());
		}
		
		// additional
		if( !isNullValue(account.getOrg()) ){
			context.setAttributeValue("o", account.getOrg());
		}

		if( !isNullValue(account.getTitle()) ){
			context.setAttributeValue("title", account.getTitle());
		}
		if( !isNullValue(account.getPostalAddress()) ){
			context.setAttributeValue("postalAddress", account.getPostalAddress());
		}
		if( !isNullValue(account.getPostalCode()) ){
			context.setAttributeValue("postalCode", account.getPostalCode());
		}
		if( !isNullValue(account.getRegisteredAddress()) ){
			context.setAttributeValue("registeredAddress", account.getRegisteredAddress());
		}
		if( !isNullValue(account.getPostOfficeBox()) ){
			context.setAttributeValue("postOfficeBox", account.getPostOfficeBox());
		}
		if( !isNullValue(account.getPhysicalDeliveryOfficeName()) ){
			context.setAttributeValue("physicalDeliveryOfficeName", account.getPhysicalDeliveryOfficeName());
		}
	}
	
	private static class AccountContextMapper implements ContextMapper {

		@Override
		public Object mapFromContext(Object ctx) {
			
			DirContextAdapter context = (DirContextAdapter) ctx;
			DistinguishedName dn = new DistinguishedName(context.getDn());
			
			Account account = AccountFactory.create(
					dn.getLdapRdn(0).getComponent().getValue(),
					context.getStringAttribute("uid"),
					context.getStringAttribute("cn"),
					context.getStringAttribute("sn"),
					context.getStringAttribute("givenName"),
					context.getStringAttribute("mail"),
					
					context.getStringAttribute("o"),
					context.getStringAttribute("title"),

					context.getStringAttribute("telephoneNumber"),
					context.getStringAttribute("description"),
					
					context.getStringAttribute("userPassword"),

					context.getStringAttribute("postalAddress"),
					context.getStringAttribute("postalCode"),
					context.getStringAttribute("registeredAddress"),
					context.getStringAttribute("postOfficeBox"),
					context.getStringAttribute("physicalDeliveryOfficeName") );

			return account;
		}
	}
	
	private boolean isNullValue(String str) {

		if(str == null) return true;
		
		if(str.length() == 0) return true;
		
		return false;
	}

	
	


	@Override
	public List<Account> findNewPasswordBeforeDate(Date date) {
		// TODO Auto-generated method stub
		return null;
	}

	/**
	 * Resets the new password
	 */
	@Override
	public void resetNewPassword(String uid) throws DataServiceException,
			NotFoundException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void changePassword(String uid, String password) throws DataServiceException {
		// TODO Auto-generated method stub
		
		
		
		
	}


	
}

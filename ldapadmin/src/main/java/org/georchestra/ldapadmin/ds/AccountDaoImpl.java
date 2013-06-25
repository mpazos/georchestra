/**
 * 
 */
package org.georchestra.ldapadmin.ds;

import java.util.Date;
import java.util.List;

import javax.naming.Name;

import org.georchestra.ldapadmin.dto.Account;
import org.springframework.ldap.core.ContextMapper;
import org.springframework.ldap.core.DirContextAdapter;
import org.springframework.ldap.core.DistinguishedName;
import org.springframework.ldap.core.LdapTemplate;
import org.springframework.ldap.filter.EqualsFilter;

/**
 * This class is responsible of maintaining the user accounts. 
 * 
 * 
 * @author Mauricio Pazos
 *
 */
public final class AccountDaoImpl implements AccountDao{
	
	private LdapTemplate ldapTemplate;
	
	public LdapTemplate getLdapTemplate() {
		return ldapTemplate;
	}

	public void setLdapTemplate(LdapTemplate ldapTemplate) {
		this.ldapTemplate = ldapTemplate;
	}
	
	/**
	 * Returns all users' account.
	 * 
	 * @return List of accounts
	 */
	@Override
	public List<Account> findAll() throws AccountDaoException{
		
		EqualsFilter filter = new EqualsFilter("objectClass", "person");
		return ldapTemplate.search(DistinguishedName.EMPTY_PATH, filter.encode(), getContextMapper());
	}

	@Override
	public List<String> findAllGroups() throws AccountDaoException {
		
		EqualsFilter filter = new EqualsFilter("objectClass", "posixGroup");
		return ldapTemplate.search(DistinguishedName.EMPTY_PATH, filter.encode(), getContextMapperGroup());
	}
	

	@Override
	public void create(final Account account, final boolean pending) throws AccountDaoException, DuplicatedEmailException, RequiredFiedException{
	
		assert account != null;
		
		// TODO implement the following logic
		// insert the account entry in ldap at the PENDING_USERS groups. 
		// Then admin will then be able to move them to SV_USERS group.
		// if pending is false the entry is set at SV_USERS group.
		
		checkMandatoryFields(account);
		
		Name dn = buildDn(account.getUid());
		
		DirContextAdapter context = new DirContextAdapter(dn);
		mapToContext(account, context);
		
		ldapTemplate.bind(dn, context, null);
	}


	@Override
	public void update(final Account account) throws AccountDaoException, DuplicatedEmailException, RequiredFiedException{
		
		checkMandatoryFields(account);
		
//		TODO hack
//		
//		Name dn = buildDn(account.getUid());
//		DirContextAdapter context = (DirContextAdapter) ldapTemplate.lookup(dn);
//		mapToContext(account, context);
//		ldapTemplate.modifyAttributes(dn, context.getModificationItems());
	}


	@Override
	public void delete(final Account account) throws AccountDaoException, NotFoundException{
		ldapTemplate.unbind(buildDn(account.getUid()));
	}

	@Override
	public Account findByUID(final String uid) throws AccountDaoException, NotFoundException{

		DistinguishedName dn = buildDn(uid);
		Account a = (Account) ldapTemplate.lookup(dn, getContextMapper());
		
		if(a == null){
			throw new NotFoundException("There is not a user with this email: " + uid);
		}
		
		return  a;
		
	}

	@Override
	public Account findByEmail(final String email) throws AccountDaoException, NotFoundException {

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
	
	
	
	private ContextMapper getContextMapperGroup() {
		return new GroupContextMapper();
	}
	

	/**
	 * Create an ldap entry for the user 
	 * 
	 * @param uid user id
	 * @return
	 */
	private DistinguishedName buildDn(String  uid) {
		DistinguishedName dn = new DistinguishedName();
				
		dn.add("uid", uid);
		dn.add("ou", "users");
		
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
		if( a.getCommonName().length() <= 0 ){
			throw new  RequiredFiedException("common name (cn) is requird");
		}
		if( a.getSurname().length() <= 0){
			throw new RequiredFiedException("surname name (sn) is requird");
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
	 */
	private void mapToContext(Account account, DirContextAdapter context) {
		
		context.setAttributeValues("objectclass", new String[] { "top", "person", "organizationalPerson" });
		
		// person attributes
		context.setAttributeValue("cn", account.getCommonName());
		
		context.setAttributeValue("sn", account.getSurname());
		
		context.setAttributeValue("uid", account.getUid());

		
		if( !isNullValue(account.getDetails())){
			context.setAttributeValue("description", account.getDetails());
		}

		if( !isNullValue(account.getPhone()) ){
			context.setAttributeValue("telephoneNumber", account.getPhone());
		}
		
		context.setAttributeValue("userPassword", account.getPassword());

		context.setAttributeValue("mail", account.getEmail());

		if( !isNullValue(account.getOrg()) ){
			context.setAttributeValue("o", account.getOrg());
		}
		
	}
	
	private boolean isNullValue(String str) {

		if(str == null) return true;
		
		if(str.length() == 0) return true;
		
		return false;
	}

	private static class GroupContextMapper implements ContextMapper {

		@Override
		public Object mapFromContext(Object ctx) {
			
			DirContextAdapter context = (DirContextAdapter) ctx;

			return context.getStringAttribute("cn");
		}
	}

	private static class AccountContextMapper implements ContextMapper {

		@Override
		public Object mapFromContext(Object ctx) {
			
			DirContextAdapter context = (DirContextAdapter) ctx;
			DistinguishedName dn = new DistinguishedName(context.getDn());
			Account user = new Account();
			user.setRole(dn.getLdapRdn(0).getComponent().getValue());
			user.setUid(context.getStringAttribute("uid"));
			user.setCommonName(context.getStringAttribute("cn"));
			user.setEmail(context.getStringAttribute("mail"));
			user.setOrg(context.getStringAttribute("o"));
			user.setCommonName(context.getStringAttribute("sn"));
			user.setCommonName(context.getStringAttribute("givenName"));
			user.setOrg(context.getStringAttribute("title"));
			user.setOrg(context.getStringAttribute("postalAddress"));
			user.setOrg(context.getStringAttribute("postalCode"));
			user.setOrg(context.getStringAttribute("registeredAddress"));
			user.setOrg(context.getStringAttribute("postOfficeBox"));
			user.setOrg(context.getStringAttribute("physicalDeliveryOfficeName"));

			//user.setGeographicArea(context.getStringAttribute("geographicArea"));
			//user.setRole(context.getStringAttribute("role"));
			// TODO requires more settings
			
			return user;
		}
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
	public void resetNewPassword(String uid) throws AccountDaoException,
			NotFoundException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void changePassword(String uid, String password) throws AccountDaoException {
		// TODO Auto-generated method stub
		
		
		
		
	}


	
}

/**
 * 
 */
package org.georchestra.ldapadmin.ds;

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
	 * Returns all persons' account.
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
	public void create(final Account account, final boolean pending) throws AccountDaoException, DuplicatedEmailException{
	
		// TODO implement the following logic
		// insert the account entry in ldap at the PENDING_USERS groups. 
		// Then admin will then be able to move them to SV_USERS group.
		// if pending is false the entry is set at SV_USERS group.
		
		Name dn = buildDn(account.getUid());
		
		DirContextAdapter context = new DirContextAdapter(dn);
		mapToContext(account, context);
		
		ldapTemplate.bind(dn, context, null);
	}


	@Override
	public void update(final Account account) throws AccountDaoException, DuplicatedEmailException{
		
		Name dn = buildDn(account.getUid());
		DirContextAdapter context = (DirContextAdapter) ldapTemplate.lookup(dn);
		mapToContext(account, context);
		ldapTemplate.modifyAttributes(dn, context.getModificationItems());
	}

	@Override
	public void delete(final Account account) throws AccountDaoException, NotFoundException{
		ldapTemplate.unbind(buildDn(account.getUid()));
	}

	@Override
	public Account findByUID(final String uid) throws AccountDaoException, NotFoundException{
		DistinguishedName dn = buildDn(uid);
		return (Account) ldapTemplate.lookup(dn, getContextMapper());
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
	
	private void mapToContext(Account account, DirContextAdapter context) {
		context.setAttributeValues("objectclass", new String[] { "top", "person" });
		context.setAttributeValue("cn", account.getName());
		context.setAttributeValue("mail", account.getEmail());
		context.setAttributeValue("o", account.getOrg());
		// TODO requires more settings
		
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
			user.setName(context.getStringAttribute("cn"));
			user.setEmail(context.getStringAttribute("mail"));
			user.setOrg(context.getStringAttribute("o"));
			//user.setGeographicArea(context.getStringAttribute("geographicArea"));
			//user.setRole(context.getStringAttribute("role"));
			// TODO requires more settings
			
			return user;
		}
	}
	
}

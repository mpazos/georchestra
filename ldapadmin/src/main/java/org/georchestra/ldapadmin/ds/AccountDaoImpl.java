/**
 * 
 */
package org.georchestra.ldapadmin.ds;

import java.util.Collections;
import java.util.List;

import javax.naming.NamingException;
import javax.naming.directory.Attributes;

import org.georchestra.ldapadmin.dto.Account;
import org.springframework.ldap.core.AttributesMapper;
import org.springframework.ldap.core.LdapTemplate;

/**
 * 
 * 
 * @author Mauricio Pazos
 *
 */
public final class AccountDaoImpl implements AccountDao{

	
	private LdapTemplate ldapTemplate;
	

	public LdapTemplate getLdapTemplate() {
		return this.ldapTemplate;
	}



	public void setLdapTemplate(LdapTemplate ldapTemplate) {
		this.ldapTemplate = ldapTemplate;
	}

	/**
	 * Returns all persons' account
	 * 
	 * @return List of accounts
	 */
	@Override
	public List<Account> findAll(){
		
		List<Account> accountList = this.ldapTemplate.search(
				"", 
				"(objectClass=person)", 
				new AttributesMapper() {
			
					@Override
					public Object mapFromAttributes(Attributes attributes)
							throws NamingException {
						
						Account account = new Account();
						
						account.setUid( (String) attributes.get("uid").get());
						account.setName( (String) attributes.get("cn").get());
						// TODO add all attributes
						
						return account;
					}
		});
		
		
		return accountList;
	}
}

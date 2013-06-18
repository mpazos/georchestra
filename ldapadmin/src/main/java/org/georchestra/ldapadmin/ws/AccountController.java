/**
 * 
 */
package org.georchestra.ldapadmin.ws;

import java.util.List;

import org.georchestra.ldapadmin.ds.AccountDao;
import org.georchestra.ldapadmin.ds.AccountDaoImpl;
import org.georchestra.ldapadmin.dto.Account;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * @author Mauricio Pazos
 *
 */
@Controller
@RequestMapping(value="/public/accounts")
public final class AccountController {
	
	private AccountDao accountDao;
	
	public AccountDao getAccountDao() {
		return accountDao;
	}

	public void setAccountDao(AccountDao accountDao) {
		this.accountDao = accountDao;
	}

	@RequestMapping(method=RequestMethod.POST)
	public void initForm(){
		
		System.out.println("initializing Account!!");
		
	}
	
	/**
	 * Returns all accounts
	 */
	@RequestMapping(method=RequestMethod.GET)
	public void findAll(){
		
		System.out.println("findAll!!");
		
		List<Account> accountList = accountDao.findAll();
		
		for (Account account : accountList) {
			
			System.out.println(account);
		}
	}

}

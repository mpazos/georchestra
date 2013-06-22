/**
 * 
 */
package org.georchestra.ldapadmin.bs;

import java.util.Date;
import java.util.List;

import org.georchestra.ldapadmin.ds.AccountDao;
import org.georchestra.ldapadmin.ds.AccountDaoException;
import org.georchestra.ldapadmin.ds.AccountDaoImpl;
import org.georchestra.ldapadmin.ds.NotFoundException;
import org.georchestra.ldapadmin.dto.Account;

/**
 * 
 * This class is responsible of checking whether the user user does not log in during this period, 
 * thus new password is discarded.
 * 
 * TODO UNDER CONSTRUCTION
 * 
 * @author Mauricio Pazos
 *
 */
public final class PasswordManagement {

	
	/** limit period to confirm the new password */ 
	private static int DEFAULT_PERIOD_DAYS = 1; 
	private int delay = DEFAULT_PERIOD_DAYS; 
	

	/**
	 * Reset those passwords which weren't confirmed in the configured period.
	 *  
	 * This task is scheduled taking into account the delay period. 
	 *  
	 */
	public void run(){

		AccountDao accountDao = new AccountDaoImpl();
		
		Date expired = null; // TODO now + delay
		List<Account>  accountList = accountDao.findNewPasswordBeforeDate(expired);
		
		for (Account account : accountList) {
			
			try {
				accountDao.resetNewPassword(account.getUid());
				
			} catch (AccountDaoException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (NotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
	}
	
	

}

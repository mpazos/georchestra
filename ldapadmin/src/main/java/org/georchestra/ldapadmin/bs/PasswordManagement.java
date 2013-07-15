/**
 * 
 */
package org.georchestra.ldapadmin.bs;

import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.georchestra.ldapadmin.ds.AccountDao;
import org.georchestra.ldapadmin.ds.DataServiceException;
import org.georchestra.ldapadmin.ds.AccountDaoImpl;
import org.georchestra.ldapadmin.ds.NotFoundException;
import org.georchestra.ldapadmin.ds.UserTokenDao;
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

	
	private static final Log LOG = LogFactory.getLog(PasswordManagement.class.getName());
	
	/** limit period to confirm the new password */ 
	private final static int DEFAULT_PERIOD_DAYS = 1; 
	private int delayInDays = DEFAULT_PERIOD_DAYS; 
	


	/**
	 * Reset those passwords which weren't confirmed in the configured period.
	 *  
	 * This task is scheduled taking into account the delay period. 
	 *  
	 */
	public void run(){

		UserTokenDao userTokenDao = new UserTokenDao();
		
		Calendar calendar = Calendar.getInstance();
		
		long now = calendar.getTimeInMillis();
		Date expired = new Date(now + toMilliseconds(this.delayInDays));
		
		try {
			List<Map<String, Object>>  userTokenToDelete = userTokenDao.findBeforeDate(expired);
			for (Map<String, Object> userToken : userTokenToDelete) {
				
				try {
					userTokenDao.delete( (String) userToken.get("uid") );
					
				} catch (Exception e) {
					LOG.error(e.getMessage());
				} 
			}
		} catch (DataServiceException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		
	}


	/**
	 * 
	 * @param delayInDays
	 * @return the delay days in milliseconds 
	 */
	private long toMilliseconds(int delayInDays) {
				
		return 24 * 3600000 * delayInDays;
	}
	
	

}

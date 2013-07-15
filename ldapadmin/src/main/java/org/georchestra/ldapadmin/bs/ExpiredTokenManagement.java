/**
 * 
 */
package org.georchestra.ldapadmin.bs;

import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.georchestra.ldapadmin.ds.DataServiceException;
import org.georchestra.ldapadmin.ds.UserTokenDao;

/**
 * 
 * This class is responsible of checking whether the user user does not use the generated token to change his password.
 * 
 * <p>
 *
 * </p>
 * 
 * @author Mauricio Pazos
 *
 */
public final class ExpiredTokenManagement {

	
	private static final Log LOG = LogFactory.getLog(ExpiredTokenManagement.class.getName());
	
	/** limit period to confirm the new password */ 
	private final static int DEFAULT_PERIOD_DAYS = 1; 
	private int delayInDays = DEFAULT_PERIOD_DAYS; 
	
	
	private final ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(1);
	
	public void start(){
		
		long delay = toMilliseconds(delayInDays);

		final ExpiredTokenCleanTask expiredTokenCleanTask = new ExpiredTokenCleanTask();
		expiredTokenCleanTask.setDelayInMillisecconds(delay);
		
		this.scheduler.scheduleWithFixedDelay(expiredTokenCleanTask, 0, delay, TimeUnit.MILLISECONDS);
		
	}


	/**
	 * Converts the days to millisecconds
	 * 
	 * @param delayInDays
	 * @return the delay days in milliseconds 
	 */
	private static long toMilliseconds(int delayInDays) {
				
		return 24 * 3600000 * delayInDays;
	}
	
	

}

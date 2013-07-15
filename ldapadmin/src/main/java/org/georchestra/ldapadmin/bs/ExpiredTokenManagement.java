/**
 * 
 */
package org.georchestra.ldapadmin.bs;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * 
 * This class is responsible of checking whether the user user does not use the generated token to change his password.
 * 
 * <p>
 * The period to execute this task is configured.
 *
 * </p>
 * 
 * @author Mauricio Pazos
 *
 */
public final class ExpiredTokenManagement {

	
	private static final Log LOG = LogFactory.getLog(ExpiredTokenManagement.class.getName());
	
	private final ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(1);

	/** the default period */
	private final static int DEFAULT_PERIOD_DAYS = 1;
	
	/** delay in days to execute the cleaning task */ 
	private int delayInDays = DEFAULT_PERIOD_DAYS; 
	
	public ExpiredTokenManagement() {
		
		start();
	}

	public int getDelayInDays() {
		return delayInDays;
	}

	public void setDelayInDays(int delayInDays) {
		this.delayInDays = delayInDays;
	}

	protected void start(){
		
		long delay = toMilliseconds(this.delayInDays);

		final ExpiredTokenCleanTask expiredTokenCleanTask = new ExpiredTokenCleanTask();
		expiredTokenCleanTask.setDelayInMillisecconds(delay);
		
		this.scheduler.scheduleWithFixedDelay(expiredTokenCleanTask, 0, delay, TimeUnit.MILLISECONDS);
		
		if(LOG.isDebugEnabled()){
			LOG.debug("was scheduled - delay (days):" + delayInDays  );
		}
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

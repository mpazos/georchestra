/**
 * 
 */
package org.georchestra.ldapadmin.mailservice;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * Facade of mail service.
 * 
 * @author Mauricio Pazos
 *
 */
public final class MailService {
	
	protected static final Log LOG = LogFactory.getLog(MailService.class.getName());

	private EmailFactoryImpl emailFactory;
	
	
	@Autowired
	public MailService(EmailFactoryImpl emailFactory) {
		this.emailFactory = emailFactory;
	}


	public void sendNewAccount(final String uid, final String userName, final String moderatorEmail) {

		try {
			NewAccountEmail email = this.emailFactory.createNewAccountEmail(new String[]{moderatorEmail});
			
			email.sendMsg(userName, uid);
		
		} catch (Exception e) {
			
			LOG.error(e);
		} 
	}

	public void sendPassowrd(final String uid, final String userName, final String newPassword, final String toEmail) {

		try {
			NewPasswordEmail email = this.emailFactory.createNewPasswordEmail(new String[]{toEmail});
			
			email.sendMsg(userName, uid, newPassword);
		
		} catch (Exception e) {
			
			LOG.error(e);
		} 
	}


}

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
		
	//TODO deprecated	
		try {
			NewPasswordEmail email = this.emailFactory.createNewPasswordEmail(new String[]{toEmail});
			
			email.sendMsg(userName, uid, newPassword);
		
		} catch (Exception e) {
			
			LOG.error(e);
		} 
	}


	/**
	 * Sent an email to the user whit the unique URL required to change his password.
	 * 
	 * @param uid
	 * @param commonName
	 * @param url
	 * @param email
	 * 
	 */
	public void sendChangePassowrdURL(final String uid, final String commonName, final String url, final String email) {
		
		if(LOG.isDebugEnabled()){
			LOG.debug("uid: "+uid+ "- commonName" + commonName + " - url: " + url + " - email: " + email);
		}
		
		
		// TODO Auto-generated method stub
		
	}


}

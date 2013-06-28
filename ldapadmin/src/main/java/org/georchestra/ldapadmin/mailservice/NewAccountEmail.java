/**
 * 
 */
package org.georchestra.ldapadmin.mailservice;

import javax.mail.MessagingException;
import javax.mail.internet.AddressException;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.georchestra.lib.mailservice.Email;

/**
 * This mail is sent to the moderator when a new account is created.
 * 
 * @author Mauricio Pazos
 *
 */
class NewAccountEmail extends Email {

	private static final Log LOG = LogFactory.getLog(NewAccountEmail.class.getName());

	public NewAccountEmail(
			String[] recipients, 
			String emailSubject,
			String smtpHost, 
			int smtpPort, 
			String replyTo, 
			String from,
			String bodyEncoding, 
			String subjectEncoding, 
			String[] languages, 
			String fileBodyTemplate) {
	
		super(recipients, emailSubject, smtpHost, smtpPort, replyTo, from,
				bodyEncoding, subjectEncoding, languages);
	}
	
	public void sendMsg(final String userName, final String uid ) throws AddressException, MessagingException {

		LOG.debug("New account user in the pending group. User ID: " + uid );
		
		String body = writeNewAccoutnMail(uid, userName);

		super.sendMsg(body);
	}
	

	private String writeNewAccoutnMail(String uid, String name) {

		String body = String.format("New user is expecting for moderation, Name:  %s  - User ID: %s", name, uid);

		return body;
	}
	
//	private String writeNewPasswordMail(final String fullName, final String newPassword) {
//		
//		// TODO retrieve the body template from this.fileTemplate 
//		
//		String body = String.format("%s:  your new password is %s", fullName, newPassword);
//		
//		return body;
//	}
	

}

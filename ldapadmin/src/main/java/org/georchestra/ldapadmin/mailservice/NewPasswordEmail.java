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
 * Email
 * 
 * @author Mauricio Pazos
 * 
 */
class NewPasswordEmail extends Email {
	
	private static final Log LOG = LogFactory.getLog(NewPasswordEmail.class.getName());
	
	public NewPasswordEmail(
			String[] recipients, 
			String emailSubject,
			String smtpHost, 
			int smtpPort, 
			String replyTo, 
			String from,
			String bodyEncoding, 
			String subjectEncoding, 
			String[] languages, 
			String fileTemplate) {

		super(recipients, emailSubject, smtpHost, smtpPort, replyTo, from,
				bodyEncoding, subjectEncoding, languages);
		
	}

	public void sendMsg(final String fullName, final String uid, final String newPassword) throws AddressException, MessagingException {

		LOG.debug("send new password email to user uid: " + uid );
		
		String body = writeNewPasswordMail(fullName, newPassword);

		super.sendMsg(body);
	}
	private String writeNewPasswordMail(final String fullName, final String newPassword) {
		
		// TODO retrieve the body template from this.fileTemplate 
		
		String body = String.format("%s:  your new password is %s", fullName, newPassword);
		
		return body;
	}

}

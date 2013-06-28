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
 * Email to send when the user requests for a new password.
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
				bodyEncoding, subjectEncoding, languages, fileTemplate);

		
	}

	public void sendMsg(final String userName, final String uid, final String newPassword) throws AddressException, MessagingException {

		LOG.debug("send new password email to user "+ userName+ " - uid: " + uid  );
		
		String body = writeNewPasswordMail(userName, newPassword);

		super.sendMsg(body);
	}
	
	private String writeNewPasswordMail(final String userName, final String newPassword) {
		
		final String body = getBodyTemplate();
		
		body.replace("{pwd}", newPassword);
		
		LOG.debug("built email: "+ body);
		
		return body;
	}

}

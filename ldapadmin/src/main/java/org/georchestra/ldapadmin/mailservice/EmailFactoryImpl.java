package org.georchestra.ldapadmin.mailservice;

import java.io.IOException;

import org.georchestra.lib.mailservice.AbstractEmailFactory;

/**
 * Creates the e-mails required for this application.
 * 
 * @author Mauricio Pazos
 *
 */
class EmailFactoryImpl extends AbstractEmailFactory {
	
	private String emailNewAccountFile;
	
	private String emailNewPasswordFile;

	public String getEmailNewAccountFile() {
		return emailNewAccountFile;
	}

	public void setEmailNewAccountFile(String emailNewAccountFile) {
		this.emailNewAccountFile = emailNewAccountFile;
	}

	public String getEmailNewPasswordFile() {
		return emailNewPasswordFile;
	}

	public void setEmailNewPasswordFile(String emailNewPasswordFile) {
		this.emailNewPasswordFile = emailNewPasswordFile;
	}
	
	
	public NewPasswordEmail createNewPasswordEmail(String[] recipients) throws IOException {
		
		NewPasswordEmail mail =  new NewPasswordEmail(
				recipients, 
				emailSubject,
				this.smtpHost,
				this.smtpPort,
				this.replyTo,
				this.from,
				this.bodyEncoding,
				this.subjectEncoding,
				this.languages,
				this.emailNewPasswordFile);
		
		return mail;
	}

	public NewAccountEmail createNewAccountEmail(String[] recipients) throws IOException {
		
		NewAccountEmail mail =  new NewAccountEmail(
				recipients, 
				emailSubject,
				this.smtpHost,
				this.smtpPort,
				this.replyTo,
				this.from,
				this.bodyEncoding,
				this.subjectEncoding,
				this.languages,
				this.emailNewAccountFile);
		
		return mail;
	}
}

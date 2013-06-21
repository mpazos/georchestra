/**
 * 
 */
package org.georchestra.ldapadmin.mailservice;

/**
 * Facade of mail service.
 * 
 * @author Mauricio Pazos
 *
 */
public final class MailService {

	public static void send(String uid, String name) {
		// TODO Auto-generated method stub
		
		String body = writeNewAccoutnMail(uid, name);
		
		System.out.println("send mail: " +  body);
		
	}

	private static String writeNewAccoutnMail(String uid, String name) {
		String body = "The following user requires an account: " + uid + " - " + name;
		
		return body;
	}

	public static void sendPassowrd(String uid, String name, String newPassword) {
		String body = writeNewPasswordMail(uid, name, newPassword);
		
		System.out.println("send mail: " +  body);
		
	}

	private static String writeNewPasswordMail(final String uid, final String name, final String newPassword) {
		
		// TODO improve this text
		String body = "A new passowrd was generated "+ newPassword + "for the user " +  name;
		
		return body;
	}

}

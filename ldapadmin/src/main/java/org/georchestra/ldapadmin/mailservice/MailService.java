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

}

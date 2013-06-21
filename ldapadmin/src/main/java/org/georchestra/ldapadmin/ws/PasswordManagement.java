/**
 * 
 */
package org.georchestra.ldapadmin.ws;

import org.apache.commons.lang.RandomStringUtils;
import org.springframework.util.StringUtils;
import org.springframework.validation.Errors;

/**
 * 
 * @author Mauricio Pazos
 *
 */
final class PasswordManagement  {
	
	public static final int SIZE = 8;
	
	public static void validate(final String password, final String confirmPassword, Errors errors) {
		
		final String pwd1 = password.trim();
		final String pwd2 = confirmPassword.trim();
		
		if( !StringUtils.hasLength(pwd1)){
			
			errors.rejectValue("password", "required", "required");
		}
		if( !StringUtils.hasLength(pwd2)){
			
			errors.rejectValue("confirmPassword", "required", "required");
		}
		if( StringUtils.hasLength(pwd1) && StringUtils.hasLength(pwd2) ){
			
			if(!pwd1.equals(pwd2)){
				errors.rejectValue("confirmPassword", "pwdNotEquals", "These passwords don't match");
				
			} else {
				
				if(pwd1.length() < SIZE ){
					errors.rejectValue("password", "sizeError", "The password does have at least 8 characters");
				}
			}
		}
	}

	/**
	 * Generates a new strong password.
	 * 
	 * @return new password
	 */
	public static String generateNewPassword() {
		return RandomStringUtils.randomAlphabetic(SIZE);	
	}
	

}

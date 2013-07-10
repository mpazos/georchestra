/**
 * 
 */
package org.georchestra.ldapadmin.ws.lostpassword;

import org.georchestra.ldapadmin.ws.utils.PasswordUtils;
import org.springframework.validation.BindingResult;

/**
 * @author Mauricio Pazos
 *
 */
public class NewPasswordFormValidator {

	
	public void validate(NewPasswordFormBean form, BindingResult errors) {
		
		PasswordUtils.validate( form.getPassword(), form.getConfirmPassword(), errors);
		
	}
	
}

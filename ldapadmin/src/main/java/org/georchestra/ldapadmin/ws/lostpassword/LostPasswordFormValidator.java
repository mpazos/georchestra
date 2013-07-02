package org.georchestra.ldapadmin.ws.lostpassword;

import org.georchestra.ldapadmin.ws.utils.EmailUtils;
import org.springframework.validation.BindingResult;

/**
 * Validates the password form. 
 * 
 * 
 * @author Mauricio Pazos
 *
 */
class LostPasswordFormValidator {

	public void validate(LostPasswordFormBean form, BindingResult errors) {
		
		EmailUtils.validate(form.getEmail(), errors);
		
	}

}

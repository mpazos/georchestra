package org.georchestra.ldapadmin.ws.lostpassword;

import org.apache.commons.validator.routines.EmailValidator;
import org.springframework.util.StringUtils;
import org.springframework.validation.BindingResult;
import org.springframework.validation.Errors;

/**
 * Validates the password form. 
 * 
 * 
 * @author Mauricio Pazos
 *
 */
class LostPasswordFormValidator {

	public void validate(LostPasswordFormBean formBean, BindingResult result) {
		
		validateEmail(formBean.getEmail(), result);
		
	}
	
	private void validateEmail(final String email, Errors errors) {
		
		if( !StringUtils.hasLength(email)){
			errors.rejectValue("email", "required", "required");
		} else {
			if(!EmailValidator.getInstance().isValid(email)){
				errors.rejectValue("email", "invalidFormat", "Invalid Format");
			}
		}
	}
	

}

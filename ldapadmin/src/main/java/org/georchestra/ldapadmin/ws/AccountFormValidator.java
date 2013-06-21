package org.georchestra.ldapadmin.ws;

import org.apache.commons.validator.routines.EmailValidator;
import org.springframework.util.StringUtils;
import org.springframework.validation.Errors;


class AccountFormValidator {
	
	public void validate(AccountFormBean form, Errors errors) {
		
		if( !StringUtils.hasLength(form.getName())){
			errors.rejectValue("name", "required", "required");
		}
		validateEmail(form.getEmail(), errors);
		
		validatePassword( form.getPassword(), form.getConfirmPassword(), errors);
		
		validatePhone(form.getPhone(), errors); 

		validateCaptcha(form.getCaptchaGenerated(), form.getCaptcha(), errors);
			
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

	private void validateCaptcha(final String captchaGenerated, final String captcha, Errors errors) {
		
		final String trimmedCaptcha = captcha.trim();
		
		if(!StringUtils.hasLength(trimmedCaptcha)){
			errors.rejectValue("captcha", "required", "required");
		} else {
			if(!captchaGenerated.equals(trimmedCaptcha)){
				errors.rejectValue("captcha", "captchaNoMatch", "The texts didn't match");
				
			}
		}
	}

	private void validatePassword(final String password, final String confirmPassword, Errors errors) {
		
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
				
				if(pwd1.length() < 8 ){
					errors.rejectValue("password", "sizeError", "The password does have at least 8 characters");
				}
			}
		}
		
	}

	private void validatePhone(final String phone, Errors errors) {
		
		if (StringUtils.hasLength(phone.trim())) {
			for (int i = 0; i < phone.length(); ++i) {
				if ((Character.isDigit(phone.charAt(i))) == false) {
					errors.rejectValue("phone", "nonNumeric", "The phone should be numeric");
					break;
				}
			}
		}
	}

}

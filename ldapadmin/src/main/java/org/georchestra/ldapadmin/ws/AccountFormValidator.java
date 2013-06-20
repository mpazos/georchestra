package org.georchestra.ldapadmin.ws;

import org.springframework.util.StringUtils;
import org.springframework.validation.Errors;


class AccountFormValidator {

	
	public void validate(AccountFormBean form, Errors errors) {
		
		if( !StringUtils.hasLength(form.getName())){
			errors.rejectValue("name", "required", "user name");
		}
		if( !StringUtils.hasLength(form.getEmail())){
			errors.rejectValue("email", "required", "");
		}
		validatePassword( form.getPassword(), form.getConfirmPassword(), errors);
		
		validatePhone(form.getPhone(), errors); 

		validateCaptcha(form.getCaptchaGenerated(), form.getCaptcha(), errors);
			
	}

	private void validateCaptcha(final String captchaGenerated, final String captcha, Errors errors) {
		
		if(!captchaGenerated.equals(captcha)){
			errors.rejectValue("captcha", "The texts didn't match", "");
			
		}
	}

	private void validatePassword(final String password, final String confirmPassword, Errors errors) {
		
		final String pwd1 = password.trim();
		final String pwd2 = confirmPassword.trim();
		
		if( !StringUtils.hasLength(pwd1)){
			
			errors.rejectValue("password", "required", "");
		}
		if( !StringUtils.hasLength(pwd2)){
			
			errors.rejectValue("confirmPassword", "required", "");
		}
		if( StringUtils.hasLength(pwd1) && StringUtils.hasLength(pwd2) ){
			
			if(!pwd1.equals(pwd2)){
				errors.rejectValue("confirmPassword", "These passwords don't match", "");
				
			} else {
				
				if(pwd1.length() < 8 ){
					errors.rejectValue("password", "The password does have at least 8 characters", "");
				}
			}
		}
		
	}

	private void validatePhone(final String phone, Errors errors) {
		
		if (StringUtils.hasLength(phone.trim())) {
			for (int i = 0; i < phone.length(); ++i) {
				if ((Character.isDigit(phone.charAt(i))) == false) {
					errors.rejectValue("phone", "nonNumeric", "non numeric");
					break;
				}
			}
		}
	}

}

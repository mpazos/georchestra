/**
 * 
 */
package org.georchestra.ldapadmin.ws.newaccount;

import java.io.Serializable;
import java.util.List;


/**
 * This model maintains the account form data.
 * 
 * @author Mauricio Pazos
 *
 */
public class AccountFormBean implements Serializable{

	
	/**
	 * 
	 */
	private static final long serialVersionUID = 6955470190631684934L;
	
	private String uid;
	private String name;
	private String surname;
	
	private String org;
	private String email;
	private String phone;
	private String details;
	private String password;
	private String confirmPassword;
	private String captchaGenerated;
	private String captcha;
	
	
	public String getUid() {
		return uid;
	}
	public void setUid(String uid) {
		this.uid = uid;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getOrg() {
		return org;
	}
	public void setOrg(String org) {
		this.org = org;
	}
	
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getPhone() {
		return phone;
	}
	public void setPhone(String phone) {
		this.phone = phone;
	}
	public String getDetails() {
		return details;
	}
	public void setDetails(String details) {
		this.details = details;
	}

	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getConfirmPassword() {
		return confirmPassword;
	}
	public void setConfirmPassword(String confirmPassword) {
		this.confirmPassword = confirmPassword;
	}
	public String getCaptcha() {
		return captcha;
	}
	public void setCaptcha(String captcha) {
		this.captcha = captcha;
	}
	
	public String getCaptchaGenerated() {
		return captchaGenerated;
	}
	
	public void setCaptchaGenerated(String captchaGenerated) {
		this.captchaGenerated = captchaGenerated;
	}
	
	public String getSurname() {
		return surname;
	}
	
	public void setSurname(String sn){

		this.surname = sn;
	}
	@Override
	public String toString() {
		return "AccountFormBean [uid=" + uid + ", name=" + name + ", org="
				+ org + ", email=" + email + ", phone=" + phone + ", details="
				+ details + ", password=" + password + ", confirmPassword="
				+ confirmPassword + ", captchaGenerated=" + captchaGenerated
				+ ", captcha=" + captcha + ", surname=" + surname + "]";
	}


	
}

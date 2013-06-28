/**
 * 
 */
package org.georchestra.ldapadmin.ws.lostpassword;

import java.io.IOException;

import org.georchestra.ldapadmin.ds.AccountDao;
import org.georchestra.ldapadmin.ds.DataServiceException;
import org.georchestra.ldapadmin.ds.NotFoundException;
import org.georchestra.ldapadmin.dto.Account;
import org.georchestra.ldapadmin.mailservice.MailService;
import org.georchestra.ldapadmin.ws.utils.PasswordUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.bind.support.SessionStatus;

/**
 * Manage the user interactions required to implement the lost password workflow: 
 * <p>
 * <ul>
 * 
 * <li>Present a form in order to ask for the user's mail.</li>
 * 
 * <li>If the given email matches one of the LDAP users, an email is sent to this user with a new strong password.</li>
 * 
 * <li>From this moment on, and for a configurable delay (say, one day by default), both passwords (old & new) will be considered as valid.</li>
 * </ul>
 * </p>
 * 
 * @author Mauricio Pazos
 */
@Controller
@SessionAttributes(types=LostPasswordFormBean.class)
public class LostPasswordFormController  {
	
	private AccountDao accountDao;
	private MailService mailService;
	
	@Autowired
	public LostPasswordFormController( AccountDao dao, MailService mailSrv){
		this.accountDao = dao;
		this.mailService = mailSrv;
	}
	
	@InitBinder
	public void initForm( WebDataBinder dataBinder) {
		
		dataBinder.setAllowedFields(new String[]{"email"});
	}
	
	@RequestMapping(value="/public/accounts/newPassword", method=RequestMethod.GET)
	public String setupForm(Model model) throws IOException{

		LostPasswordFormBean formBean = new LostPasswordFormBean();
		
		model.addAttribute(formBean);
		
		return "lostPasswordForm";
	}
	
	/**
	 * Generates a new password, then an e-mail is sent to the user to inform that a new password is available.
	 * 
	 * @param formBean		Contains the user's email
	 * @param resultErrors 	will be updated with the list of found errors. 
	 * @param sessionStatus	
	 * 
	 * @return the next view
	 * 
	 * @throws IOException 
	 */
	@RequestMapping(value="/public/accounts/newPassword", method=RequestMethod.POST)
	public String generateNewPassword(
						@ModelAttribute LostPasswordFormBean formBean, 
						BindingResult resultErrors, 
						SessionStatus sessionStatus) 
						throws IOException {
		
		new LostPasswordFormValidator().validate(formBean, resultErrors);
		
		if(resultErrors.hasErrors()){
			
			return "lostPasswordForm";
		}
		
		try {
			// Finds the user using the email as key, if it exists a new password is generated.
			// The new password is stored and an e-mail is sent to the user.
			Account account = this.accountDao.findByEmail(formBean.getEmail());
			
			final String newPassword =  PasswordUtils.generateNewPassword();
			
			this.accountDao.addNewPassword(account.getUid(), newPassword);

			this.mailService.sendPassowrd(account.getUid(), account.getCommonName(), newPassword, account.getEmail());
			
			sessionStatus.setComplete();
			
			return "emailWasSentForm";
			
		} catch (DataServiceException e) {
			
			throw new IOException(e);
			
		} catch (NotFoundException e) {
			
			resultErrors.rejectValue("email", "mailNoExist", "There is not a user with the provided email.");
			
			return "lostPasswordForm";
			
		} 
	}
}

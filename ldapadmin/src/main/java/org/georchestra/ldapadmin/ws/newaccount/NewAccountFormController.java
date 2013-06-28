/**
 * 
 */
package org.georchestra.ldapadmin.ws.newaccount;

import java.io.IOException;
import java.util.UUID;

import org.georchestra.ldapadmin.ds.AccountDao;
import org.georchestra.ldapadmin.ds.DataServiceException;
import org.georchestra.ldapadmin.ds.DuplicatedEmailException;
import org.georchestra.ldapadmin.dto.Account;
import org.georchestra.ldapadmin.dto.AccountFactory;
import org.georchestra.ldapadmin.dto.Group;
import org.georchestra.ldapadmin.mailservice.MailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.bind.support.SessionStatus;

/**
 * Manages the UI Account Form.
 * <p>
 * 
 * </p> 
 * 
 * @author Mauricio Pazos
 *
 */
@Controller
@SessionAttributes(types=AccountFormBean.class)
public final class NewAccountFormController {
	
	private AccountDao accountDao;
	
	/** MODERATED_SIGNUP configuration option */
	private boolean moderatedSignup = false; 
	
	@Autowired
	public NewAccountFormController( AccountDao dao){
		this.accountDao = dao;
	}
	
	@InitBinder
	public void initForm( WebDataBinder dataBinder) {
		
		dataBinder.setAllowedFields(new String[]{"firstName","surname", "email", "phone", "org", "details", "password", "confirmPassword", "role", "captchaGenerated", "captcha"});
	}
	
	@RequestMapping(value="/public/accounts/new", method=RequestMethod.GET)
	public String setupForm(Model model) throws IOException{

		
		AccountFormBean formBean = new AccountFormBean();
		
		model.addAttribute(formBean);
		
		return "createAccountForm";
	}
	
	/**
	 * Creates a new account in ldap.
	 * 
	 * @param formBean
	 * @param result
	 * @param sessionStatus
	 * 
	 * @return the next view
	 * 
	 * @throws IOException 
	 */
	@RequestMapping(value="/public/accounts/new", method=RequestMethod.POST)
	public String create(
						@ModelAttribute AccountFormBean formBean, 
						BindingResult result, 
						SessionStatus sessionStatus) 
						throws IOException {
		
		new AccountFormValidator().validate(formBean, result);
		
		if(result.hasErrors()){
			
			return "createAccountForm";
		}
		
		// insert the new account 
		try {
			
			Account account =  AccountFactory.createBrief(
					UUID.randomUUID().toString(),
					formBean.getPassword(),
					formBean.getFirstName(),
					formBean.getSurname(),
					formBean.getEmail(),
					formBean.getPhone(),
					formBean.getOrg(),
					formBean.getDetails() );

			String groupID = this.moderatedSignup ? Group.PENDING_USERS : Group.SV_USER; 
			
			this.accountDao.create(account, groupID);

			MailService.send(account.getUid(), account.getCommonName());
			
			sessionStatus.setComplete();
			
			return "welcomeNewUser";
			
		} catch (DuplicatedEmailException e) {

			result.addError(new ObjectError("email", "Exist a user with this e-mail"));
			return "createAccountForm";
			
		} catch (DataServiceException e) {
			
			throw new IOException(e);
		}
	}


}

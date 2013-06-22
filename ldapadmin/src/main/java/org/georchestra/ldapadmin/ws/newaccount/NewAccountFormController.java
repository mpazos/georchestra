/**
 * 
 */
package org.georchestra.ldapadmin.ws.newaccount;

import java.io.IOException;
import java.util.List;
import java.util.UUID;

import org.georchestra.ldapadmin.ds.AccountDao;
import org.georchestra.ldapadmin.ds.AccountDaoException;
import org.georchestra.ldapadmin.ds.DuplicatedEmailException;
import org.georchestra.ldapadmin.dto.Account;
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
		
		dataBinder.setAllowedFields(new String[]{"name", "email", "phone", "org", "geographicArea", "details", "password", "confirmPassword", "role", "captchaGenerated", "captcha"});
	}
	
	@RequestMapping(value="/public/accounts/new", method=RequestMethod.GET)
	public String setupForm(Model model) throws IOException{

		List<String> groups = null;
		try {
			groups = findGroups();
		} catch (IOException e) {
			throw new IOException(e);
		}
		// TODO FILTER PRIVILIGED USER 
		
		AccountFormBean formBean = new AccountFormBean();
		formBean.setRoleList(groups);
		
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
			
			UUID uid = UUID.randomUUID();
			Account account = createDto(formBean, uid.toString());
			this.accountDao.create(account, this.moderatedSignup);

			MailService.send(account.getUid(), account.getName());
			
			sessionStatus.setComplete();
			
			//return "redirect:/accounts/new?uid=" + account.getUid();			
			return "welcomeNewUser";
			
		} catch (AccountDaoException e) {
			
			throw new IOException(e);
			
		} catch (DuplicatedEmailException e) {

			result.addError(new ObjectError("email", "Exist a user with this e-mail"));
			return "createAccountForm";
		}
	}

	private Account createDto(AccountFormBean form, String uid) {
		
		Account account = new Account();
		
		account.setName(form.getName());
		account.setEmail(form.getEmail());
		account.setPhone(form.getPhone());
		account.setDetails(form.getDetails());
		account.setRole(form.getRole());
		account.setGeographicArea(form.getGeographicArea());
		account.setOrg(form.getOrg());
		account.setPassword(form.getPassword());
		
		return account;
	}

	
	private List<String> findGroups() throws IOException{
		
		try {
			List<String> list = accountDao.findAllGroups();
			
			return list;
		} catch (AccountDaoException e) {
			throw new IOException(e);
		}
	}
}

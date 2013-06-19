/**
 * 
 */
package org.georchestra.ldapadmin.ws;

import java.util.List;

import org.georchestra.ldapadmin.ds.AccountDao;
import org.georchestra.ldapadmin.dto.Account;
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
 * @author Mauricio Pazos
 *
 */
@Controller
@RequestMapping("/public/accounts.do")
@SessionAttributes(types=AccountFormBean.class)
public final class AccountController {
	
	private AccountDao accountDao;
	
	public AccountDao getAccountDao() {
		return accountDao;
	}

	public void setAccountDao(AccountDao accountDao) {
		this.accountDao = accountDao;
	}
	
	@InitBinder
	public void initForm( WebDataBinder dataBinder) {
		
		System.out.println("initForm");
		
		dataBinder.setAllowedFields(new String[]{"name", "email", "phone"});
	}
	
	@RequestMapping(method=RequestMethod.GET)
	public String setupForm(Model model){
		
		AccountFormBean formBean = new AccountFormBean();
		model.addAttribute(formBean);
		
		return "createaccountform";
	}
	
	@RequestMapping(method=RequestMethod.POST)
	public String processSubmit(@ModelAttribute AccountFormBean formBean, 
								BindingResult result, 
								SessionStatus sessionStatus) {
		
		new AccountFormValidator().validate(formBean, result);
		
		if(result.hasErrors()){
			
			return null;//"createaccountform";
			
		} else{
			//TODO  insert the account entry in ldap
			
			sessionStatus.setComplete();
			//return "redirect:/accounts.do?uid=" + account.getUid();			
			return null;			
		}
	}
	
	/**
	 * Returns all accounts
	 */
	@RequestMapping(method=RequestMethod.GET )
	public void findAll(){
		
		System.out.println("findAll!!");
		
		List<Account> accountList = accountDao.findAll();
		
		for (Account account : accountList) {
			
			System.out.println(account);
		}
	}

}

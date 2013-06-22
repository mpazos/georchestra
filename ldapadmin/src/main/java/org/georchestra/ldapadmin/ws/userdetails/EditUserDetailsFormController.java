/**
 * 
 */
package org.georchestra.ldapadmin.ws.userdetails;

import java.io.IOException;

import org.georchestra.ldapadmin.ds.AccountDao;
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
 * @author Mauricio Pazos
 *
 */
@Controller
@SessionAttributes(types=EditUserDetailsFormBean.class)
public class EditUserDetailsFormController {

	private AccountDao accountDao;
	
	@Autowired
	public EditUserDetailsFormController( AccountDao dao){
		this.accountDao = dao;
	}
	
	@InitBinder
	public void initForm( WebDataBinder dataBinder) {
		
		dataBinder.setAllowedFields(new String[]{"uid", "surname", "givenName","org", "title", "postalAddress", "postalCode",  "registeredAddress", "postOfficeBox", "physicalDeliveryOfficeName"});
	}
	
	@RequestMapping(value="/public/accounts/userdetails", method=RequestMethod.GET)
	public String setupForm(Model model) throws IOException{

		EditUserDetailsFormBean formBean = new EditUserDetailsFormBean();
		
		
		// TODO retrieve user data uid
		
		
		model.addAttribute(formBean);
		
		return "editUserDetailsForm";
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
	@RequestMapping(value="/public/accounts/userdetails", method=RequestMethod.POST)
	public String generateNewPassword(
						@ModelAttribute EditUserDetailsFormBean formBean, 
						BindingResult resultErrors, 
						SessionStatus sessionStatus) 
						throws IOException {
		
		new EditUserDetailsValidator().validate(formBean, resultErrors);
		
		if(resultErrors.hasErrors()){
			
			return "editUserDetailsForm";
		}
		
//		try {
//			
//			Account account = null;
//			
//			
//			this.accountDao.update(account);
//
//			
//			sessionStatus.setComplete();
//			
//			return "editFinish";
//			
//		} catch (AccountDaoException e) {
//			
//			throw new IOException(e);
//			
//		} catch (NotFoundException e) {
//			
//			resultErrors.rejectValue("email", "mailNoExist", "There is not a user with the provided email.");
//			
//			return "lostPasswordForm";
//			
//		} catch (DuplicatedEmailException e) {
//			throw new IOException(e);
//		}
		return "editUserDetailsForm";
	}
	
	
}

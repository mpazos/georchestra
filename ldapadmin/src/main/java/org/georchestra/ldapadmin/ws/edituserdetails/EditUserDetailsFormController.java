/**
 * 
 */
package org.georchestra.ldapadmin.ws.edituserdetails;

import java.io.IOException;

import org.georchestra.ldapadmin.ds.AccountDao;
import org.georchestra.ldapadmin.ds.AccountDaoException;
import org.georchestra.ldapadmin.dto.Account;
import org.georchestra.ldapadmin.dto.AccountFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
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
		
		dataBinder.setAllowedFields(new String[]{"uid", "surname", "firstName","org", "title", "postalAddress", "postalCode",  "registeredAddress", "postOfficeBox", "physicalDeliveryOfficeName"});
	}
	
	
	/**
	 * Retrieves the account data and sets the model before presenting the edit form view.
	 * 
	 * @param uid
	 * @param model
	 * 
	 * @return the edit form view
	 * 
	 * @throws IOException
	 */
	@RequestMapping(value="/public/accounts/userdetails", method=RequestMethod.GET)
	public String setupForm(@RequestParam("uid") String uid,  Model model) throws IOException{

		try {
			Account account = this.accountDao.findByUID(uid);
			
			EditUserDetailsFormBean formBean = createForm(account);

			model.addAttribute(formBean);
			
			return "editUserDetailsForm";
			
		} catch (Exception e) {
			e.printStackTrace();
			throw new IOException(e);
		} 
	}
	
	/**
	 * Creates a form based on the account data.
	 * 
	 * @param account input data 
	 * @param formBean (out)
	 */
	private EditUserDetailsFormBean createForm(final Account account) {

		EditUserDetailsFormBean formBean = new EditUserDetailsFormBean();
		
		formBean.setUid(account.getUid());
		
		formBean.setFirstName(account.getGivenName());
		formBean.setSurname(account.getSurname());

		formBean.setOrg(account.getOrg());
		formBean.setPhysicalDeliveryOfficeName(account.getPhysicalDeliveryOfficeName());
		formBean.setPostalAddress(account.getPostalAddress());
		formBean.setPostalCode(account.getPostalCode());
		formBean.setPostOfficeBox(account.getPostOfficeBox());
		formBean.setRegisteredAddress(account.getRegisteredAddress());
		formBean.setTitle(account.getTitle());

		return formBean;
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

		// updates the account details 
		try {
			Account account = AccountFactory.createDetails(
					formBean.getUid(),
					formBean.getFirstName(),
					formBean.getOrg(),
					formBean.getPhysicalDeliveryOfficeName(),
					formBean.getPostalAddress(),
					formBean.getPostalCode(),
					formBean.getPostOfficeBox(),
					formBean.getRegisteredAddress(),
					formBean.getSurname(),
					formBean.getTitle()
				);
			
			this.accountDao.update(account);
			
			sessionStatus.setComplete();

			return "editUserDetailsSuccess";
			
		} catch (AccountDaoException e) {
			
			throw new IOException(e);
			
		} catch (Exception e) {
			
			throw new IOException(e);
		} 
	}

	
	
}

package org.georchestra.ldapadmin.ds;

import java.util.Date;
import java.util.List;

import org.georchestra.ldapadmin.dto.Account;

/**
 * Defines the operations to maintain the set of account.
 * 
 * @author Mauricio Pazos
 *
 */
public interface AccountDao {

	
	/**
	 * Returns all accounts
	 * 
	 * @return List of {@link Account}
	 * @throws DataServiceException
	 */
	List<Account> findAll() throws DataServiceException;
	
	/**
	 * Creates a new account
	 * 
	 * @param account
	 * @param groupID
	 * @throws DataServiceException
	 * @throws DuplicatedEmailException
	 * @throws RequiredFiedException
	 */
	void create(final Account account, final String groupID) throws DataServiceException, DuplicatedEmailException;

	/**
	 * Updates the user account
	 * @param account
	 * @throws DataServiceException
	 * @throws DuplicatedEmailException
	 * @throws RequiredFiedException
	 */
	void update(final Account account) throws DataServiceException, DuplicatedEmailException;

	/**
	 * Changes the user password
	 * 
	 * @param uid
	 * @param password
	 * @throws DataServiceException
	 */
	void changePassword(final String uid, final String password)throws DataServiceException;

	/**
	 * Remove the new password
	 * 
	 * @param uid
	 * @throws DataServiceException
	 * @throws NotFoundException
	 */
	void resetNewPassword(final String uid)throws DataServiceException, NotFoundException;

	/**
	 * Delets the account
	 * 
	 * @param account
	 * @throws DataServiceException
	 * @throws NotFoundException
	 */
	void delete(final Account account) throws DataServiceException, NotFoundException;

	/**
	 * Returns the account that contains the uid provided as parameter.
	 * 
	 * @param uid
	 * 
	 * @return {@link Account}
	 * 
	 * @throws DataServiceException
	 * @throws NotFoundException
	 */
	Account findByUID(final String uid)throws DataServiceException, NotFoundException;

	/**
	 * Returns the account that contains the email provided as parameter.
	 * 
	 * @param email
	 * @return {@link Account}
	 * 
	 * @throws DataServiceException
	 * @throws NotFoundException
	 */
	Account findByEmail(final String email) throws DataServiceException, NotFoundException;
	

	/**
	 * Returns the accounts which new password befor the date
	 *  
	 * @param date
	 * 
	 * @return {@link Account}
	 */
	List<Account> findNewPasswordBeforeDate(final Date date);

	
	/**
	 * Add the new password. This method is part of the "lost password" workflow to maintan the old password and the new password until the
	 * user can confirm that he had asked for a new password.   
	 * 
	 * @param udi
	 * @param newPassword
	 */
	void addNewPassword(String uid, String newPassword);
}

package org.georchestra.ldapadmin.ds;

import java.util.Date;
import java.util.List;

import org.georchestra.ldapadmin.dto.Account;

/**
 * 
 * @author Mauricio Pazos
 *
 */
public interface AccountDao {

	List<Account> findAll() throws DataServiceException;
	
	void create(final Account account, final boolean pending) throws DataServiceException, DuplicatedEmailException, RequiredFiedException;

	void update(final Account account) throws DataServiceException, DuplicatedEmailException, RequiredFiedException;

	void changePassword(String uid, String password)throws DataServiceException;

	void resetNewPassword(final String uid)throws DataServiceException, NotFoundException;

	void delete(final Account account) throws DataServiceException, NotFoundException;

	Account findByUID(final String uid)throws DataServiceException, NotFoundException;

	Account findByEmail(String email) throws DataServiceException, NotFoundException;
	

	List<Account> findNewPasswordBeforeDate(Date date);
}

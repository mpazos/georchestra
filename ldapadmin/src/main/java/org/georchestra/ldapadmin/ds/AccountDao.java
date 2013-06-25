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

	List<Account> findAll() throws AccountDaoException;
	
	void create(final Account account, final boolean pending) throws AccountDaoException, DuplicatedEmailException, RequiredFiedException;

	void update(final Account account) throws AccountDaoException, DuplicatedEmailException, RequiredFiedException;

	void changePassword(String uid, String password)throws AccountDaoException;

	void resetNewPassword(final String uid)throws AccountDaoException, NotFoundException;

	void delete(final Account account) throws AccountDaoException, NotFoundException;

	Account findByUID(final String uid)throws AccountDaoException, NotFoundException;

	Account findByEmail(String email) throws AccountDaoException, NotFoundException;
	
	List<String> findAllGroups() throws AccountDaoException;

	List<Account> findNewPasswordBeforeDate(Date date);




}

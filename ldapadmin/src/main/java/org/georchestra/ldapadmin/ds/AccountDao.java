package org.georchestra.ldapadmin.ds;

import java.util.List;

import org.georchestra.ldapadmin.dto.Account;

/**
 * 
 * @author Mauricio Pazos
 *
 */
public interface AccountDao {

	List<Account> findAll() throws AccountDaoException;
	
	void create(final Account account, final boolean pending) throws AccountDaoException, DuplicatedEmailException;

	void update(final Account account) throws AccountDaoException, DuplicatedEmailException;

	void delete(final Account account) throws AccountDaoException, NotFoundException;

	Account findByUID(final String uid)throws AccountDaoException, NotFoundException;

	Account findByEmail(String email) throws AccountDaoException, NotFoundException;

	List<String> findAllGroups() throws AccountDaoException;


}

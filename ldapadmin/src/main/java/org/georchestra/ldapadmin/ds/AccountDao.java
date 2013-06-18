package org.georchestra.ldapadmin.ds;

import java.util.List;

import org.georchestra.ldapadmin.dto.Account;

public interface AccountDao {

	List<Account> findAll();

	void create(Account account);

	void update(Account account);

	void delete(Account account);

	Account findByUID(String uid);

}

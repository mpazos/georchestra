package org.georchestra.ldapadmin.ds;

import java.util.List;

import org.georchestra.ldapadmin.dto.Account;

public interface AccountDao {
	
	List<Account> findAll();


}

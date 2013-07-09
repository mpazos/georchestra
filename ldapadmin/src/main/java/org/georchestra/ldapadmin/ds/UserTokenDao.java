package org.georchestra.ldapadmin.ds;

import java.util.HashMap;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.georchestra.ogcservstatistics.dataservices.DataServicesConfiguration;


/**
 * Maintains the tokens generated when the "Lost password use case" is executed.
 * 
 * <p>
 * 
 * 
 * </p>
 * 
 * @author Mauricio Pazos
 *
 */
public class UserTokenDao {
	 
	private static final Log LOG = LogFactory.getLog(UserTokenDao.class.getName());
	
	private DataServicesConfiguration dataServiceConfiguration = DataServicesConfiguration.getInstance();
	
	private String databaseUser;
	private String databasePassword;
	private String jdbcURL;

	public String getDatabaseUser() {
		return databaseUser;
	}

	public void setDatabaseUser(String databaseUser) {
		this.databaseUser = databaseUser;
	}

	public String getDatabasePassword() {
		return databasePassword;
	}


	public void setDatabasePassword(String databasePassword) {
		this.databasePassword = databasePassword;
	}

	public String getJdbcURL() {
		return jdbcURL;
	}


	public void setJdbcURL(String jdbcURL) {
		this.jdbcURL = jdbcURL;
	}


	public void addToken(String uid, String token) throws DataServiceException {
		
		if(LOG.isDebugEnabled()){
			String msg = "DatabaseUser: " + databaseUser +"; " + "DatabasePassword: " + databasePassword +"; " + "JdbcURL: " + jdbcURL;
			LOG.debug(msg);
		}
		try {
			InsertUserTokenCommand cmd = new InsertUserTokenCommand();
			cmd.setConnection(this.dataServiceConfiguration .getConnection());
			Map<String, String> row = new HashMap<String, String>(2);
			row.put(InsertUserTokenCommand.UID_COLUMN, uid);
			row.put(InsertUserTokenCommand.TOKEN_COLUMN,  token);
			cmd.setRowValues( row );
			cmd.execute();

		} catch (Exception e) {

			LOG.error("Failed to insert the uid,token", e);
			
			throw new DataServiceException(e);
		}
		
	}
	
	
	

}

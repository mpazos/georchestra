/**
 * 
 */
package org.georchestra.ldapadmin.ds;

import java.sql.PreparedStatement;
import java.sql.SQLException;


/**
 * Command to search a row, in the user_token table, by uid.
 * 
 * @author Mauricio Pazos
 *
 */
class QueryByUidCommand extends org.georchestra.ldapadmin.ds.AbstractQueryCommand {

	private String uid;
	
	public void setUid(final String uid){
		this.uid = uid;
	}
			
	
	/**
	 * builds the sql query 
	 * 
	 * @return the sql statement
	 */
	private String getSQLStatement(){

		StringBuilder sql = new StringBuilder();

		sql.append(" SELECT ")
				.append(DatabaseSchema.UID_COLUMN).append(",").append(DatabaseSchema.TOKEN_COLUMN ).append(",").append(DatabaseSchema.CREATEION_DATE_COLUMN )
				.append(" FROM ").append(DatabaseSchema.TABLE_USER_TOKEN)
				.append(" WHERE uid = ?");
		
		return sql.toString();
	}
	
	
	/**
	 * Prepares the Statement setting the year and month.
	 */
	@Override
	protected PreparedStatement prepareStatement() throws SQLException {

		PreparedStatement pStmt = this.connection.prepareStatement(getSQLStatement());

		pStmt.setString(1, this.uid);

		return pStmt;
	}


	
}

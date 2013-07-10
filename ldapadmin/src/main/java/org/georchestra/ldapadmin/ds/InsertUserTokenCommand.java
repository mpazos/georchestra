/**
 * 
 */
package org.georchestra.ldapadmin.ds;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Map;

import org.georchestra.ogcservstatistics.dataservices.AbstractDataCommand;
import org.georchestra.ogcservstatistics.dataservices.DataCommandException;

/**
 * Inserts the <b>token</b> associated to the user in the table "USER_TOKEN".
 * 
 * @author Mauricio Pazos
 */
final class InsertUserTokenCommand extends AbstractDataCommand {

	public final static String UID_COLUMN = "uid";
	public final static String TOKEN_COLUMN = "token";
	public final static String TIMESTAMP_COLUMN = "timeStamp";

	private static final String SQL_INSERT= "INSERT INTO USER_TOKEN("+UID_COLUMN+","+ TOKEN_COLUMN+ ","+TIMESTAMP_COLUMN+") VALUES (?, ?, ?)";
	
	private Map<String, Object> rowValues;

	/**
	 * Sets the uid and token in the command.
	 * To
	 * 
	 * @param row (UID_COLUMN, value)(TOKEN_COLUMN, value) (TIMESTAMP_COLUMN, value)
	 */
	public void setRowValues(final Map<String, Object> row) {

		assert row.keySet().size() == 2;
		
		this.rowValues = row;
	}

	
	private PreparedStatement prepareStatement() throws SQLException {

        assert this.connection != null: "database connection is null, use setConnection";

        PreparedStatement pStmt = this.connection.prepareStatement(SQL_INSERT);

        pStmt.setString(1, (String)this.rowValues.get(UID_COLUMN));
		pStmt.setString(2, (String)this.rowValues.get(TOKEN_COLUMN));
		pStmt.setString(3, (String)this.rowValues.get(TIMESTAMP_COLUMN));
		
		return pStmt;
	}
	

	/**
	 * Execute the sql insert to add the new row (uid, token, timestamp)
	 *  
	 * @see org.georchestra.ogcservstatistics.dataservices.DataCommand#execute()
	 */
	@Override
	public void execute() throws DataCommandException {
        assert this.connection != null: "database connection is null, use setConnection";

        // executes the sql statement and checks that the update operation will be inserted one row in the table
        PreparedStatement pStmt=null;
        try {
        	this.connection.setAutoCommit(false);
            pStmt = prepareStatement();
            int updatedRows = pStmt.executeUpdate();
            this.connection.commit();
            
            if(updatedRows < 1){
                throw new DataCommandException("Failed inserting in USER_TOKEN table. " + pStmt.toString());
            }

        } catch (SQLException e) {
        	if(this.connection != null){
        		try {
					this.connection.rollback();
				} catch (SQLException e1) {
					e1.printStackTrace();
		            throw new DataCommandException(e.getMessage());
				}
	            throw new DataCommandException(e.getMessage());
        	}
        } finally{
            try {
                if(pStmt != null) pStmt.close();
            	this.connection.setAutoCommit(true);
                
            } catch (SQLException e1) {
                throw new DataCommandException(e1.getMessage());
            } 
        }
	}

}

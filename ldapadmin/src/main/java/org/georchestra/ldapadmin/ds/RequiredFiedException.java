/**
 * 
 */
package org.georchestra.ldapadmin.ds;

/**
 * This exception is thrown if a required field is not provided.
 * 
 * @author Mauricio Pazos
 *
 */
public class RequiredFiedException extends Exception {


	/**
	 * 
	 */
	private static final long serialVersionUID = -8057979013332888767L;

	public RequiredFiedException() {
		super();
	}
	
	public RequiredFiedException(Throwable e) {
		super(e);
	}
	
    public RequiredFiedException(String message, Throwable cause) {
        super(message, cause);
    }
	
	public RequiredFiedException(String message) {
		super(message);
	}

	
	
}

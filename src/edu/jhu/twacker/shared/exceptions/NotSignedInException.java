/**
 * 
 */
package edu.jhu.twacker.shared.exceptions;

import java.io.Serializable;

/**
 * @author Disa Mhembere
 *
 */
public class NotSignedInException extends Exception implements Serializable
{

	/**
	 * 
	 */
	private static final long serialVersionUID = -91098232192146742L;

	
	/**
	 * Default constructor same as {@link Exception}
	 */
	public NotSignedInException()
	{
		super();
	}
	
	/**
	 * Call {@link Exception} class constructor
	 * @param message
	 */
	public NotSignedInException(String message)
	{
		super(message);
	}
	
	
}

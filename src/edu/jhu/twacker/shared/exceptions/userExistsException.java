/**
 * OOSE Project - Group 4
 * {@link userExistsException}.java 
 */

package edu.jhu.twacker.shared.exceptions;
import java.io.Serializable;
/**
 * This class is used for registration purposes. It is
 * thrown when a user attempts to make a same username as another
 * @author Disa Mhembere
 *
 */
@SuppressWarnings("serial")
public class userExistsException extends Exception implements Serializable
{
	/**
	 * Default constructor same as {@link Exception}
	 */
	public userExistsException()
	{
		super();
	}
	
	/**
	 * Call {@link Exception} class constructor
	 * @param message
	 */
	public userExistsException(String message)
	{
		super(message);
	}
}

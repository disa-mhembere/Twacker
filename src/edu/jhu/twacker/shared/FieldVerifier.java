/** 
 * OOSE Project - Group 4
 * FieldVerifier.java
 */
 
package edu.jhu.twacker.shared;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * FieldVerifier validates that the name the user enters is valid.
 * @author Disa Mhembere
 */
public class FieldVerifier
{

	/**
	 * Validates whether user name is valid based on our
	 * criteria for validity
	 * @param name the name of user
	 * @return true if valid else false
	 */
	public static boolean isValidUserName(String name)
	{
		
		Pattern p = Pattern.compile("[^a-zA-z0-9]");
	    Matcher m = p.matcher(name);
		
		if (name == null || m.find() || name.length() < 3 )
		{
			return false;
		}
		return true;
	}
	
	/**
	 * Validate whether a password is permissible or not
	 * @param password
	 * @return true is valid else false
	 */
	public static boolean isValidPassword(String password)
	{
		Pattern p = Pattern.compile("\\s");
	    Matcher m = p.matcher(password);
		
		if (password == null || m.find() || password.length() < 5 )
		{
			return false;
		}
		return true;
	}
}

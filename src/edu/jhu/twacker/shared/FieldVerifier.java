/** 
 * OOSE Project - Group 4
 * {@link FieldVerifier}.java
 */

package edu.jhu.twacker.shared;

import java.util.regex.Matcher;
import java.util.regex.Pattern;



/**
 * FieldVerifier validates that the name the user enters is valid.
 * 
 * @author Disa Mhembere, Alex Long
 */
public class FieldVerifier
{

	public final static String PRIMARY_DEFAULT = "(required)";
	public final static String SECONDARY_DEFAULT = "(optional)";
	
	/**
	 * Verify that an email address provided is at least of valid format
	 * @param emailAdd the users email address
	 * @return true is email address matches a known pattern for email addresses
	 */
	public static boolean isValidEmail(String emailAdd)
	{
		Pattern p = Pattern.compile(".+@.+\\..+");
		Matcher m = p.matcher(emailAdd);
		return m.matches();
	}

	/**
	 * Validates whether user name is valid based on our criteria for validity
	 * @param name the name of user
	 * @return true if valid else false
	 */
	public static boolean isValidUserName(String name)
	{

		Pattern p = Pattern.compile("[^a-zA-Z0-9]"); // No special Characters
		Matcher m = p.matcher(name);

		if (name == null || m.find() || name.length() < 3)
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
		Pattern p = Pattern.compile("\\s"); // No spaces in password
		Matcher m = p.matcher(password);

		return !(password == null || m.find() || password.length() < 5);
	}
	
	/**
	 * Validate whether a users first name OR last name are 
	 * reasonable to insert
	 * @param name
	 * @return
	 */
	public static boolean isValidName(String name)
	{
		Pattern p = Pattern.compile("[A-Z][a-z]*|[a-z]+"); // No special Characters
		Matcher m = p.matcher(name);
		
		return (m.matches());
	}
	
	/**
	 * Verifies that the search term is not the default or empty.
	 * The only other requirement is that the String is at least 3 characters.
	 * 
	 * @param search the user's search term
	 * @return true if valid, else false
	 */
	public static boolean isValidSearch(String search) 
	{
		if (search == null || search.equals(PRIMARY_DEFAULT) || search.equals(SECONDARY_DEFAULT)) {
			return false;
		}
		return search.length() >= 3;
	}
}

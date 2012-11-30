/**
 * OOSE Project - Group 4
 * {@link AuthService}.java 
 */

package edu.jhu.twacker.client.service;

import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;


/**
 * Web service which implements an authentication interface & sign-in
 * verification framework
 * @author Disa Mhembere
 */
@RemoteServiceRelativePath("AuthService")
public interface AuthService extends RemoteService
{

	/**
	 * Get the username of the user signed in based on httpsession data
	 * if not signed in return "guest"
	 * @return
	 */
	public String getUserName();
	
	/**
	 * Determine if the user is signed in
	 * @return true if user is signed in else return false
	 */
	public boolean isSignedIn();
	
	/**
	 * Sign in to the web application
	 * @param username the username that a user created the account with
	 * @param password the password that a user created the account with
	 * @return the username of the user signed in now
	 */
	public String signIn(String username, String password);
	
	/**
	 * Sign out of the web application & disable all personal aspects of the site
	 * @return sanity check to ensure "guest" is returned
	 */
	public String signOut();
	
	/**
	 * Set session variable for username to specified username
	 * @param username
	 */
	public void setUsername(String username);
	
}

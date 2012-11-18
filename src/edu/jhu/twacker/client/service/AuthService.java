/**
 * OOSE Project - Group 4
 * AuthService.java 
 */

package edu.jhu.twacker.client.service;

import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;

import edu.jhu.twacker.shared.Session;

/**
 * 
 * @author Disa Mhembere
 */
@RemoteServiceRelativePath("AuthService")
public interface AuthService extends RemoteService {
	
	/**
	 * Signup with new a username and password
	 * @param username The user's requested username
	 * @param password The user's password
	 * @return current session object
	 * @throws Exception upon failure to create new account 
	 */
	Session signUp(String username, String password) throws Exception;
	 
	/** 
	 * Try to sign in a user with given credentials
	 * @param username The users username
	 * @param password The users password
	 * @return the session ID
	 * @throws Exception on sign in failure
	 */
	Session signIn(String username, String password) throws Exception;
		
	/** 
	 * Uses the sessionID to check if the user is logged in or not
	 * @param the sessionID 
	 * @return sign in status
	 * @throws Exception if not signed in
	 */
	boolean isSignedIn(String sessionID) throws Exception;
}



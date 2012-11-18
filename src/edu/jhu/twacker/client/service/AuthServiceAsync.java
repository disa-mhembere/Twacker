/**
 * OOSE Project - Group 4
 * AuthServiceAsync.java
 */

package edu.jhu.twacker.client.service;

import com.google.gwt.user.client.rpc.AsyncCallback;
import edu.jhu.twacker.shared.Session;

/**
 * This is for the callback for the rpc call in Auth Service
 * @author Disa Mhembere
 *
 */
public interface AuthServiceAsync
{

	/**
	 * 
	 * @see edu.jhu.twacker.client.service.AuthService#isSignedIn(java.lang.String)
	 */
	void isSignedIn(String sessionID, AsyncCallback<Boolean> callback);

	/**
	 * 
	 * @see edu.jhu.twacker.client.service.AuthService#signIn(java.lang.String, java.lang.String)
	 */
	void signIn(String username, String password, AsyncCallback<Session> callback);

	/**
	 * 
	 * @see edu.jhu.twacker.client.service.AuthService#signUp(java.lang.String, java.lang.String)
	 */
	void signUp(String username, String password,
			AsyncCallback<Session> callback);

}

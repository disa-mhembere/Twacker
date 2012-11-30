/** 
 * OOSE Project - Group 4
 * {@link AuthServiceAsync}.java
 */

package edu.jhu.twacker.client.service;

import com.google.gwt.user.client.rpc.AsyncCallback;

/**
 * @author Disa Mhembere
 *
 */
public interface AuthServiceAsync
{

	/**
	 * 
	 * @see edu.jhu.twacker.client.service.AuthService#getUserName()
	 */
	void getUserName(AsyncCallback<String> callback);

	/**
	 * 
	 * @see edu.jhu.twacker.client.service.AuthService#isSignedIn()
	 */
	void isSignedIn(AsyncCallback<Boolean> callback);

	/**
	 * 
	 * @see edu.jhu.twacker.client.service.AuthService#signIn(java.lang.String, java.lang.String)
	 */
	void signIn(String username, String password, AsyncCallback<String> callback);

	/**
	 * 
	 * @see edu.jhu.twacker.client.service.AuthService#signOut()
	 */
	void signOut(AsyncCallback<String> callback);
	
	/**
	 * 
	 * @see edu.jhu.twacker.client.service.AuthService#setUsername()
	 */
	void setUsername(String username, AsyncCallback<Void> callback);

}

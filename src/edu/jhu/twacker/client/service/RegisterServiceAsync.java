/**
 * OOSE Project - Group 4
 * {@link RegisterService}.java
 */
 package edu.jhu.twacker.client.service;

import com.google.gwt.user.client.rpc.AsyncCallback;

/**
 * @author Disa Mhembere
 *
 */
public interface RegisterServiceAsync
{

	/**
	 * 
	 * @see edu.jhu.twacker.client.service.RegisterService#registerUser(java.lang.String, java.lang.String, java.lang.String, java.lang.String, java.lang.String)
	 */
	void registerUser(String firstName, String lastName, String username,
			String password, String email, AsyncCallback<Void> callback);

}

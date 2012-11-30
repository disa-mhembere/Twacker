/**
 * 
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

}

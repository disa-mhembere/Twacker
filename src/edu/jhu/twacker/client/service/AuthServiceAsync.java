/**
 * 
 */
package edu.jhu.twacker.client.service;

import com.google.gwt.user.client.rpc.AsyncCallback;

import edu.jhu.twacker.client.data.AuthInfo;

/**
 * @author Disa Mhembere
 *
 */
public interface AuthServiceAsync
{

	/**
	 * 
	 * @see edu.jhu.twacker.client.service.AuthService#signIn(java.lang.String)
	 */
	void signIn(String requestUri, AsyncCallback<AuthInfo> callback);

}

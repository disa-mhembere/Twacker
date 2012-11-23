/**
 * OOSE Project - Group 4
 * AuthService.java 
 */

package edu.jhu.twacker.client.service;

import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;

import edu.jhu.twacker.client.data.AuthInfo;

/**
 * 
 * @author Disa Mhembere
 */
@RemoteServiceRelativePath("AuthService")
public interface AuthService extends RemoteService
{

	public AuthInfo signIn(String requestUri);
}

/**
 * OOSE Project - Group 4
 * AuthService.java 
 */

package edu.jhu.twacker.client.service;

import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;


/**
 * 
 * @author Disa Mhembere
 */
@RemoteServiceRelativePath("AuthService")
public interface AuthService extends RemoteService
{

	public String getUserName();
}

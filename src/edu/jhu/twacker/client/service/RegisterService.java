/**
 * OOSE Project - Group 4
 * {@link RegisterService}.java
 */
package edu.jhu.twacker.client.service;

import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;
import com.google.gwt.user.client.rpc.RemoteService;

import edu.jhu.twacker.shared.exceptions.userExistsException;

/**
 * Class for registering a user to the Twacker web app
 * @author Disa Mhembere
 */
@RemoteServiceRelativePath("Register")
public interface RegisterService extends RemoteService
{
	/**
	 * Register user with personal info & requested credentials 
	 * @param firstName users first name
	 * @param lastName users last name
	 * @param username chosen unique user name
	 * @param password chosen password
	 * @param email
	 */
	public void registerUser(String firstName, String lastName, String username, String password, String email) throws userExistsException;
	
}

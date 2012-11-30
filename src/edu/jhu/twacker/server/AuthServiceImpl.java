/**
 * OOSE Project - Group 4
 * {@link AuthService}.java
 */
package edu.jhu.twacker.server;
import javax.servlet.http.HttpSession;

import edu.jhu.twacker.client.service.AuthService;
import com.google.gwt.user.server.rpc.RemoteServiceServlet;

/**
 * Server side implementation of authService
 * 
 * @author Disa Mhembere
 */
@SuppressWarnings("serial")
public class AuthServiceImpl extends RemoteServiceServlet implements
		AuthService
{
	/**
	 * @see edu.jhu.twacker.client.service.AuthService#signIn(java.lang.String)
	 */
	@Override
	public String getUserName()
	{
		HttpSession httpSession = getThreadLocalRequest().getSession(true);
		return httpSession.getAttribute("username").toString() ;
	}
	
	/**
	 * Associate a user with the current httpSession
	 * thus persisting user session data
	 * @param username the new username created  
	 */
	public void setUsername(String username)
	{
	     HttpSession httpSession = getThreadLocalRequest().getSession(true);
	     httpSession.setAttribute("username", username);
	}


}

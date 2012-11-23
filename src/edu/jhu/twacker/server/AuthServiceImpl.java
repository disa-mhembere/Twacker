/**
 * OOSE Project - Group 4
 * AuthServiceImpl.java
 */
package edu.jhu.twacker.server;

//import java.util.Date;
//import java.util.HashMap;
//import java.util.Map;
//import java.util.UUID;
//import edu.jhu.twacker.shared.Session;
import com.google.appengine.api.users.User;
import com.google.appengine.api.users.UserService;
import com.google.appengine.api.users.UserServiceFactory;

import edu.jhu.twacker.client.data.AuthInfo;
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
	 * For Serialization
	 */

	//private Map<String, String> users = new HashMap<String, String>(); // user to
																								// hashed
																								// password
																								// mapping
	//{
	//	users.put("username", "password"); // Single user with username
	//}

	/**
	 * @see edu.jhu.twacker.client.service.AuthService#signIn(java.lang.String)
	 */
	@Override
	public AuthInfo signIn(String requestUri)
	{
		UserService userService = UserServiceFactory.getUserService();
		User user = userService.getCurrentUser();
		AuthInfo loginInfo = new AuthInfo();

		if (user != null)
		{
			loginInfo.setLoggedIn(true);
			loginInfo.setEmailAddress(user.getEmail());
			loginInfo.setNickname(user.getNickname());
			loginInfo.setLogoutUrl(userService.createLogoutURL(requestUri));
		} else
		{
			loginInfo.setLoggedIn(false);
			loginInfo.setLoginUrl(userService.createLoginURL(requestUri));
		}
		return loginInfo;
	}

}

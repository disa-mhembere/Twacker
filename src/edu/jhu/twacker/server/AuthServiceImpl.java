/**
 * OOSE Project - Group 4
 * {@link AuthService}.java
 */
package edu.jhu.twacker.server;

import java.util.List;

import javax.jdo.JDOHelper;
import javax.jdo.PersistenceManager;
import javax.jdo.PersistenceManagerFactory;
import javax.jdo.Query;
import javax.servlet.http.HttpSession;

import edu.jhu.twacker.client.service.AuthService;
import edu.jhu.twacker.server.data.Users;

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
	private static final PersistenceManagerFactory PMF = JDOHelper
			.getPersistenceManagerFactory("transactions-optional");

	/**
	 * @see edu.jhu.twacker.client.service.AuthService#signIn(java.lang.String)
	 */
	@Override
	public String getUserName()
	{
		HttpSession httpSession = getThreadLocalRequest().getSession(true);

//		if (httpSession.getAttribute("username") == null)
//		{
//			setUsername("guest");
//			return getUserName(); // Default user when a user decides to not log in
//		}
		return httpSession.getAttribute("username").toString();
	}

	@Override
	/**
	 * 
	 * @see edu.jhu.twacker.client.service.AuthService#isSignedIn()
	 */
	public boolean isSignedIn()
	{
		// TODO DM Auto-generated method stub
		return false;
	}

	@Override
	/**
	 * 
	 * @see edu.jhu.twacker.client.service.AuthService#signIn(java.lang.String, java.lang.String)
	 */
	public String signIn(String username, String password)
	{
		PersistenceManager pm = PMF.getPersistenceManager(); // TODO: PersistenceManager pm = PMF.getPersistenceManager("transactions-optional");
		Query q = pm.newQuery(Users.class);
		q.setFilter("username == usernameParam && password == passwordParam");

		q.declareParameters("String usernameParam, String passwordParam");

		try
		{
			@SuppressWarnings("unchecked")
			List<Users> authenticatedUser = (List<Users>) q.execute(username, password);
			if (authenticatedUser.size() > 0) // This guy is in the DB
			{
				setUsername(username); // Set session username
				return getUserName(); // Return session username
			}
		} finally
		{
			pm.close();
		}

		// If all that fails then blow up for now // TODO DM: Add
		// AuthFailureException
		return null;
	}

	/**
	 * 
	 * @see edu.jhu.twacker.client.service.AuthService#signOut()
	 */
	@Override
	public String signOut()
	{
		// TODO DM Auto-generated method stub
		return null;
	}

	/**
	 * 
	 * @see edu.jhu.twacker.client.service.AuthService#setUsername(String)
	 */
	@Override
	public void setUsername(String username)
	{
		HttpSession httpSession = getThreadLocalRequest().getSession(true);
		httpSession.setAttribute("username", username);
	}
}

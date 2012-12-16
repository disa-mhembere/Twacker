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
import edu.jhu.twacker.shared.exceptions.NotSignedInException;
import edu.jhu.twacker.shared.security.BCrypt;

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
	public String getUsername()
	{
		HttpSession httpSession = getThreadLocalRequest().getSession(true);

		if (httpSession.getAttribute("username") == null)
		{
			setUsername("guest");
			return getUsername(); // Default user when a user decides to not log in
		}
		return httpSession.getAttribute("username").toString();
	}

	@Override
	/**
	 * Determines if a user is signed in or not
	 * @see edu.jhu.twacker.client.service.AuthService#isSignedIn()
	 */
	public boolean isSignedIn()
	{
		return !getUsername().toString().equals("guest");
	}

	@Override
	/**
	 * 
	 * @see edu.jhu.twacker.client.service.AuthService#signIn(java.lang.String, java.lang.String)
	 */
	public String signIn(String username, String password)
	{
		PersistenceManager pm = PMF.getPersistenceManager(); 
		Query q = pm.newQuery(Users.class);
		q.setFilter("username == usernameParam");

		q.declareParameters("String usernameParam");

		try
		{
			@SuppressWarnings("unchecked")
			List<Users> authenticatedUser = (List<Users>) q.execute(username);
			
			// Since there is only 1 value since Username is primary key
			if (authenticatedUser.size() == 0)
			{
				; // Do nothing
			}
			
			else
			{
				if (BCrypt.checkpw(password, authenticatedUser.get(0).getPassword()))
				{
					setUsername(username); // Set session username
					return getUsername(); // Return session username
				}
			}
		} finally
		{
			pm.close();
		}
		return null;
	}

	/**
	 * Signs a user out i.e sets the username to "guest" again
	 * @see edu.jhu.twacker.client.service.AuthService#signOut()
	 */
	@Override
	public String signOut() throws NotSignedInException
	{
		setUsername("guest");
		return getUsername();
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

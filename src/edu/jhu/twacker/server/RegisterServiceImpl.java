/**
 * OOSE Project - Group 4
 * {@link RegisterServiceImpl}.java
 */
package edu.jhu.twacker.server;

import java.util.List;

import javax.jdo.JDOHelper;
import javax.jdo.JDOObjectNotFoundException;
import javax.jdo.PersistenceManager;
import javax.jdo.PersistenceManagerFactory;
import com.google.appengine.api.datastore.Key;
import com.google.appengine.api.datastore.KeyFactory;
import javax.jdo.Query;
import javax.servlet.http.HttpSession;

import com.google.gwt.user.server.rpc.RemoteServiceServlet;

import edu.jhu.twacker.client.service.RegisterService;
import edu.jhu.twacker.server.data.Users;
import edu.jhu.twacker.shared.exceptions.userExistsException;
import edu.jhu.twacker.shared.security.BCrypt;

/**
 * This class implements the register service by attempting to add
 * a user to the datastore 
 * @author Disa Mhembere
 *
 */
@SuppressWarnings("serial")
public class RegisterServiceImpl extends RemoteServiceServlet implements RegisterService
{
	private static final PersistenceManagerFactory PMF =
	      JDOHelper.getPersistenceManagerFactory("transactions-optional");
	
	@Override
	/**
	 * 
	 * @see edu.jhu.twacker.client.service.RegisterService#registerUser(java.lang.String, java.lang.String, java.lang.String, java.lang.String, java.lang.String)
	 */
	public void registerUser(String firstName, String lastName, String username,
			String password, String email) throws userExistsException
	{
		createEnitityUserIfNotExsists();
		
		// If the user exists throw this exception
		
		if (checkUserExists(username, email))
		{
			throw new userExistsException();
		}
		
		// Else add the user to the DB
		else
		{
			PersistenceManager pm = getPersistenceManager();
			try
			{
				pm.makePersistent(new Users(firstName, lastName, username,
						BCrypt.hashpw(password, BCrypt.gensalt()), email)); // Hash password for security
				setUsername(username); 
			} finally
			{
				pm.close();
			}
		}
		
	}
	
	/**
	 * Check if a user with the requested credentials already exists
	 * @param username the users requested username
	 * @param email the users requested email address
	 * @return true if the user exists else false
	 */
	private boolean checkUserExists(String username, String email)
	{
		PersistenceManager pm = getPersistenceManager();
		Query q = pm.newQuery(Users.class);
		q.setFilter("username == usernameParam");

		q.declareParameters("String usernameParam");
		try
		{
			@SuppressWarnings("unchecked")
			List<Users> guestList = (List<Users>) q.execute(username);
			if (guestList.size() > 0)
			{
				return true; // User exists
			}
		} finally
		{
			pm.close();
		}
		
		return false;
	}
	
	/**
	 * Get the persistence manager instance
	 * @return the persistence manager instance
	 */
	private PersistenceManager getPersistenceManager()
	{
		return PMF.getPersistenceManager();
	}
	
	/**
	 * Check if the Users table exists, if it does not then
	 * create the users table & pass in the guest user
	 */
	private void createEnitityUserIfNotExsists()
	{
		try 
		{
		PersistenceManager pm = getPersistenceManager();
		Key k = KeyFactory.createKey(Users.class.getSimpleName(), "guest"); // Pass primary key as username "guest"
	   
		@SuppressWarnings("unused")
		Users u = pm.getObjectById(Users.class, k); // Intentionally used. Just here to throw exception
		}
		catch (JDOObjectNotFoundException e)
		{
			PersistenceManager pm = getPersistenceManager();
		    try {
		      pm.makePersistent(new Users("","","guest","",""));
		    } finally {
		      pm.close();
		    }
		}		
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

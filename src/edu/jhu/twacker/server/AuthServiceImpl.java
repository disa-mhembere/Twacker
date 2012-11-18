/**
 * OOSE Project - Group 4
 * AuthServiceImpl.java
 */
package edu.jhu.twacker.server;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import com.google.gwt.user.server.rpc.RemoteServiceServlet;

import edu.jhu.twacker.client.service.AuthService;
import edu.jhu.twacker.shared.Session;

/**
 * Server side implementation of authService
 * @author Disa Mhembere
 */
@SuppressWarnings("serial")
public class AuthServiceImpl extends RemoteServiceServlet implements AuthService {
	
	/**
	 * For Serialization
	 */
	
	private Map<String, String> users = new HashMap<String, String>(); //user to hashed password mapping
	private Map<String, Date> sessionIDs = new HashMap<String, Date>(); //Session ID to User mapping
	private final static long COOKIE_RETENTION_TIME = 1000 * 60 * 60 * 24;//1000 msecs * 60 secs * 60 minutes * 24 hours = 1 day
	{
		users.put("username", "password"); //Single user with username
	}
	
	/**
	 * @see edu.jhu.twacker.client.service.AuthService#signIn(java.lang.String, java.lang.String)
	 */
	@Override
	public Session signIn(String username, String password)
			throws Exception
	{
		if (username == null || password == null)
		{
			throw new Exception();
		}

		if (users.get(username) != null && users.get(username).equals(password))
		{
			String sessionID = UUID.randomUUID().toString();
			Session session = new Session();
			session.setSessionID(sessionID);
			Date expiration = new Date(System.currentTimeMillis() + COOKIE_RETENTION_TIME);
			
			session.setUsername(username);
			sessionIDs.put(sessionID, expiration);
			return session;
		}
		throw new Exception();
	}

	/**
	 * @see edu.jhu.twacker.client.service.AuthService#isSignedIn(java.lang.String)   
	 */
	@Override
	public boolean isSignedIn(String sessionID) throws Exception
	{
		if (sessionID == null)
		{
			throw new Exception();
		}
		return sessionIDs.containsKey(sessionID)
				&& sessionIDs.get(sessionID).after(new Date());
	}

	/**
	 * @see edu.jhu.twacker.client.service.AuthService#signUp(java.lang.String, java.lang.String)
	 */
	@Override
	public Session signUp(String username, String password) throws Exception
	{
		/*
		if (username == null || password == null)
		{
			throw new Exception();
		}
		if (!FieldVerifier.isValidUserNameAndPassword(username, password))
		{
			throw new Exception();
		}
		if (users.containsKey(username))
		{
			throw new UserExistsException();
		}
		*/
		
		// TODO : DM 
		users.put(username, password);
		try
		{
			return signIn(username, password);
		} catch (Exception e)
		{
			throw new Exception();
		}

	}

}

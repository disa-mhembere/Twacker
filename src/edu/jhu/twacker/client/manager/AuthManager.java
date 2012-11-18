/**
 * OOSE Project - Group 4
 * AuthManager.java
 */
package edu.jhu.twacker.client.manager;

import java.util.ArrayList;
import java.util.List;

import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.Cookies;
import com.google.gwt.user.client.rpc.AsyncCallback;

import edu.jhu.twacker.client.event.AuthActionListener;
import edu.jhu.twacker.client.service.AuthService;
import edu.jhu.twacker.client.service.AuthServiceAsync;
import edu.jhu.twacker.shared.FieldVerifier;
import edu.jhu.twacker.shared.Session;

/**
 * Manages authentication requests
 * @author Disa Mhembere
 */
public class AuthManager
{

	private static AuthManager instance;
	private List<AuthActionListener> signListeners;
	private AuthServiceAsync service = GWT.create(AuthService.class);

	/**
	 * Private default constructor for the singleton pattern
	 */
	private AuthManager()
	{
		if (service == null)
		{
			service = GWT.create(AuthService.class);
		}
		signListeners = new ArrayList<AuthActionListener>();
	}

	/**
	 * Get an instance of the AuthManager.
	 * 
	 * @return The Auth manager
	 */
	public static AuthManager getInstance()
	{
		if (instance == null)
		{
			instance = new AuthManager();
		}
		return instance;
	}

	/**
	 * Returns the current user session ID, or null if the user has no session
	 * ID
	 * 
	 * @return The session ID
	 */
	public String getSessionID()
	{
		return Cookies.getCookie("sid");
	}

	/**
	 * Returns the user name for the user signed in
	 * 
	 * @return The user name
	 */
	public String getUsername()
	{
		return Cookies.getCookie("username");
	}

	/**
	 * Adds authentication listeners
	 * 
	 * @param listener
	 *            The listener to be added to the list
	 */
	public void addSignInListener(AuthActionListener listener)
	{
		signListeners.add(listener);
	}

	/**
	 * Removes authentication in listeners
	 * 
	 * @param listener
	 *            The listener to be removed to the list
	 */
	public void removeSignInListener(AuthActionListener listener)
	{
		signListeners.remove(listener);
	}

	/**
	 * Asynchronous method to determine if user is signed in or not
	 * 
	 * @param ID
	 *            The Session ID, obtained through a call to
	 *            {@link #signIn(String, String, AuthManagerCallback)}
	 * @param callback
	 *            The callback, as described above. Executes at the completion
	 *            of the call.
	 */
	public void isSignedOn(String ID,
			final AuthManagerCallback<Boolean> callback)
	{

		AsyncCallback<Boolean> signedInCallBack = new AsyncCallback<Boolean>()
		{

			@Override
			public void onFailure(Throwable caught)
			{
				if (callback != null)
				{
					callback.onFail();
				}

			}

			@Override
			public void onSuccess(Boolean result)
			{
				if (callback != null)
				{
					if (result != null && result)
					{
						callback.onSuccess(result);
					} else
					{
						callback.onFail();
					}
				}

			}
		};
		service.isSignedIn(ID, signedInCallBack);
	}

	/**
	 * Attempt user signIn onSuccess & onFailure are
	 * called dependent on result of signIn
	 * 
	 * @param username users username
	 * @param password users password
	 * @param callback executes at the completion of the call.
	 */
	public void signIn(String username, String password,
			final AuthManagerCallback<Session> callback)
	{
		AsyncCallback<Session> signInCallBack = new AsyncCallback<Session>()
		{
			@Override
			public void onFailure(Throwable caught)
			{
				if (callback != null)
				{
					callback.onFail();
				}
			}

			@Override
			public void onSuccess(Session result)
			{
				if (result != null)
				{
					for (AuthActionListener listener : signListeners)
					{
						listener.onSignIn();
					}
				}
				if (callback != null)
				{
					if (result != null)
					{
						createSessionCookie(result);
						callback.onSuccess(result);
					} else
					{
						callback.onFail();
					}
				}
			}
		};
		service.signIn(username, password, signInCallBack);
	}

	/**
	 * Sign up attempt. 
	 * @param username users requested username
	 * @param passwordusers requested password
	 * @param callback executes at the completion of the call.
	 */
	public void signUp(String username, String password,
			final AuthManagerCallback<Session> callback)
	{
		if (FieldVerifier.isValidUserName(username) &&  FieldVerifier.isValidPassword(password))
		{
			AsyncCallback<Session> registerCallback = new AsyncCallback<Session>()
			{
				@Override
				public void onFailure(Throwable caught)
				{
					if (callback != null)
					{
						callback.onFail();
					}

				}

				@Override
				public void onSuccess(Session result)
				{
					if (callback != null)
					{
						if (result != null)
						{
							createSessionCookie(result);
							callback.onSuccess(result);
						} else
						{
							callback.onFail();
						}
					}
				}
			};
			service.signUp(username, password, registerCallback);
		} else
		{
			if (callback != null)
			{
				callback.onFail();
			}
		}

	}

	/**
	 * Creates a new session cookie
	 * 
	 * @param newSession The cookie info
	 */
	private void createSessionCookie(Session newSession)
	{
		Cookies.setCookie("sid", newSession.getSessionID());
		Cookies.setCookie("username", newSession.getUsername());
				
	}

}

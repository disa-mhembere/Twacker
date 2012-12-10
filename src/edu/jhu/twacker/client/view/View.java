/**
 * OOSE Project - Group 4
 * {@link View}.java 
 */
package edu.jhu.twacker.client.view;

import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Composite;

import edu.jhu.twacker.client.service.AuthService;
import edu.jhu.twacker.client.service.AuthServiceAsync;

/**
 * The base class from which all views inherit
 * 
 * @author Disa Mhembere, Andy Tien
 * 
 */
public class View extends Composite
{
	private boolean isSignedIn;
	private final AuthServiceAsync authService = GWT.create(AuthService.class);

	/**
	 * Intentional No-op constructor
	 */
	View()
	{

	}

	/**
	 * Determine if a user is signed in or we are operating under a guest
	 * account
	 */
	public void updateStatus()
	{
		authService.isSignedIn(new AsyncCallback<Boolean>() {
			@Override
			public void onSuccess(Boolean result)
			{
				isSignedIn = result;
			}

			@Override
			public void onFailure(Throwable caught)
			{
				isSignedIn = false;
			}
		});
	}

	public boolean isSignedIn()
	{
		return isSignedIn;
	}

}

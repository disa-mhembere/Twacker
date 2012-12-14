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
	private boolean isSignedIn = false;
	private final AuthServiceAsync authService = GWT.create(AuthService.class);

	/**
	 * Intentional No-operation constructor
	 */
	View()
	{

	}

	/**
	 * Initiates an asynchronous call to check whether the user is signed in.
	 * updateView() will be called so that any necessary changes are made if the
	 * user is signed in
	 */
	public void updateStatus()
	{
		authService.isSignedIn(new AsyncCallback<Boolean>() {
			@Override
			public void onSuccess(Boolean result)
			{
				if (isSignedIn != result) {
					isSignedIn = result;
					updateView();
				}
			}

			@Override
			public void onFailure(Throwable caught)
			{
				isSignedIn = false;
			}
		});
	}

	/**
	 * Returns the view's perception of the user's current signed in state
	 * 
	 * @return
	 */
	public boolean isSignedIn()
	{
		return isSignedIn;
	}

	/**
	 * This method is called whenever updateStatus() results in a state change.
	 * This allows us to, if necessary, update the panels depending on the
	 * user's logged in status
	 */
	protected void updateView()
	{

	}

}

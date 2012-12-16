/**
 * OOSE Project - Group 4
 * {@link ViewManager}.java
 */

package edu.jhu.twacker.client.manager;

import java.util.HashMap;

import com.google.gwt.user.client.History;
//import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.RootPanel;
//import com.google.gwt.core.client.GWT;
import com.google.gwt.event.logical.shared.ValueChangeEvent;
import com.google.gwt.event.logical.shared.ValueChangeHandler;

//import edu.jhu.twacker.client.service.AuthService;
//import edu.jhu.twacker.client.service.AuthServiceAsync;
import edu.jhu.twacker.client.view.HomeView;
import edu.jhu.twacker.client.view.AuthView;
import edu.jhu.twacker.client.view.PersonalHistoryView;
import edu.jhu.twacker.client.view.SignOutView;
import edu.jhu.twacker.client.view.RegisterView;
import edu.jhu.twacker.client.view.View;
import edu.jhu.twacker.client.view.ViewEnum;

/**
 * This class manages the changing the of views. It determines the view
 * requested and correct url to delegate
 * 
 * @author Disa Mhembere
 * 
 */
public class ViewManager implements ValueChangeHandler<String>
{
	private static ViewManager instance;
	private static HashMap<ViewEnum, View> allViews = new HashMap<ViewEnum, View>();

	// Map of all views & corresponding Enums
	// private AuthServiceAsync authService = GWT.create(AuthService.class);
	// private boolean signedIn = false;

	/**
	 * Private constructor for singleton pattern Register all views here which
	 * add each view to the <code>allViews</code> hashMap
	 */
	private ViewManager()
	{
		// Register views
		registerView(ViewEnum.AUTH, new AuthView());
		registerView(ViewEnum.HOME, new HomeView());
		registerView(ViewEnum.REGISTER, new RegisterView());
		registerView(ViewEnum.SIGNOUT, new SignOutView());
		registerView(ViewEnum.HISTORY, new PersonalHistoryView());
		History.addValueChangeHandler(this);
	}

	/**
	 * Register a view with the manager i.e add it to the <code>allViews</code>
	 * hashMap
	 * 
	 * @param vE
	 *            the enum corresponding to the view <code>v</code>
	 * @param v
	 *            the view
	 */
	private void registerView(ViewEnum vE, View v)
	{
		allViews.put(vE, v);
	}

	/**
	 * Gives the singleton instance
	 * 
	 * @return this instance of the view manager
	 */
	public static ViewManager getInstance()
	{
		if (instance != null)
			return instance;
		return instance = new ViewManager();

	}

	/**
	 * Sets up start page and initializes the history
	 */
	public void loadBaseView()
	{
		// Consistently loaded upon launch content
		// RootPanel.get("body").add(new HomeView());
		RootPanel.get("body").add(allViews.get(ViewEnum.HOME));

		if (History.getToken().length() == 0) {
			History.newItem("HOME");
		}
	}

	/**
	 * Sets the body of the website to a certain view
	 * 
	 * @param body
	 *            enum corresponding to view in <code>allViews HashMap</code>
	 */
	public void setBody(ViewEnum body)
	{
		View view;
		try {
			view = allViews.get(body);
			view.updateView();
			view.updateStatus();
		} catch (RuntimeException r) {
			throw new RuntimeException("Unknown view TODO: " + body.toString());
		}

		RootPanel.get("body").clear();
		RootPanel.get("body").add(view);
	}

	/**
	 * Processes the hyperlink clicks
	 * @param event
	 *            The event of the hyperlink click
	 */
	@Override
	public void onValueChange(ValueChangeEvent<String> event)
	{
		ViewEnum view = ViewEnum.HOME;
		try {
			view = ViewEnum.valueOf(event.getValue());

		} catch (IllegalArgumentException e) {
			return;
		}
		setBody(view);
	}
}

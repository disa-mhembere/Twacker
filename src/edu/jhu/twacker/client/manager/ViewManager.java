/**
 * OOSE Project - Group 4
 * ViewManager.java
 */

package edu.jhu.twacker.client.manager;

import java.util.HashMap;

import com.google.gwt.user.client.History;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.event.logical.shared.ValueChangeEvent;
import com.google.gwt.event.logical.shared.ValueChangeHandler;

import edu.jhu.twacker.client.view.LoginView;
import edu.jhu.twacker.client.view.View;
import edu.jhu.twacker.client.view.ViewEnum;

/**
 * @author Disa Mhembere
 * 
 */
public class ViewManager implements ValueChangeHandler<String>
{

	private static ViewManager instance;

	private static HashMap <ViewEnum, View> allViews;
	/**
	 * Private constructor for singleton pattern
	 */
	private ViewManager()
	{
		// Register views
		allViews = new HashMap<ViewEnum, View>();
		allViews.put(ViewEnum.LOGIN, new LoginView()); 
		//allViews.put(ViewEnum.REGISTER, new RegisterView()); 
		//allViews.put(ViewEnum.LOGIN, new SearchView());
		
		History.addValueChangeHandler(this);
	}
	

	/**
	 * Sets up start page and initializes the history (if there isn't a
	 * history).
	 */
	public void loadStartView()
	{
		RootPanel.get("body").add(new LoginView()); // Should be replace with static content
		
		// AT : TODO

//		if (History.getToken().length() == 0)
//		{
//			History.newItem("HOME");
//		}
	}

	/**
	 * Gives the singleton instance 
	 * @return The singleton instance
	 */
	public static ViewManager getInstance()
	{
		if (instance == null)
		{
			instance = new ViewManager();
		}
		return instance;
	}

	/**
	 * Sets the body of the website to a certain view
	 * @param body enum corresponding to view in <code>allViews HashMap</code> 
	 */
	public void setBody(ViewEnum body)
	{
		View view;
		try
		{
			view = allViews.get(body);  
		} catch (RuntimeException r)
		{
			throw new RuntimeException("Unknown view TODO: " + body.toString());
		}

		RootPanel.get("body").clear();
		RootPanel.get("body").add(view);
	}

	/**
	 * Processes the hyperlink clicks
	 * 
	 * @param event
	 *            The event of the hyperlink click
	 */
	@Override
	public void onValueChange(ValueChangeEvent<String> event)
	{
		 ViewEnum view = ViewEnum.HOME;
		 try {
		 view = ViewEnum.valueOf(event.getValue());
		 } catch(IllegalArgumentException e) {
		 return;
		 }
		setBody(view);
	}

}

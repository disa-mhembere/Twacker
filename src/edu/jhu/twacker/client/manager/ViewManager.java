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

import edu.jhu.twacker.client.view.HomeView;
import edu.jhu.twacker.client.view.AuthView;
import edu.jhu.twacker.client.view.View;
import edu.jhu.twacker.client.view.ViewEnum;

/**
 * @author Disa Mhembere
 * 
 */
public class ViewManager implements ValueChangeHandler<String>
{
	private static ViewManager instance;
	private static HashMap <ViewEnum, View> allViews; // Map of all views & corresponding Enums
	
	/**
	 * 
	 * Private constructor for singleton pattern
	 */
	private ViewManager()
	{
		// Register views
		allViews = new HashMap<ViewEnum, View>();
		allViews.put(ViewEnum.AUTH, new AuthView()); 
		allViews.put(ViewEnum.HOME, new HomeView());
		
		History.addValueChangeHandler(this);
	}
	
	/**
	 * Gives the singleton instance 
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
		
		RootPanel.get("body").add(new HomeView()); // Should be replace with static content
		
		// AT : TODO

		if (History.getToken().length() == 0)
		{
			History.newItem("HOME");
		}
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
	 * @param event The event of the hyperlink click
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

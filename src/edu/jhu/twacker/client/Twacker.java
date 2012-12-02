/**
 * OOSE Project - Group 4
 * Twacker.java
 */

package edu.jhu.twacker.client;

import com.allen_sauer.gwt.log.client.Log;
import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.History;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.visualization.client.VisualizationUtils;
import com.google.gwt.visualization.client.visualizations.corechart.CoreChart;

import edu.jhu.twacker.client.manager.ViewManager;
import edu.jhu.twacker.client.service.AuthService;
import edu.jhu.twacker.client.service.AuthServiceAsync;


/**
 * This class is used to redirect the page according to the view we want
 * rendered
 * 
 * @author Disa Mhembere
 * 
 */
public class Twacker implements EntryPoint
{
	private final AuthServiceAsync authService = GWT.create(AuthService.class);

	/**
	 * This is the entry point method for the application
	 * When it loads this is run
	 */
	public void onModuleLoad()
	{
		Runnable onLoadCallback = new Runnable() {
			@Override
			public void run() {
				ViewManager.getInstance().loadBaseView();
			    History.fireCurrentHistoryState();
			    
			    // Set the default user as guest from the start Temporary
			    authService.setUsername("guest", new AsyncCallback<Void>()
				{
					@Override
					public void onSuccess(Void result)
					{
						// Do nothing
					}
					@Override
					public void onFailure(Throwable caught)
					{
						Log.debug("DM failure " + this.getClass().getName() + " Method: onModuleLoad()");
					}
				});
			}
		};
	    VisualizationUtils.loadVisualizationApi(onLoadCallback, CoreChart.PACKAGE);  
	}
}

/**
 * OOSE Project - Group 4
 * Twacker.java
 */

package edu.jhu.twacker.client;

import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.user.client.History;
import com.google.gwt.visualization.client.VisualizationUtils;
import com.google.gwt.visualization.client.visualizations.corechart.CoreChart;

import edu.jhu.twacker.client.manager.ViewManager;


/**
 * This class is used to redirect the page according to the view we want
 * rendered
 * 
 * @author Disa Mhembere
 * 
 */
public class Twacker implements EntryPoint
{

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
			}
		};
	    VisualizationUtils.loadVisualizationApi(onLoadCallback, CoreChart.PACKAGE);  
	}
}

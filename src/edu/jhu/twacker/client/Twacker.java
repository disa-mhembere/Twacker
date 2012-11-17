/**
 * OOSE Project - Group 4
 * Twacker.java
 */

package edu.jhu.twacker.client;

import com.google.gwt.core.client.EntryPoint;
//import com.google.gwt.user.client.History;

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
	 * This is the entry point method.
	 */
	public void onModuleLoad()
	{
		ViewManager.getInstance().loadStartView();
	    //History.fireCurrentHistoryState();
	}
}

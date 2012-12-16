/**
 * OOSE Project - Group 4
 * {@link SearchService}.java 
 */

package edu.jhu.twacker.client.service;

import java.util.Date;
import java.util.List;
import java.util.Map;

import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;

/**
 * The client side stub for the RPC service.
 * @author Disa Mhembere, Alex Long
 */
@RemoteServiceRelativePath("search")
public interface SearchService extends RemoteService {
	String queryServer(String name) throws IllegalArgumentException;
	/**
	 * Save a search term to a specific user if signed in
	 * or else save it under guest account name
	 * @param query
	 */
	public void saveSearch(String query);
	
	/**
	 * Query and return all of the searches a user has ever passed in
	 * @return a Map with a list of strings containing all searches in history
	 */
	public Map<Date, List<String>> getAllSearches();
	
	/**
	 * Query datastore for a list of all searches of the day 
	 * @param date the date requested
	 * @return a Map with a list of strings containing all searches of a chosen day
	 */
	public Map<Date,List<String>> getDaySearches(Date date);	
}

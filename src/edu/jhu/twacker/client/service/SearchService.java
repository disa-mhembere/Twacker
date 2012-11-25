package edu.jhu.twacker.client.service;

import java.util.Date;
import java.util.Map;

import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;

/**
 * The client side stub for the RPC service.
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
	 * @return a list of strings corresponding to all searches
	 * the user has ever submitted
	 */
	public Map<Date, String> getAllSearches();
	
	/**
	 * Query datastore for a list of all searches of the day 
	 * @param newParam TODO
	 * @return a list of string containing all searches of a chosen day
	 */
	public Map<Date, String> getDaySearches(Date newParam);
}

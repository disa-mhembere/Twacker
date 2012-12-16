/** 
 * OOSE Project - Group 4
 * {@link SearchServiceAsync}.java
 */
package edu.jhu.twacker.client.service;

import java.util.Date;
import java.util.List;
import java.util.Map;

import com.google.gwt.user.client.rpc.AsyncCallback;

/**
 * The asynchronous counterpart of {@link SearchService}
 */
public interface SearchServiceAsync {
	/**
	 * 
	 * @see edu.jhu.twacker.client.service.SearchService#queryServer(java.lang.String)
	 */
	void queryServer(String input, AsyncCallback<String> callback)
			throws IllegalArgumentException;

	/**
	 * 
	 * @see edu.jhu.twacker.client.service.SearchService#saveSearch(java.lang.String)
	 */
	void saveSearch(String search, AsyncCallback<Void> callback);

	/**
	 * 
	 * @see edu.jhu.twacker.client.service.SearchService#getAllSearches(java.util.Map<java.util.Date, java.lang.String>)
	 */
	void getAllSearches(AsyncCallback<Map<Date,List<String>>> callback);

	/**
	 * 
	 * @see edu.jhu.twacker.client.service.SearchService#getDaySearches(java.util.Map<java.util.Date, java.lang.String>)
	 */
	void getDaySearches(Date newParam, AsyncCallback<Map<Date, List<String>>> callback);
}

package edu.jhu.twacker.client.service;

import java.util.Date;
import java.util.Map;

import com.google.gwt.user.client.rpc.AsyncCallback;

/**
 * The async counterpart of <code>SearchService</code>.
 */
public interface SearchServiceAsync {
	void queryServer(String input, AsyncCallback<String> callback)
			throws IllegalArgumentException;

	void saveSearch(String search, AsyncCallback<Void> callback);

	void getAllSearches(AsyncCallback<Map<Date,String>> callback);

	void getDaySearches(Date newParam, AsyncCallback<Map<Date, String>> callback);

}

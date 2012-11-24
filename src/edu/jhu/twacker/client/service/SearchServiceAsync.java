package edu.jhu.twacker.client.service;

import java.util.List;

import com.google.gwt.user.client.rpc.AsyncCallback;

/**
 * The async counterpart of <code>SearchService</code>.
 */
public interface SearchServiceAsync {
	void queryServer(String input, AsyncCallback<String> callback)
			throws IllegalArgumentException;

	void saveSearch(String search, AsyncCallback<Void> callback);

	void getAllSearches(AsyncCallback<List<String>> callback);

	void getDaySearches(AsyncCallback<List<String>> asyncCallback);
}

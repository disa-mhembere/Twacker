package edu.jhu.twacker.client.service;

import com.google.gwt.user.client.rpc.AsyncCallback;

/**
 * The async counterpart of <code>GreetingService</code>.
 */
public interface QueryServiceAsync {
	void queryServer(String input, AsyncCallback<String> callback)
			throws IllegalArgumentException;
}

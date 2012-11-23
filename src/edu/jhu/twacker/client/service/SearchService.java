package edu.jhu.twacker.client.service;

import java.util.List;

import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;

/**
 * The client side stub for the RPC service.
 */
@RemoteServiceRelativePath("search")
public interface SearchService extends RemoteService {
	String queryServer(String name) throws IllegalArgumentException;
	public void saveSearch(String query);
	public List<String> getAllSearches(); 
}

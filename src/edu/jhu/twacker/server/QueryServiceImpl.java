package edu.jhu.twacker.server;

import edu.jhu.twacker.client.service.QueryService;
import edu.jhu.twacker.model.TwackerModel;
import com.google.gwt.user.server.rpc.RemoteServiceServlet;

/**
 * The server side implementation of the RPC service.
 */
@SuppressWarnings("serial")
public class QueryServiceImpl extends RemoteServiceServlet implements
		QueryService {

	private TwackerModel model;
	
	public String queryServer(String input) throws IllegalArgumentException {
		
		model = new TwackerModel(input);
		model.run();		
		return model.toString();
	}
}

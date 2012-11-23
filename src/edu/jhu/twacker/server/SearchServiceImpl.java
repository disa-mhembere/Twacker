package edu.jhu.twacker.server;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

import javax.jdo.JDOHelper;
import javax.jdo.PersistenceManager;
import javax.jdo.PersistenceManagerFactory;
import javax.jdo.Query;

import edu.jhu.twacker.client.service.SearchService;
import edu.jhu.twacker.model.TwackerModel;
import edu.jhu.twacker.server.data.Search;

import com.google.appengine.api.users.User;
import com.google.appengine.api.users.UserService;
import com.google.appengine.api.users.UserServiceFactory;
import com.google.gwt.user.server.rpc.RemoteServiceServlet;

/**
 * The server side implementation of the RPC service.
 */
@SuppressWarnings("serial")
public class SearchServiceImpl extends RemoteServiceServlet implements
		SearchService {

	private TwackerModel model;
	private static final Logger LOG = Logger.getLogger(SearchServiceImpl.class.getName());
	  private static final PersistenceManagerFactory PMF =
	      JDOHelper.getPersistenceManagerFactory("transactions-optional");
	
	
	public String queryServer(String input) throws IllegalArgumentException {
		
		model = new TwackerModel(input);
		model.run();		
		return model.toString();
	}

	@Override
	public void saveSearch(String search)
	{
		PersistenceManager pm = getPersistenceManager();
	    try {
	      pm.makePersistent(new Search(getUser(), search));
	    } finally {
	      pm.close();
	    }
		
	}

	@Override
	public List<String> getAllSearches()
	{
		PersistenceManager pm = getPersistenceManager();
		List<String> result = new ArrayList<String>();

		try
		{
			/*
			Query q = pm.newQuery("javax.jdo.query.SQL","SELECT searchTerm FROM Search WHERE user = "+ getUser()); // TODO
			q.setClass(Search.class);
			q.setUnique(true);
			@SuppressWarnings("unchecked")
			List<Search> results = (List<Search>) q.execute();
			*/
			Query q = pm.newQuery(Search.class, "user == u");
			q.declareParameters("com.google.appengine.api.users.User u");
			q.setOrdering("createDate");

			@SuppressWarnings("unchecked")
			List<Search> searches = (List<Search>) q.execute(getUser());
			
			if (searches.size() == 0)
			{
				result.add("No Entries");
			}
			
			else
			{
				for (Search searchTerm : searches)
				{
					result.add(searchTerm.toString());
				}
			}
		} finally
		{
			pm.close();
		}
		return result;
	}
	
	private User getUser() {
	    UserService userService = UserServiceFactory.getUserService();
	    return userService.getCurrentUser();
	  }
	
	private PersistenceManager getPersistenceManager() {
	    return PMF.getPersistenceManager();
	  }
}

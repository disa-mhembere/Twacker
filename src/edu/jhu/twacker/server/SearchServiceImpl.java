package edu.jhu.twacker.server;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.jdo.JDOHelper;
import javax.jdo.PersistenceManager;
import javax.jdo.PersistenceManagerFactory;
//import javax.jdo.Query;

import edu.jhu.twacker.client.service.SearchService;
import edu.jhu.twacker.model.TwackerModel;
import edu.jhu.twacker.server.data.Search;

//import com.allen_sauer.gwt.log.client.Log;
import com.google.appengine.api.datastore.DatastoreService;
import com.google.appengine.api.datastore.DatastoreServiceFactory;
import com.google.appengine.api.datastore.Entity;
import com.google.appengine.api.datastore.Query.CompositeFilterOperator;
import com.google.appengine.api.datastore.Query.Filter;
import com.google.appengine.api.datastore.Query.FilterPredicate;
import com.google.appengine.api.datastore.Query.FilterOperator;
import com.google.appengine.api.datastore.Query;
import com.google.appengine.api.datastore.PreparedQuery;

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
	private static final PersistenceManagerFactory PMF =
	      JDOHelper.getPersistenceManagerFactory("transactions-optional");
	
	// Get the Datastore Service
	private DatastoreService datastore = DatastoreServiceFactory.getDatastoreService();
	
	
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
	public Map<Date, String> getAllSearches()
	{
//		PersistenceManager pm = getPersistenceManager();
		Map<Date, String> result = new HashMap<Date, String>();
		
		Filter isUser = new FilterPredicate("user", FilterOperator.EQUAL, new User("guest", "gmail.com")); //DM: TODO Temporary
		Query q = new Query("Search").setFilter(isUser);
		PreparedQuery pq = datastore.prepare(q);
		
		try
		{
			for (Entity retSearches : pq.asIterable())
			{
				// String user = (String) retSearches.getProperty("user");
				// result.add(user);
				// String searchTerm = (String)
				// retSearches.getProperty("searchTerm");
				result.put((Date) retSearches.getProperty("createDate"),
						(String) retSearches.getProperty("searchTerm"));
			}
		} catch (Exception e)
		{
			// DM OK to return empty hashMap 
			// result.put(new Date(), "No user entries for" + getUser());
		}

		/*
		try
		{
			
			Query q = pm.newQuery(Search.class, "user == u");
			q.declareParameters("com.google.appengine.api.users.User u");
			q.setOrdering("createDate");
			@SuppressWarnings("unchecked")
			//List<Search> searches = (List<Search>) q.execute(getUser());
			List<Search> searches = (List<Search>) q.execute("guest");
			
			if (searches.size() == 0)
			{
				result.add("No Entries for user "+ getUser());
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
		*/

		return result;
	}
	
	@Override
	public Map<Date, String> getDaySearches(Date newParam)
	{
		Map<Date, String> result = new HashMap<Date, String>();

		Filter isUser = new FilterPredicate("user", FilterOperator.EQUAL,
				new User("guest", "gmail.com")); // DM: TODO Temporary
		
		
		Filter isDate = new FilterPredicate("createDate", FilterOperator.EQUAL,
				newParam);
		
		Filter isUserAndDate = CompositeFilterOperator.and(isUser, isDate);
//		Query q = new Query("Search").setFilter(isUser).setFilter(Entity.KEY_RESERVED_PROPERTY,
//				FilterOperator.EQUAL, lastSeenKey)  ;
		Query q = new Query("Search").setFilter(isUser);
		//Query q = new Query("Search").setFilter(isUserAndDate);
		PreparedQuery pq = datastore.prepare(q);

		try
		{
			for (Entity retSearches : pq.asIterable())
			{
				result.put((Date) retSearches.getProperty("createDate"),
						(String) retSearches.getProperty("searchTerm"));
			}
		} catch (Exception e)
		{
			// DM OK to return empty hashMap
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

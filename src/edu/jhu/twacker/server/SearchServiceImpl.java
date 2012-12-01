/**
 * OOSE Project - Group 4
 * {@link SearchServiceImpl}.java
 */
package edu.jhu.twacker.server;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.jdo.JDOHelper;
import javax.jdo.PersistenceManager;
import javax.jdo.PersistenceManagerFactory;
import javax.jdo.Query;
import javax.servlet.http.HttpSession;
//import javax.jdo.Query;

import edu.jhu.twacker.client.service.SearchService;
import edu.jhu.twacker.model.TwackerModel;
import edu.jhu.twacker.server.data.Search;

import com.google.gwt.user.server.rpc.RemoteServiceServlet;


/**
 * The server side implementation of the RPC SearchService
 * @author Alex Long, Disa Mhembere
 */
@SuppressWarnings("serial")
public class SearchServiceImpl extends RemoteServiceServlet implements
		SearchService {

	private static final PersistenceManagerFactory PMF =
	      JDOHelper.getPersistenceManagerFactory("transactions-optional");
	
	/**
	 * 
	 * @see edu.jhu.twacker.client.service.SearchService#queryServer(java.lang.String)
	 */ 
	public String queryServer(String input) throws IllegalArgumentException {
		TwackerModel model = new TwackerModel(input);
		model.run();
		return model.toString();
	}

	@Override
	/**
	 * 
	 * @see edu.jhu.twacker.client.service.SearchService#saveSearch(java.lang.String)
	 */
	public void saveSearch(String search)
	{
		PersistenceManager pm = getPersistenceManager();
	    try {
	      pm.makePersistent(new Search(getUsername(), search));
	    } finally {
	      pm.close();
	    }
		
	}

	@Override
	/**
	 * 
	 * @see edu.jhu.twacker.client.service.SearchService#getAllSearches(java.util.Map<java.util.Date, java.lang.String>)
	 */
	public Map<Date, String> getAllSearches()
	{
		PersistenceManager pm = getPersistenceManager();
		Map<Date, String> result = new HashMap<Date, String>();
		
		Query q = pm.newQuery(Search.class);
		q.setFilter("username == usernameParam");

		q.declareParameters("String usernameParam");
		
		@SuppressWarnings("unchecked")
		List<Search> retSearches = (List<Search>) q.execute( getUsername());
		
		try
		{
			for (Search searchObj : retSearches)
			{
//				Date newDate = new Date(searchObj.getCreateDate().getTime());		 
//				result.put(newDate, (String)searchObj.getSearchTerm()); 
				
				result.put(new Date(searchObj.getCreateDate().getTime()),(String)searchObj.getSearchTerm()); // TODO : DM Fix duplicated
			}
		} catch (Exception e)
		{
			// DM OK to return empty hashMap 
			// result.put(new Date(), "No user entries for" + getUser());
		}
				return result;
	}
	
	@Override
	/**
	 * 
	 * @see edu.jhu.twacker.client.service.SearchService#getDaySearches(java.util.Map<java.util.Date, java.lang.String>)
	 */
	public Map<Date, String> getDaySearches(Date newParam)
	{
		Map<Date, String> daySearches = new HashMap<Date, String>();
		Map<Date, String> result = new HashMap<Date, String>();
		daySearches = getAllSearches();
		java.sql.Date queryDate = new java.sql.Date(newParam.getTime());
		try
		{
			for (Date utilDate : daySearches.keySet())
			{
				
				java.sql.Date retDate = new java.sql.Date(utilDate.getTime());
				
				if (retDate.toString().equals(queryDate.toString()))
				{
					result.put(utilDate, daySearches.get(utilDate));
				}
		}
		} catch (Exception e)
		{
			// DM OK to return empty hashMap
		}

		return result;
	}
	
	/**
	 * Get the user with the current httpSession
	 * @return username the users username  
	 */
	public String getUsername()
	{
		HttpSession httpSession = getThreadLocalRequest().getSession(true);
		return httpSession.getAttribute("username").toString(); 
	}
	
	
	/**
	 * Get the persistence manager instance
	 * @return the persistence manager instance
	 */
	private PersistenceManager getPersistenceManager() {
	    return PMF.getPersistenceManager();
	  }

}

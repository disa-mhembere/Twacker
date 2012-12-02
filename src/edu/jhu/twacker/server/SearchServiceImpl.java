/**
 * OOSE Project - Group 4
 * {@link SearchServiceImpl}.java
 */
package edu.jhu.twacker.server;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.jdo.JDOHelper;
import javax.jdo.PersistenceManager;
import javax.jdo.PersistenceManagerFactory;
import javax.jdo.Query;
import javax.servlet.http.HttpSession;

import edu.jhu.twacker.client.service.SearchService;
import edu.jhu.twacker.model.TwackerModel;
import edu.jhu.twacker.server.data.Search;

import com.google.gwt.user.server.rpc.RemoteServiceServlet;

/**
 * The server side implementation of the RPC SearchService
 * 
 * @author Alex Long, Disa Mhembere
 */
@SuppressWarnings("serial")
public class SearchServiceImpl extends RemoteServiceServlet implements
		SearchService
{

	private static final PersistenceManagerFactory PMF = JDOHelper
			.getPersistenceManagerFactory("transactions-optional");

	/**
	 * 
	 * @see edu.jhu.twacker.client.service.SearchService#queryServer(java.lang.String)
	 */
	public String queryServer(String input) throws IllegalArgumentException
	{
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
		try
		{
			pm.makePersistent(new Search(getUsername(), search));
		} finally
		{
			pm.close();
		}

	}

	@SuppressWarnings("unchecked")
	@Override
	/**
	 * 
	 * @see edu.jhu.twacker.client.service.SearchService#getAllSearches(java.util.Map<java.util.Date, java.lang.String>)
	 */
	public Map<Date, List<String>> getAllSearches()
	{
		PersistenceManager pm = getPersistenceManager();
		Map<Date, List<String>> result = new HashMap<Date, List<String>>();

		Query q = pm.newQuery(Search.class);
		q.setFilter("username == usernameParam");

		q.declareParameters("String usernameParam");

		List<Search> retSearches = (List<Search>) q.execute(getUsername());

		try
		{
			for (Search searchObj : retSearches)
			{
				// Date unseen
				Date newDate = new Date(searchObj.getCreateDate().getTime());
				if (!result.containsKey(newDate))
				{

					result.put(
							newDate,
							new ArrayList<String>(Arrays.asList((String) searchObj
									.getSearchTerm())));
				}
				// Date seen
				else
				{
					// List<String> updateList = new ArrayList<String>();
					// updateList = result.get(newDate);
					// updateList.add((String)searchObj.getSearchTerm());
					// result.put(newDate, updateList);
					result.get(newDate).add((String) searchObj.getSearchTerm());
				}

			}
		} catch (Exception e)
		{
			// DM OK to return empty hashMap
		}
		return result;
	}

	@Override
	/**
	 * 
	 * @see edu.jhu.twacker.client.service.SearchService#getDaySearches(java.util.Map<java.util.Date, java.lang.String>)
	 */
	public Map<Date, List<String>> getDaySearches(Date chosenDate)
	{
		Map<Date, List<String>> daySearches = new HashMap<Date, List<String>>();
		Map<Date, List<String>> result = new HashMap<Date, List<String>>();
		daySearches = getAllSearches();
		java.sql.Date queryDate = new java.sql.Date(chosenDate.getTime()); // sql equivalent of req date
		
		try
		{
			for (Date utilDate : daySearches.keySet())
			{

				java.sql.Date retDate = new java.sql.Date(utilDate.getTime());

				if (retDate.toString().equals(queryDate.toString()))
				{
					result.put(utilDate,	new ArrayList<String>(daySearches.get(utilDate)));
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
	 * 
	 * @return username the users username
	 */
	public String getUsername()
	{
		HttpSession httpSession = getThreadLocalRequest().getSession(true);
		return httpSession.getAttribute("username").toString();
	}

	/**
	 * Get the persistence manager instance
	 * 
	 * @return the persistence manager instance
	 */
	private PersistenceManager getPersistenceManager()
	{
		return PMF.getPersistenceManager();
	}

}

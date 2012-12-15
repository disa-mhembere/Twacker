package edu.jhu.twacker.model.query;

import java.util.ArrayList;
import java.util.List;

import com.google.gson.Gson;

import edu.jhu.twacker.model.query.otter.experts.Expert;
import edu.jhu.twacker.model.query.otter.experts.ExpertsQuery;
import edu.jhu.twacker.model.query.otter.experts.ExpertsResponse;

/**
 * This class is responsible for obtaining the top experts who Tweet
 * about a specific search term the most.
 * 
 * @author Daniel Deutsch
 */
public class ExpertsExec extends QueryExec
{
	/**
	 * The term to search for.
	 */
	private String searchTerm;
	
	/**
	 * The results of the query.
	 */
	private List<Expert> experts = new ArrayList<Expert>();
	
	/**
	 * The results object.
	 */
	private ExpertsResponse results;
	
	/**
	 * The constructor for the class.
	 * @param term The term to search for.
	 */
	public ExpertsExec(String term)
	{
		this.searchTerm = term;
	}
	
	/**
	 * Executes the query.
	 */
	public void run()
	{
		ExpertsQuery query = new ExpertsQuery(this.searchTerm);
		HttpGetWrapper wrapper = new HttpGetWrapper(query.createUrl());
				
		Gson gson = new Gson();
		
		ExpertsQuery response = gson.fromJson(wrapper.get(), ExpertsQuery.class);
			
		try
		{
			this.results = ((ExpertsResponse) response.getResponse());
			this.experts = this.results.getList();
		}
		catch (NullPointerException e)
		{
			// empty objects so their toString methods produce nothing
			this.results = new ExpertsResponse();
			this.experts = new ArrayList<Expert>();
		}
	}
	
	/**
	 * Retrieves the results of the query.
	 * @return The response object.
	 */
	public ExpertsResponse getResults()
	{
		return this.results;
	}

	/**
	 * Generates a JSON formatted representation of this object.
	 * @return The String.
	 */
	public String toString()
	{
		List<Expert> topFive = new ArrayList<Expert>();
		int fiveOrMax = Math.min(5, this.experts.size());
		
		for (int i = 0; i < fiveOrMax; i++)
			topFive.add(this.experts.get(i));
		
		return "\"experts\" : { \"experts\" : " + topFive.toString() + " }";
	}
	
	public static void main(String[] args)
	{
		ExpertsExec experts = new ExpertsExec("Obama");
		experts.run();
		
		System.out.println(experts);
	}
}

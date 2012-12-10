/**
 * OOSE Project - Group 4
 * HistogramExec.java
 */
package edu.jhu.twacker.model.query;

import com.google.gson.Gson;
import edu.jhu.twacker.model.query.otter.*;
import edu.jhu.twacker.model.query.otter.histogram.HistogramQuery;
import edu.jhu.twacker.model.query.otter.histogram.HistogramResponse;

/**
 * This class performs the queries necessary to obtain the data
 * to draw the histogram.
 * 
 * @author Daniel Deutsch
 */
public class HistogramExec extends QueryExec
{
	/**
	 * The term to search for.
	 */
	private String term;
	
	/**
	 * The number of seconds per slice.
	 */
	private String slice;
	
	/**
	 * The number of slices to sample.
	 */
	private String period;
	
	/**
	 * The response from the HttpGet in the form of a list
	 * of integers.
	 */
	private String results;
	
	/**
	 * Contains the results of the query.
	 */
	private HistogramResponse response;
	
	/**
	 * The constructor for the class.
	 * @param search The term to search for.
	 * @param slice The seconds per slice.
	 * @param period The number of slices to sample.
	 */
	public HistogramExec(String search, String slice, String period)
	{
		this.term = search;
		this.slice = slice;
		this.period = period;
	}
	
	/**
	 * Executes the necessary query to get the histogram data.
	 * @param search The term to search for.
	 */
	public void run()
	{
		OtterQuery query = new HistogramQuery(this.term, this.slice, this.period);
		HttpGetWrapper wrapper = new HttpGetWrapper(query.createUrl());
		
		Gson gson = new Gson();
		try
		{
			OtterQuery response = gson.fromJson(wrapper.get(), HistogramQuery.class);
			this.results = response.getResponse().toString();
			this.response = (HistogramResponse) response.getResponse();
		}
		catch (Exception e)
		{
			System.out.println(e);
		}
	}
	
	/**
	 * Retrieves the results of the query.
	 * @return The results object.
	 */
	public HistogramResponse getResults()
	{
		return this.response;
	}
	
	/**
	 * Creates a JSON representation of this object.
	 */
	public String toString()
	{
		return "\"histogram:\": { \"data\": " + this.results + ", \"slice\": \"" + this.slice + "\"," +
				" \"period\": \"" + this.period + "\" }";
	}
	
	/**
	 * Tests the HistogramExec class.
	 */
	public static void main(String[] args)
	{
		HistogramExec histogram = new HistogramExec("Obama", "86400", "30");
		histogram.run();
		
		System.out.println(histogram);
	}
}

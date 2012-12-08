/**
 * OOSE Project - Group 4
 * TwackerModel.java
 */
package edu.jhu.twacker.model;

import java.util.ArrayList;
import java.util.List;

import edu.jhu.twacker.model.query.ExpertsExec;
import edu.jhu.twacker.model.query.HistogramExec;
import edu.jhu.twacker.model.query.QueryExec;
import edu.jhu.twacker.model.query.SentimentExec;

/**
 * This class represents the model for the logic behind the Twacker web site. It contains
 * three different query executers that each perform their own queries to a variety
 * of APIs that interact with Twitter. They are the <code>HistogramExec</code>, the
 * <code>HeatMapExec</code>, and the <code>SentimentExec</code>.
 * 
 * @author Daniel Deutsch
 */
public class TwackerModel
{
	/**
	 * A list of all of the query executers that this class will have.
	 * For right now, there will be 3: the HistogramExec, the
	 * SentimentExec, and the FrequencyExec.
	 */
	private List<QueryExec> executers = new ArrayList<QueryExec>();
	
	private HistogramExec histogram;
	
	private SentimentExec sentiment;
	
	private ExpertsExec experts;
	
	/**
	 * The term to search for.
	 */
	private String search;
	
	/**
	 * The result of the queries in JSON format that will be send to the
	 * Javascript part of the application which will display the data.
	 * This data member should be retrieved after the thread has been run.
	 */
	private String jsonResult;
	
	/**
	 * The result of the queries.
	 */
	private Result result;
	
	/**
	 * The constructor for the class.
	 */
	public TwackerModel(String search)
	{
		this.search = search;
		
		this.histogram = new HistogramExec(this.search, "86400", "10");
		this.sentiment = new SentimentExec(this.search);
		this.experts = new ExpertsExec(this.search);
		
		this.executers.add(this.histogram);
		this.executers.add(this.sentiment);
		this.executers.add(this.experts);
	}
	
	/**
	 * Executes the query to get all of the data necessary to create the
	 * graphs to display on the site.
	 */
	public void run()
	{
		for (QueryExec executer : this.executers)
			executer.run();
		
		this.createJsonFormat();
		this.result = new Result(this.search, this.histogram.getResults(), this.experts.getResults(), this.sentiment.getResults());
	}
	
	/**
	 * Creates the JSON format for the model.
	 */
	public void createJsonFormat()
	{
		this.jsonResult = "{ \"search\" : \"" + this.search + "\", ";
		for (QueryExec executer : this.executers)
			this.jsonResult += executer + ", ";
		
		int index = this.jsonResult.lastIndexOf(',');
		this.jsonResult = this.jsonResult.substring(0, index);
		this.jsonResult += " }";
	}
	
	/**
	 * Retrieves the results of all of the queries.
	 * @return The result.
	 */
	public Result getResult()
	{
		return this.result;
	}
	
	/**
	 * Gets the JSON representation of the result of these queries.
	 * @return The String representation.
	 */
	public String getJsonResult()
	{
		return this.jsonResult;
	}
	
	/**
	 * Gets the JSON representation of the result of these queries.
	 * @return The String representation.
	 */
	public String toString()
	{
		return this.getJsonResult();
	}
	
	/**
	 * Runs the HisogramExec, HeatMapExec, and SentimentExec.
	 */
	public static void main(String[] args)
	{
		TwackerModel model = new TwackerModel("Obama");
		model.run();
		
		System.out.println(model.toString());
	}
}

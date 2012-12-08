package edu.jhu.twacker.model;

import edu.jhu.twacker.model.query.alchemy.AlchemyReponseWrapper;
import edu.jhu.twacker.model.query.otter.experts.ExpertsResponse;
import edu.jhu.twacker.model.query.otter.histogram.HistogramResponse;

/**
 * This class is a wrapper for the entire reponse from creating a TwackerModel call.
 * 
 * @author Daniel Deutsch
 */
public class Result 
{
	/**
	 * The term that was searched for.
	 */
	private String search;
	
	/**
	 * The histogram response.
	 */
	private HistogramResponse histogramResponse;
	
	/**
	 * The experts response.
	 */
	private ExpertsResponse expertsExec;
	
	/**
	 * The sentiment response.
	 */
	private AlchemyReponseWrapper sentimentResponse;
	
	/**
	 * The constructor.
	 * @param search The search term that generated this data.
	 * @param histogram The histogram response.
	 * @param experts The experts response.
	 * @param alchemy The sentiment response.
	 */
	public Result(String search, HistogramResponse histogram, ExpertsResponse experts, AlchemyReponseWrapper alchemy)
	{
		this.search = search;
		this.histogramResponse = histogram;
		this.expertsExec = experts;
		this.sentimentResponse = alchemy;
	}
	
	/**
	 * Retrieves the search term used to generate this data.
	 * @return The search term.
	 */
	public String getSearch()
	{
		return this.search;
	}

	/**
	 * Retrieves the histogram response.
	 * @return The histogram response.
	 */
	public HistogramResponse getHistogramResponse() 
	{
		return histogramResponse;
	}

	/**
	 * Retrieves the experts response.
	 * @return The experts response.
	 */
	public ExpertsResponse getExpertsExec() 
	{
		return expertsExec;
	}

	/**
	 * Retrieves the sentiment response.
	 * @return The sentiment response.
	 */
	public AlchemyReponseWrapper getSentimentResponse() 
	{
		return sentimentResponse;
	}
}

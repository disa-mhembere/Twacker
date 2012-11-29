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
	
	public Result(HistogramResponse histogram, ExpertsResponse experts, AlchemyReponseWrapper alchemy)
	{
		this.histogramResponse = histogram;
		this.expertsExec = experts;
		this.sentimentResponse = alchemy;
	}

	public HistogramResponse getHistogramResponse() 
	{
		return histogramResponse;
	}

	public ExpertsResponse getExpertsExec() 
	{
		return expertsExec;
	}

	public AlchemyReponseWrapper getSentimentResponse() 
	{
		return sentimentResponse;
	}
}

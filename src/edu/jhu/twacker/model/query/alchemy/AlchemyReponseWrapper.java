package edu.jhu.twacker.model.query.alchemy;

/**
 * Wraps the results of multiple AlchemyResponse objects.
 * 
 * @author Daniel Deutsch
 */
public class AlchemyReponseWrapper 
{
	/**
	 * The number of positive responses.
	 */
	private int positive;
	
	/**
	 * The number of negative responses.
	 */
	private int negative;
	
	/**
	 * The number of neutral responses.
	 */
	private int neutral;
	
	/**
	 * The number of errors.
	 */
	private int errors;
	
	/**
	 * The constructor.
	 * @param pos The number of positive responses.
	 * @param neg The number of negative responses.
	 * @param neu The number of neutral responses.
	 * @param err The number of errors.
	 */
	public AlchemyReponseWrapper(int pos, int neg, int neu, int err)
	{
		this.positive = pos;
		this.negative = neg;
		this.neutral = neu;
		this.errors = err;
	}

	public int getPositive() 
	{
		return positive;
	}

	public int getNegative() 
	{
		return negative;
	}

	public int getNeutral() 
	{
		return neutral;
	}

	public int getErrors() 
	{
		return errors;
	}
	
	
}

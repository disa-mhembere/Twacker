package edu.jhu.twacker.model.query.sentiment140;

/**
 * The Data response type from the Sentiment140 API.
 * 
 * @author Daniel Deutsch
 */
public class Sentiment140Data 
{
	/**
	 * The text that this piece of data reprsents.
	 */
	private String text;
	
	/**
	 * The polarity of the text, 0 for negative, 2 for neutral, and 4 for positive.
	 */
	private int polarity;
	
	/**
	 * The text to test for polairty, like the search term.
	 */
	private String query;
	
	/**
	 * The empty constructor.
	 */
	public Sentiment140Data()
	{
		// empty
	}
	
	/**
	 * Retrieves the text.
	 * @return The text.
	 */
	public String getText()
	{
		return this.text;
	}

	/**
	 * Retrieves the polarity.
	 * @return The polarity.
	 */
	public int getPolarity()
	{
		return this.polarity;
	}
	
	/**
	 * Retrieves the query.
	 * @return The query.
	 */
	public String getQuery()
	{
		return this.query;
	}
	
	public String toString()
	{
		return "" + this.polarity;
	}
}

/**
 * OOSE Project - Group 4
 * SentimentExec.java
 */
package edu.jhu.twacker.model.query;


import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.conn.ClientConnectionManager;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.params.BasicHttpParams;
import org.apache.http.params.HttpParams;
import org.apache.http.util.EntityUtils;

import com.google.gson.Gson;

import edu.jhu.twacker.model.query.alchemy.AlchemyReponseWrapper;
import edu.jhu.twacker.model.query.sentiment140.Sentiment140Response;
import edu.jhu.twacker.model.query.twitter.SearchTerm;
import edu.jhu.twacker.model.query.twitter.tweet.Tweet;

/**
 * This class is responsible for executing the query to obtain the data
 * to create the sentiment analysis graphs. 
 * 
 * The way it works is by creating a <code>Streamer</code> object to interact
 * with the Twitter API. It lets the Streamer gather Tweets for a given
 * amount of time. It then retrieves the Tweets and analyzes them for their
 * sentiment using the Alchemy API.
 * 
 * The result is a list of counts in the form of [positive neutral negative total].
 * 
 * The Alchemy API documentation can be found here {@link http://www.alchemyapi.com/api/sentiment/}
 * 
 * @author Daniel Deutsch
 */
public class SentimentExec extends QueryExec
{	
	/**
	 * The term to search for.
	 */
	private String search;
	
	/**
	 * The number of positive Tweets.
	 */
	private int positive;
	
	/**
	 * The number of neutral Tweets.
	 */
	private int neutral;
	
	/**
	 * The number of negative Tweets.
	 */
	private int negative;
	
	/**
	 * The total number of Tweets analyzed not including errors.
	 */
	private int total;
	
	/**
	 * The number of Tweets that produced an error from the Alchemy API.
	 */
	private int errors;
	
	/**
	 * The class that contains the result data.
	 */
	private AlchemyReponseWrapper response;
	
	public SentimentExec(String search)
	{
		this.search = search;
	}
	
	/**
	 * Executes the query to search for the given String.
	 */
	public void run()
	{	
		try 
		{
			Sentiment140Response response = sentiment140Analysis(getTweets());
			this.positive = response.positive();
			this.negative = response.negative();
			this.neutral = response.neutral();
			
			this.total = this.positive + this.neutral + this.negative;
			
			System.out.println(toString());
		}
		catch (NullPointerException e)
		{
			this.positive = 0;
			this.negative = 0;
			this.neutral = 0;
			this.total = 0;
		}

		this.response = new AlchemyReponseWrapper(this.positive, this.negative, this.neutral, this.errors);
	}
	
	/**
	 * Retrieves the results of the query.
	 * @return The results object.
	 */
	public AlchemyReponseWrapper getResults()
	{
		return this.response;
	}
	
	/**
	 * Gathers the Tweets to be analyzed and converts them into a list
	 * of their text.
	 * @return The list of the text.
	 */
	private List<String> getTweets()
	{
		SearchTerm searcher = new SearchTerm(this.search);
		List<Tweet> tweets = searcher.getTweets();
		
		List<String> text = new LinkedList<String>();
		for (Tweet tweet : tweets)
		{
			if (tweet.getText() != null)
				text.add(tweet.getText());
		}
		
		return text;
	}
	
	public Sentiment140Response sentiment140Analysis(List<String> tweets)
	{	
		List<String> newTweets = new ArrayList<String>();
		for (String tweet : tweets)
		{
			// would not let me do tweet = tweet.replace() for some reason?
			String newTweet = tweet.replace("\"", " ");
			newTweets.add(newTweet);
		}
		tweets = newTweets;
		
		String body = this.packageTweets(tweets);
		
		HttpResponse response = this.sendHttpPostWithTweets(body);
		
		Gson gson = new Gson();
		
		try
		{
			return gson.fromJson(EntityUtils.toString(response.getEntity()), Sentiment140Response.class);
		}
		catch (Exception e)
		{
			// something bad went wrong and we can't recover
			return null;
		}
	}
	
	/**
	 * Performs the http post with the body contents as the Tweets to the Sentiment140 API.
	 * @param body The tweets to set as the body of the response.
	 * @return The HttpResponse.
	 */
	private HttpResponse sendHttpPostWithTweets(String body)
	{
		// form the connection
		// comment this section out if running locally
		HttpParams httpParams = new BasicHttpParams();
		ClientConnectionManager connectionManager = new GAEConnectionManager();
		HttpClient client = new DefaultHttpClient(connectionManager, httpParams);
				
		// uncomment this section if running locally
//		HttpClient client = new DefaultHttpClient();
		
		HttpPost post = new HttpPost("http://www.sentiment140.com/api/bulkClassifyJson");
		
		// add the Tweets to the body
		try
		{
			StringEntity se = new StringEntity(body);
			post.setEntity(se);

			return client.execute(post);
		}
		catch (Exception e)
		{
			// probably cannot recover. No data.
			return null;
		}
	}
	
	private String packageTweets(List<String> tweets)
	{
		String json = "{\"data\": [";
		for (String tweet : tweets)
		{
			json = json + "{\"text\": \"" + tweet + "\", \"query\": \"" + this.search + "\"},";
		}
		json = json.substring(0, json.length() - 1);
		json = json + "]}";
		
		return json;
	}

	/**
	 * Creates a JSON representation of the results from this query.
	 */
	public String toString()
	{
		return "\"sentiment\": { \"positive\" : \"" + this.positive + "\", \"negative\" : \"" + this.negative +
				"\", \"neutral\" : \"" + this.neutral + "\", \"total\" : \"" + this.total + "\", " +
				"\"errors\": \"" + this.errors + "\" }";
	}
	
	/**
	 * Tests the <code>SentimentExec</code> class.
	 * @throws UnsupportedEncodingException 
	 */
	public static void main(String[] args) throws Exception
	{	
		SentimentExec sentiment = new SentimentExec("Obama");
		sentiment.run();
		
		System.out.println(sentiment);
	}
}

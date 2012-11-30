package edu.jhu.twacker.model.tests;

import static org.junit.Assert.fail;

import java.util.List;

import org.junit.Test;

import edu.jhu.twacker.model.query.twitter.SearchTerm;
import edu.jhu.twacker.model.query.twitter.tweet.Tweet;

public class SearchTermTest 
{

	@Test
	public void test() 
	{
		testSanityRun();
		testNewYorkTimes();
		testWeirdInput();
	}
	
	private void testSanityRun()
	{
		SearchTerm search = new SearchTerm("Obama");
		List<Tweet> tweets = search.getTweets();

		if (tweets.size() < 50)
			fail("Obama did not return enough Tweets");
	}

	private void testNewYorkTimes()
	{
		SearchTerm search = new SearchTerm("New York Times");
		List<Tweet> tweets = search.getTweets();
		
		if (tweets.size() < 50)
			fail("New York Times did not return enough Tweets");
	}
	
	private void testWeirdInput()
	{
		SearchTerm search = new SearchTerm("1 2!@##@$%$^&^*(&){][`~ jajwe");
		List<Tweet> tweets = search.getTweets();
		
		if (tweets.size() != 0)
			fail("Random string got results when it should not have");
	}
}

package edu.jhu.twacker.model.tests;

import static org.junit.Assert.*;

import org.junit.Test;

import edu.jhu.twacker.model.query.ExpertsExec;

/**
 * This class tests the functionality of the ExpertsExec class.
 * 
 * @author Daniel Deutsch
 */
public class ExpertsExecTest
{
	/**
	 * The ExpertsExec object that will be tested.
	 */
	private ExpertsExec experts;
	
	/**
	 * Tests to see if it retrieves the correct expert results for various search terms.
	 * These terms were chosen because it is unlikely that their results are going to change
	 * frequently.
	 */
	@Test
	public void test()
	{
		testObama();
		testMicrosoft();
		testApple();
		testNewYorkTimes();
		testWeirdSearch();
	}

	private void testObama()
	{
		this.experts = new ExpertsExec("Obama");
		this.experts.run();

		if (!this.experts.toString().equals("\"experts\" : { \"experts\" : [{ \"username\" : \"Barack Obama\", \"photoUrl\" : \"http://a0.twimg.com/profile_images/2824616796/2d3383532bbc7bcc28f7a07e69cfe25e_normal.png\", \"influenceLevel\" : \"10\", \"url\" : \"http://twitter.com/barackobama\" } , { \"username\" : \"BIG GUNS\", \"photoUrl\" : \"http://a0.twimg.com/profile_images/2873776625/c2a83b7ab13b80e4999deaa9f72fbc46_normal.jpeg\", \"influenceLevel\" : \"10\", \"url\" : \"http://twitter.com/obama_games\" } , { \"username\" : \"Mark Knoller\", \"photoUrl\" : \"http://a0.twimg.com/profile_images/137394623/knoller_normal.jpg\", \"influenceLevel\" : \"10\", \"url\" : \"http://twitter.com/markknoller\" } , { \"username\" : \"Katy\", \"photoUrl\" : \"http://a0.twimg.com/profile_images/2848657255/e2dfc6a96218f089d1fa195ccbfe6099_normal.jpeg\", \"influenceLevel\" : \"10\", \"url\" : \"http://twitter.com/katyinindy\" } , { \"username\" : \"The White House\", \"photoUrl\" : \"http://a0.twimg.com/profile_images/2741210047/6ec930886044fa31e56179bb4c202ac3_normal.png\", \"influenceLevel\" : \"10\", \"url\" : \"http://twitter.com/whitehouse\" } ] }") &&
				!this.experts.toString().equals("\"experts\" : { \"experts\" : [{ \"username\" : \"Barack Obama\", \"photoUrl\" : \"http://a0.twimg.com/profile_images/2824616796/2d3383532bbc7bcc28f7a07e69cfe25e_normal.png\", \"influenceLevel\" : \"10\", \"url\" : \"http://twitter.com/barackobama\" } , { \"username\" : \"BIG GUNS\", \"photoUrl\" : \"http://a0.twimg.com/profile_images/2873776625/c2a83b7ab13b80e4999deaa9f72fbc46_normal.jpeg\", \"influenceLevel\" : \"10\", \"url\" : \"http://twitter.com/obama_games\" } , { \"username\" : \"Mark Knoller\", \"photoUrl\" : \"http://a0.twimg.com/profile_images/137394623/knoller_normal.jpg\", \"influenceLevel\" : \"10\", \"url\" : \"http://twitter.com/markknoller\" } , { \"username\" : \"Katy\", \"photoUrl\" : \"http://a0.twimg.com/profile_images/2848657255/e2dfc6a96218f089d1fa195ccbfe6099_normal.jpeg\", \"influenceLevel\" : \"10\", \"url\" : \"http://twitter.com/katyinindy\" } , { \"username\" : \"f396\", \"photoUrl\" : \"http://a0.twimg.com/profile_images/2557330961/rbhztjgigar4kd4chb2y_normal.jpeg\", \"influenceLevel\" : \"10\", \"url\" : \"http://twitter.com/f396\" } ] }"))
			fail("Retrieved the incorrect expert results for Obama");
	}
	
	private void testMicrosoft()
	{
		this.experts = new ExpertsExec("Microsoft");
		this.experts.run();
		
		if (!this.experts.toString().equals("\"experts\" : { \"experts\" : [{ \"username\" : \"Microsoft\", \"photoUrl\" : \"http://a0.twimg.com/profile_images/2535822544/secztwqh31xo8xydi4kq_normal.png\", \"influenceLevel\" : \"10\", \"url\" : \"http://twitter.com/microsoft\" } , { \"username\" : \"Microsoft Support\", \"photoUrl\" : \"http://a0.twimg.com/profile_images/1572699660/MSHelps_StartTile_normal.jpg\", \"influenceLevel\" : \"9\", \"url\" : \"http://twitter.com/microsofthelps\" } , { \"username\" : \"George Clegg\", \"photoUrl\" : \"http://a0.twimg.com/profile_images/320320579/George2_normal.jpg\", \"influenceLevel\" : \"9\", \"url\" : \"http://twitter.com/logicald\" } , { \"username\" : \"Everything Microsoft\", \"photoUrl\" : \"http://a0.twimg.com/profile_images/76731057/ems_logo_normal.png\", \"influenceLevel\" : \"9\", \"url\" : \"http://twitter.com/everythingms\" } , { \"username\" : \"Office\", \"photoUrl\" : \"http://a0.twimg.com/profile_images/2403217978/rqfl27w62vzudsujwmrp_normal.jpeg\", \"influenceLevel\" : \"10\", \"url\" : \"http://twitter.com/office\" } ] }"))
			fail("Retrieved the incorrect expert results for Microsoft");
	}

	private void testApple()
	{
		this.experts = new ExpertsExec("Apple");
		this.experts.run();
		
		if (!this.experts.toString().equals("\"experts\" : { \"experts\" : [{ \"username\" : \"Apple News\", \"photoUrl\" : \"http://a0.twimg.com/profile_images/192098593/Apple_normal.png\", \"influenceLevel\" : \"8\", \"url\" : \"http://twitter.com/applenws\" } , { \"username\" : \"Apple Mac Geek\", \"photoUrl\" : \"http://a0.twimg.com/profile_images/85072933/IMG_0011_normal.JPG\", \"influenceLevel\" : \"9\", \"url\" : \"http://twitter.com/applemacgeek\" } , { \"username\" : \"AppleInsider\", \"photoUrl\" : \"http://a0.twimg.com/profile_images/77313025/apple-touch-icon_bigger_normal.png\", \"influenceLevel\" : \"10\", \"url\" : \"http://twitter.com/appleinsider\" } , { \"username\" : \"MacTrast\", \"photoUrl\" : \"http://a0.twimg.com/profile_images/2288222893/c6cn1051jn16zh3mrst7_normal.png\", \"influenceLevel\" : \"10\", \"url\" : \"http://twitter.com/mactrast\" } , { \"username\" : \"MacMagazine.com.br\", \"photoUrl\" : \"http://a0.twimg.com/profile_images/1355189510/avatar_normal.png\", \"influenceLevel\" : \"10\", \"url\" : \"http://twitter.com/macmagazine\" } ] }"))
			fail("Incorrect results for Apple experts");
	}	
	
	private void testNewYorkTimes()
	{
		this.experts = new ExpertsExec("New York Times");
		this.experts.run();
		
		if (!this.experts.toString().equals("\"experts\" : { \"experts\" : [{ \"username\" : \"NY Times NavelGaze\", \"photoUrl\" : \"http://a0.twimg.com/profile_images/1232861735/icon_nyt01_normal.png\", \"influenceLevel\" : \"9\", \"url\" : \"http://twitter.com/nyttweets\" } , { \"username\" : \"Mediagazer\", \"photoUrl\" : \"http://a0.twimg.com/profile_images/740681844/mediaGazer_twitter2white_normal.png\", \"influenceLevel\" : \"9\", \"url\" : \"http://twitter.com/mediagazer\" } , { \"username\" : \"Yahoo!\", \"photoUrl\" : \"http://a0.twimg.com/profile_images/2508221033/949vu4lyu86p4eituhy3_normal.jpeg\", \"influenceLevel\" : \"10\", \"url\" : \"http://twitter.com/yahoo\" } , { \"username\" : \"YouTube\", \"photoUrl\" : \"http://a0.twimg.com/profile_images/1616286352/youtube-stacked_google_200px_normal.png\", \"influenceLevel\" : \"10\", \"url\" : \"http://twitter.com/youtube\" } , { \"username\" : \"memeorandum\", \"photoUrl\" : \"http://a0.twimg.com/profile_images/791192176/memeo_iicon_normal.gif\", \"influenceLevel\" : \"8\", \"url\" : \"http://twitter.com/memeorandum\" } ] }"))
			fail("Incorrect results for New York Times experts");
	}

	private void testWeirdSearch()
	{
		this.experts = new ExpertsExec("aA.*- !?%+[{0(|~`");
		this.experts.run();
		
		if (this.experts.getResults() == null)
			fail("Did not get any response for the weird string. The result should not be null");
	}
}

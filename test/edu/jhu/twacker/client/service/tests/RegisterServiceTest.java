/**
 * 
 */
package edu.jhu.twacker.client.service.tests;

import com.google.gwt.junit.client.GWTTestCase; 

import edu.jhu.twacker.client.view.RegisterView;

/**
 * Class to test the functionality of the RegisterService interface
 * and all that implement it
 * @author Disa Mhembere
 */
public class RegisterServiceTest extends GWTTestCase
{
	
	  /**
	   * Must refers to a valid module that sources this class.
	   */
	  public String getModuleName() {                                       
	    return "edu.jhu.twacker.Twacker";
	  }

	  /**
	   * Add as many tests as you like.
	   */
	  public void testSimple() {
	    assertTrue(true);
	  }
	  
	  
	  /**
	   * Test the register service by adding a Test user & 
	   * checking the infoLabel which indicates the result of 
	   * the service is correct. 
	   * *NOTE: Must delete this user from the DB after creating
	   */
	  public void testRegisterService()
	  { // TODO: DM Doesnt run yet.
		  RegisterView registerView = new RegisterView(); 
		  registerView.requestNewCredentials("TestFirstName", "TestLastName", "TestUsername", "pass", "pass", "tester@test.com");
		  assertEquals("The info Label is not equal to the expected value", "Success!", registerView.getInfoLabel());
	  }

}

/**
 * OOSE Project - Group 4
 * {@link RegisterViewTest}.java
 */
package edu.jhu.twacker.client.view.tests;

import com.google.gwt.junit.client.GWTTestCase; 
import edu.jhu.twacker.client.view.RegisterView;

/**
 * Class to test the functionality of the {@link RegisterView}
 * The idea is to test the visual aspects of the view & not
 * the RPC calls
 * @author Disa Mhembere
 */
public class RegisterViewTest extends GWTTestCase
{
	
	  /**
	   * Refers to module that sources this class
	   * @return the module containing the onModuleLoad() method
	   */
	  public String getModuleName() {                                       
	    return "edu.jhu.twacker.Twacker";
	  }

	  
	  /**
	   * Test the register service client side by adding a Test user & 
	   * checking the infoLabel which indicates the result of request of
	   * the RPC call. 
	   * *Note the RPC redirect cannot be tested directly from here.
	   */
	  public void testRegisterService()
	  { 
		  RegisterView registerView = new RegisterView();
		   
		  registerView.requestNewCredentials("Testfirstname", "Testlastname", "Testusername", "passer", "passer", "tester@test.com");

		  assertEquals("The info Label is not equal to the expected value", "Requesting validation...", registerView.getInfoLabel());
	  }
}

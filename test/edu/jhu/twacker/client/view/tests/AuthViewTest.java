/**
 * OOSE Project - Group 4
 * {@link AuthViewTest}.java
 */
package edu.jhu.twacker.client.view.tests;

import com.google.gwt.junit.client.GWTTestCase; 
import edu.jhu.twacker.client.view.AuthView;

/**
 * Class to test the functionality of the {@link AuthView}
 * The idea is to test the visual aspects of the view & not
 * the RPC calls
 * @author Disa Mhembere
 */
public class AuthViewTest extends GWTTestCase
{
	
	  /**
	   * Refers to module that sources this class
	   * @return the module containing the onModuleLoad() method
	   */
	  public String getModuleName() {                                       
	    return "edu.jhu.twacker.Twacker";
	  }

	  
	  /**
	   * Test the authentication service client side by signing in a test user & 
	   * checking the infoLabel which indicates the result of request of
	   * the RPC call. 
	   * *Note the RPC redirect cannot be tested directly from here.
	   */
	  public void testSignInService()
	  { 
		  AuthView authView = new AuthView();
		   
		  authView.requestSignIn("Testusername", "passer");
		  
		  System.out.println("InfoLabel after = " + authView.getInfoLabel());

		  assertEquals("The info Label is not equal to the expected value", "Requesting validation...", authView.getInfoLabel());
	  }
}

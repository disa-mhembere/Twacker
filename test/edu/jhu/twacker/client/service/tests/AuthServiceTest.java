/**
 * OOSE Project - Group 4
 * {@link AuthServiceTest}.java
 */
package edu.jhu.twacker.client.service.tests;

import org.junit.Test;

import com.google.gwt.core.client.GWT;
import com.google.gwt.junit.client.GWTTestCase;
import com.google.gwt.user.client.rpc.AsyncCallback;

import edu.jhu.twacker.client.service.AuthService;
import edu.jhu.twacker.client.service.AuthServiceAsync;

/**
 * Class to test the correct functionality of RPCs to
 * the Authentication service.
 * @author Disa Mhembere
 *
 */
public class AuthServiceTest extends GWTTestCase
{
	/**
	   * Must refers to a valid module that sources this class.
	   * @return the module containing the onModuleLoad() method
	   */
	  public String getModuleName() {                                       
	    return "edu.jhu.twacker.Twacker";
	  }

	@Test
	/**
	 * Test the set & get username methods to see
	 * if we set the username through an RPC we 
	 * can retrieve the same username back via the get
	 */
	public void testSetAndGetUsername()
	{ 
		final AuthServiceAsync authService = GWT.create(AuthService.class);
		authService.setUsername("test", new AsyncCallback<Void>()
		{
			@Override
			public void onSuccess(Void result)
			{
				authService.getUsername(new AsyncCallback<String>()/*This is throwing an exception*/
				{
					public void onSuccess(String result)
					{
						System.out.println("The returned Username = " + result);
						assertEquals("The username is incorrect testSetAndGetUsername",true, result.equals("test"));
					}
					
					@Override
					public void onFailure(Throwable caught)
					{
						fail("RPC failure to getUsername");
					}
					
				});	
			}
			
			@Override
			public void onFailure(Throwable caught)
			{
				fail("RPC failure to setUsername");
			}
		});
	}

}

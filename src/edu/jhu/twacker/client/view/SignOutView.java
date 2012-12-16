/**
 * OOSE Project - Group 4
 * {@link SignOutView}.java
 */
package edu.jhu.twacker.client.view;

import com.allen_sauer.gwt.log.client.Log;
import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.History;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Hyperlink;
import com.google.gwt.user.client.ui.VerticalPanel;

import edu.jhu.twacker.client.service.AuthService;
import edu.jhu.twacker.client.service.AuthServiceAsync;

/**
 * Simple view to sign out a user & redirect to the HOMEPAGE under the "guest"
 * account
 * 
 * @author Disa Mhembere
 * 
 */
public class SignOutView extends View
{
	private final AuthServiceAsync authService = GWT.create(AuthService.class);
	private VerticalPanel mainPanel;
	private Button signOutButton;
	private Button imFeelingLuckyButton;
	private Hyperlink returnToHome;
	private HorizontalPanel buttonPanel;

	/**
	 * Default constructor
	 */
	public SignOutView()
	{
		super();
		mainPanel = new VerticalPanel();
		buttonPanel = new HorizontalPanel();
		signOutButton = new Button("Sign Out");
		returnToHome = new Hyperlink("Don't want to sign out? Return to Home",
				"HOME");
		imFeelingLuckyButton = new Button("I'm Feeling Lucky");

		mainPanel.setSpacing(20);
		buttonPanel.setSpacing(10);
		buttonPanel.add(signOutButton);
		buttonPanel.add(imFeelingLuckyButton);
		mainPanel.add(buttonPanel);
		mainPanel.add(returnToHome);
		
		leftSidePanel.add(mainPanel);

		// Signout button handler
		signOutButton.addClickHandler(new ClickHandler() {

			@Override
			public void onClick(ClickEvent event)
			{
				signOutUser();
			}
		});

		// If you're feeling lucky you won't after this!
		imFeelingLuckyButton.addClickHandler(new ClickHandler() {

			@Override
			public void onClick(ClickEvent event)
			{
				Window.alert("This ain't Google fool! Get outta here!");
			}
		});

	}

	/**
	 * Call to the service responsible for signing users out
	 */
	private void signOutUser()
	{
		authService.signOut(new AsyncCallback<String>() {
			@Override
			public void onSuccess(String result)
			{
				History.newItem("HOME"); // redirect to home
			}

			@Override
			public void onFailure(Throwable caught)
			{
				Log.debug("DM Failure: edu.jhu.twacker.client.view.SignOutView constructor"
						+ caught.getLocalizedMessage());
			}
		});
	}
}

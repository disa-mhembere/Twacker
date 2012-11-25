/**
 * OOSE Project - Group 4
 * AuthView.java
 */
package edu.jhu.twacker.client.view;

// Visual features
import com.google.gwt.user.client.History;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.Grid;
import com.google.gwt.user.client.ui.PasswordTextBox;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.Anchor;

// Event handling

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;

// Authentication
import com.google.gwt.core.client.GWT;

import edu.jhu.twacker.client.data.AuthInfo;
import edu.jhu.twacker.client.service.AuthService;
import edu.jhu.twacker.client.service.AuthServiceAsync;

/**
 * View for user sign-in & sign-up If a user chooses to not sign-up/sign-in the
 * default username & password are applied
 * 
 * @author Disa Mhembere
 */
public class AuthView extends View
{

	private AuthInfo authInfo = null;
	private final AuthServiceAsync authService = GWT.create(AuthService.class);
	private Anchor gmailSignUpAnchor = new Anchor("Gmail Sign-up", true,
			"https://accounts.google.com/SignUp?service=mail&continue=https%3A%2F%2"
					+ "Fmail.google.com%2Fmail%2F&ltmpl=default", "_blank"); // Link
																								// to
																								// gmail
																								// sign-up

	// Sign-in

	private VerticalPanel signInPanel = new VerticalPanel();
	private TextBox usernameTextBox = new TextBox();
	private PasswordTextBox signInPasswordBox = new PasswordTextBox();
	private Button signInButton = new Button("Sign-in");
	private Button continueNoSignInButton = new Button("I don't want to Sign-in");
	private Label signInInfoLabel = new Label();

	/**
	 * Default constructor loads up sign-in & sign-up view forms
	 */
	public AuthView()
	{

		onSignedIn();
		usernameTextBox.setText("e.g gangnam");
		signInInfoLabel.setVisible(false);

		// Assemble sign-in panel
		signInPanel.add(new Label("Twacker Sign-in"));
		signInPanel.add(new Label("Gmail address"));
		signInPanel.add(usernameTextBox);
		signInPanel.add(new Label("Twacker Password"));
		signInPanel.add(signInPasswordBox);

		Grid buttonPanel = new Grid(1, 2);
		buttonPanel.setWidget(0, 0, signInButton);
		buttonPanel.setWidget(0, 1, continueNoSignInButton);
		signInPanel.add(buttonPanel);
		signInPanel.add(gmailSignUpAnchor);
		signInPanel.add(signInInfoLabel);

		// Listen for mouse events on the sign-in button.
		signInButton.addClickHandler(new ClickHandler()
		{
			@Override
			public void onClick(ClickEvent event)
			{
				requestLogin();
			}
		});

		continueNoSignInButton.addClickHandler(new ClickHandler()
		{
			@Override
			public void onClick(ClickEvent event)
			{
				requestNoLogin();
			}
		});

		initWidget(signInPanel); // instantiate widget

	}

	/**
	 * Requests access to Twacker homepage without passing a username and
	 * password thus passes in the default values
	 */
	public void requestNoLogin()
	{
		signInInfoLabel.setVisible(false);
		History.newItem("HOME"); // No login? Then redirect to homepage
	}

	/**
	 * Requests access to Twacker homepage via a login username and password
	 */
	public void requestLogin()
	{
		signInInfoLabel.setVisible(false);
		
		if (authInfo == null)
			authInfo = new AuthInfo();

		authService.signIn(GWT.getHostPageBaseURL(),
				new AsyncCallback<AuthInfo>()
				{
					public void onFailure(Throwable error)
					{
						signInInfoLabel.setVisible(true);
						System.err.println("DM: Error on sign-in: " + error.getLocalizedMessage()); // TODO : DM
						signInInfoLabel.setText("DM: Error on sign-in: " + error.getLocalizedMessage());
					}

					public void onSuccess(AuthInfo result)
					{
						authInfo = result;
						if (authInfo.isLoggedIn())
						{
							signInInfoLabel.setVisible(true);
							signInInfoLabel.setText("You are already signed-in");
							History.newItem("HOME");
						} else // User isn't logged in - then verify email & password & load up home
						{
							signInInfoLabel.setVisible(true);
							
							signInInfoLabel.setText("Login email: " + result.getEmailAddress() + 
									"Login nickname " + result.getNickname());
							
							//History.newItem("AUTH");
							//Window.Location.reload();
						}
					}
				});

	}
	
	/**
	 * TODO : DM
	 */
	public void onSignedIn()
	{
		if (authInfo == null)
			return;
		
		if (!authInfo.isLoggedIn())
			return;
		
		if (authInfo.isLoggedIn())
			History.newItem("HOME");	
	}
}

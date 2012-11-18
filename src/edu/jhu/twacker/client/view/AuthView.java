/**
 * OOSE Project - Group 4
 * AuthView.java
 */
package edu.jhu.twacker.client.view;

// Visual features
import com.google.gwt.user.client.History;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.Grid;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.PasswordTextBox;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.client.ui.Label;

// Event handling
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;

import edu.jhu.twacker.client.manager.AuthManager;
import edu.jhu.twacker.client.manager.AuthManagerCallback;
import edu.jhu.twacker.shared.Session;

/**
 * View for user sign-in & sign-up
 * If a user chooses to not sign-up/sign-in the default
 * username & passphrase are applied
 * 
 * @author Disa Mhembere
 */
public class AuthView extends View
{

	private HorizontalPanel mainPanel = new HorizontalPanel();

	// Sign-up
	private VerticalPanel signUpPanel = new VerticalPanel();
	private TextBox signUpUsernameTextBox = new TextBox();
	private PasswordTextBox signUpPasswordTextBox = new PasswordTextBox();
	private PasswordTextBox confirmSignUpPasswordTextBox = new PasswordTextBox();
	private Button signUpButton = new Button("Sign-up");
	private Label signUpinfoLabel = new Label();

	private VerticalPanel signInPanel = new VerticalPanel();
	private TextBox usernameTextBox = new TextBox();
	private PasswordTextBox signInPasswordBox = new PasswordTextBox();
	private Button signInButton = new Button("Sign-in");
	private Button continueNoSignInButton = new Button("I don't want to Sign-in");
	private Label signInInfoLabel = new Label();

	/**
	 * Default constructor loads up sign-in & sign-up view
	 * forms
	 */
	public AuthView()
	{

		usernameTextBox.setText("e.g gangnam");
		signInInfoLabel.setVisible(false);

		// Assemble sign-in panel
		signInPanel.add(new Label("Twacker Sign-in"));
		signInPanel.add(new Label("Twacker Username"));
		signInPanel.add(usernameTextBox);
		signInPanel.add(new Label("Twacker Password"));
		signInPanel.add(signInPasswordBox);

		Grid buttonPanel = new Grid(1, 2);
		buttonPanel.setWidget(0, 0, signInButton);
		buttonPanel.setWidget(0, 1, continueNoSignInButton);
		signInPanel.add(buttonPanel);
		signInPanel.add(signInInfoLabel);

		mainPanel.add(signInPanel);

		// Assemble Sign-up panel
		signUpPanel.add(new Label("Twacker Sign-up"));
		signUpPanel.add(new Label("New Username"));
		signUpPanel.add(signUpUsernameTextBox);
		signUpPanel.add(new Label("New Password"));
		signUpPanel.add(signUpPasswordTextBox);
		signUpPanel.add(new Label("Re-enter Password"));
		signUpPanel.add(confirmSignUpPasswordTextBox);
		signUpPanel.add(signUpButton);
		signUpPanel.add(signUpinfoLabel);

		mainPanel.add(signUpPanel);

		// Listen for mouse events on the Add button.
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

		initWidget(mainPanel); // instantiate widget

	}

	/**
	 * Requests access to Twacker homepage
	 * without passing a username and password
	 * thus passes in the default values
	 */
	public void requestNoLogin()
	{
		signInInfoLabel.setVisible(false);
		AuthManager.getInstance().signIn("username", "password",
				new AuthManagerCallback<Session>() // Generic user
				{
					@Override
					public void onSuccess(Session result)
					{
						History.newItem("HOME");
					}

					// Cant get to this state
					@Override
					public void onFail()
					{
						signInInfoLabel.setVisible(true);
						signInInfoLabel.setText("This should never happen!");
					}
				});

	}

	/**
	 * Requests access to Twacker homepage
	 * via a login username and password
	 */
	public void requestLogin()
	{
		signInInfoLabel.setVisible(false);
		AuthManager.getInstance().signIn(usernameTextBox.getText(),
				signInPasswordBox.getText(), new AuthManagerCallback<Session>()
				{
					@Override
					public void onSuccess(Session result)
					{
						History.newItem("HOME");
					}

					@Override
					public void onFail()
					{
						signInInfoLabel.setVisible(true);
						signInInfoLabel.setText("Login Failed!");
					}
				});
	}
	
	
	public void requestRegister()
	{
		// DM : TODO
	}
	

}

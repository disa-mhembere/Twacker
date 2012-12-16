/** 
 * OOSE Project - Group 4
 * {@link RegisterView}.java
 */
package edu.jhu.twacker.client.view;

import com.google.gwt.user.client.History;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.Grid;
import com.google.gwt.user.client.ui.Hyperlink;
import com.google.gwt.user.client.ui.PasswordTextBox;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.client.ui.Label;

// Event handling

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;

// Authentication
import com.google.gwt.core.client.GWT;

import edu.jhu.twacker.client.service.RegisterService;
import edu.jhu.twacker.client.service.RegisterServiceAsync;
import edu.jhu.twacker.shared.FieldVerifier;

/**
 * Class to allow users to register for the Twacker website
 * 
 * @author Disa Mhembere
 * 
 */
public class RegisterView extends View
{

	private final RegisterServiceAsync registerService = GWT
			.create(RegisterService.class);
	Hyperlink accountAlreadyHyperlink = new Hyperlink(
			"Have a username? Sign-in!", "AUTH");

	// Sign-in
	private VerticalPanel signUpPanel = new VerticalPanel();
	private TextBox firstNameTextBox = new TextBox();
	private TextBox lastNameTextBox = new TextBox();
	private TextBox usernameTextBox = new TextBox();
	private PasswordTextBox signUpPasswordBox = new PasswordTextBox();
	private PasswordTextBox verifyPasswordBox = new PasswordTextBox();
	private Button signUpButton = new Button("Sign-up");
	private Button continueNoSignUpButton = new Button(
			"Guest");

	private TextBox emailTextBox = new TextBox();
	private Label infoLabel = new Label();

	/**
	 * Default constructor loads up sign-in & sign-up view forms
	 */
	public RegisterView()
	{
		super();
		infoLabel.setVisible(false);

		// Assemble sign-up panel
		signUpPanel.add(new Label("Sign-up!"));
		signUpPanel.add(new Label("First Name:"));
		signUpPanel.add(firstNameTextBox);
		signUpPanel.add(new Label("Last Name:"));
		signUpPanel.add(lastNameTextBox);
		signUpPanel.add(new Label("Username:"));
		signUpPanel.add(usernameTextBox);
		signUpPanel.add(new Label("Enter password:"));
		signUpPanel.add(signUpPasswordBox);
		signUpPanel.add(new Label("Confirm password:"));
		signUpPanel.add(verifyPasswordBox);
		signUpPanel.add(new Label("Enter e-mail address:"));
		signUpPanel.add(emailTextBox);

		Grid buttonPanel = new Grid(1, 2);
		buttonPanel.setWidget(0, 0, signUpButton);
		buttonPanel.setWidget(0, 1, continueNoSignUpButton);
		signUpPanel.add(buttonPanel);
		signUpPanel.add(accountAlreadyHyperlink);

		rightSidePanel.add(infoLabel);
		leftSidePanel.add(signUpPanel);

		// Listen for mouse events on the sign-in button.
		signUpButton.addClickHandler(new ClickHandler() {
			@Override
			public void onClick(ClickEvent event)
			{
				requestNewCredentials(firstNameTextBox.getText(),
						lastNameTextBox.getText(), usernameTextBox.getText(),
						signUpPasswordBox.getText(),
						verifyPasswordBox.getText(), emailTextBox.getText());
			}
		});

		continueNoSignUpButton.addClickHandler(new ClickHandler() {
			@Override
			public void onClick(ClickEvent event)
			{
				requestNoLogin();
			}
		});

	}

	/**
	 * Requests access to Twacker homepage without passing a username and
	 * password thus passes in the default values
	 */
	public void requestNoLogin()
	{
		infoLabel.setVisible(false);
		History.newItem("HOME"); // No login? Then redirect to homepage
	}

	/**
	 * Requests new user name and password from register service after
	 * validating fields
	 * 
	 * @param firstName
	 *            the users first name
	 * @param lastName
	 *            the users last name
	 * @param username
	 *            the requested username
	 * @param pwd
	 *            the requested password
	 * @param confirmPwd
	 *            matching the requested password <code>pwd</code>
	 * @param email
	 *            the users email address
	 */
	public void requestNewCredentials(String firstName, String lastName,
			String username, String pwd, String confirmPwd, String email)
	{
		if (validateFields(firstName, lastName, username, pwd, confirmPwd,
				email)) {
			registerService.registerUser(firstName, lastName, username, pwd,
					email, new AsyncCallback<Void>() {

						@Override
						public void onSuccess(Void result)
						{
							infoLabel.setVisible(true);
							infoLabel.setText("Success!");
							History.newItem("HOME");
						}

						@Override
						public void onFailure(Throwable caught)
						{
							infoLabel.setVisible(true);
							infoLabel.setText("Username Already exists!");
							// Log.debug("DM: Failure!");
						}
					});
		}
	}

	/**
	 * Validate all input fields as being at least viable
	 * 
	 * @param firstName
	 *            the users first name
	 * @param lastName
	 *            the users last name
	 * @param username
	 *            the requested username
	 * @param pwd
	 *            the requested password
	 * @param confirmPwd
	 *            matching the requested password <code>pwd</code>
	 * @param email
	 *            the users email address
	 * @return true if all fields are OK else false
	 */
	public boolean validateFields(String firstName, String lastName,
			String username, String pwd, String confirmPwd, String email)
	{

		infoLabel.setVisible(false);
		// Confirm valid first name
		if (!FieldVerifier.isValidName(firstName)) {
			infoLabel.setVisible(true);
			infoLabel.setText("Invalid First Name");
			return false;
		}

		// Confirm valid last name
		if (!FieldVerifier.isValidName(lastName)) {
			infoLabel.setVisible(true);
			infoLabel.setText("Invalid Last Name");
			return false;
		}

		// Confirm valid user name
		if (!FieldVerifier.isValidUsername(username)) {
			infoLabel.setVisible(true);
			if (username.length() < 3) {
				infoLabel.setText("Username too short. Min of 3 characters.");
			} else {
				infoLabel
						.setText("Invalid username. (Btdubz: No special characters/spaces)");
			}
			return false;
		}

		// Confirm valid password
		if (!FieldVerifier.isValidPassword(pwd)) {
			infoLabel.setVisible(true);
			if (pwd.length() < 5) {
				infoLabel.setText("Password too short. Min 5 characters");
			} else {
				infoLabel.setText("Invalid Password. (Btdubz: No spaces)");
			}
			return false;
		}

		// Confirm password matching
		if (!pwd.equals(confirmPwd)) {
			infoLabel.setVisible(true);
			infoLabel.setText("Passwords do not match");
			return false;
		}

		// Confirm valid email address
		if (!FieldVerifier.isValidEmail(email)
				|| email.equalsIgnoreCase("someone@something.com")) {
			infoLabel.setVisible(true);
			infoLabel.setText("Invalid email address. Try again");
			return false;
		}

		infoLabel.setVisible(true);
		infoLabel.setText("Requesting validation...");
		return true;

	}

	/**
	 * Used to return the value of the infoLabel Mainly used for external
	 * testing
	 * 
	 * @return
	 */
	public String getInfoLabel()
	{
		return this.infoLabel.getText();

	}
	
	@Override
	// TODO : AT Comment on this
	public void updateView()
	{
		super.updateView();
		firstNameTextBox.setText("");
		lastNameTextBox.setText("");
		usernameTextBox.setText("");
		signUpPasswordBox.setText("");
		verifyPasswordBox.setText("");
		emailTextBox.setText("");
	}

}

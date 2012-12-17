/**
 * OOSE Project - Group 4
 * {@link AuthView}.java
 */
package edu.jhu.twacker.client.view;

// Visual features
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
import com.google.gwt.event.dom.client.KeyCodes;
import com.google.gwt.event.dom.client.KeyUpEvent;
import com.google.gwt.event.dom.client.KeyUpHandler;

// Authentication
import com.google.gwt.core.client.GWT;
import edu.jhu.twacker.client.service.AuthService;
import edu.jhu.twacker.client.service.AuthServiceAsync;
import edu.jhu.twacker.shared.FieldVerifier;

/**
 * View for user sign-in & sign-up If a user chooses to not sign-up/sign-in the
 * default username & password are applied
 * 
 * @author Disa Mhembere, Andy Tien
 */
public class AuthView extends View
{
	private final AuthServiceAsync authService = GWT.create(AuthService.class);
	Hyperlink registerHyperlink = new Hyperlink("No username? Sign-up!",
			"REGISTER");

	private VerticalPanel signInPanel = new VerticalPanel();
	private TextBox usernameTextBox = new TextBox();
	private PasswordTextBox signInPasswordBox = new PasswordTextBox();
	private Button signInButton = new Button("Sign-in");
	private Button continueNoSignInButton = new Button("Guest");
	private Label infoLabel = new Label();

	private Button whoami = new Button("WhoamI");

	/**
	 * Default constructor loads up sign-in & sign-up view forms
	 */
	public AuthView()
	{
		super();
		usernameTextBox.setText("e.g. gangnam");
		usernameTextBox.addStyleName("preClickTextBox");
		infoLabel.setVisible(false);

		// Assemble sign-in panel
		signInPanel.add(new Label("Twacker Sign-in"));
		signInPanel.add(new Label("Username"));
		signInPanel.add(usernameTextBox);
		signInPanel.add(new Label("Password"));
		signInPanel.add(signInPasswordBox);

		Grid buttonPanel = new Grid(1, 2);
		buttonPanel.setWidget(0, 0, signInButton);
		buttonPanel.setWidget(0, 1, continueNoSignInButton);
		signInPanel.add(buttonPanel);
		signInPanel.add(registerHyperlink);
		// signInPanel.add(infoLabel);
		rightSidePanel.add(infoLabel);

		// signInPanel.add(whoami);

		// Listen for mouse events on the sign-in button.
		signInButton.addClickHandler(new ClickHandler() {
			@Override
			public void onClick(ClickEvent event)
			{
				requestSignIn(usernameTextBox.getText(),
						signInPasswordBox.getText());
				// usernameTextBox.setText("");
				signInPasswordBox.setText("");
			}
		});

		signInPasswordBox.addKeyUpHandler(new KeyUpHandler() {

			@Override
			public void onKeyUp(KeyUpEvent event)
			{
				if (event.getNativeKeyCode() == KeyCodes.KEY_ENTER) {
					requestSignIn(usernameTextBox.getText(),
							signInPasswordBox.getText());
				}

			}
		});

		continueNoSignInButton.addClickHandler(new ClickHandler() {
			@Override
			public void onClick(ClickEvent event)
			{
				requestNoSignIn();
				usernameTextBox.setText("");
				signInPasswordBox.setText("");
			}
		});

		whoami.addClickHandler(new ClickHandler() {

			@Override
			public void onClick(ClickEvent event)
			{
				authService.getUsername(new AsyncCallback<String>() {
					@Override
					public void onSuccess(String result)
					{
						infoLabel.setVisible(true);
						infoLabel.setText("I am: " + result);
					}

					@Override
					public void onFailure(Throwable caught)
					{
						infoLabel.setText("There was a failure!");

					}
				});

			}
		});

		usernameTextBox.addClickHandler(new ClickHandler() {

			@Override
			public void onClick(ClickEvent event)
			{
				if (usernameTextBox.getStyleName().contains("preClickTextBox")) {
					usernameTextBox.removeStyleName("preClickTextBox");
					usernameTextBox.setText("");
				}
			}
		});

		leftSidePanel.add(signInPanel);
	}

	/**
	 * Requests access to Twacker homepage without passing a username and
	 * password thus passes in the default values
	 */
	public void requestNoSignIn()
	{
		infoLabel.setVisible(false);

		authService.setUsername("guest", new AsyncCallback<Void>() {

			@Override
			public void onSuccess(Void result)
			{
				infoLabel.setVisible(true);
				infoLabel.setText("Entering Twacker site as guest.");
				History.newItem("HOME"); // Don't want to login? Then redirect
											// to homepage
			}

			@Override
			public void onFailure(Throwable caught)
			{
				infoLabel.setVisible(true);
				infoLabel.setText("Failure to enter Twacker site as guest");
			}
		});
	}

	/**
	 * Requests access to Twacker homepage via a signIn username and password
	 */
	public void requestSignIn(String username, String password)
	{
		infoLabel.setVisible(false);
		if (validateFields(username, password)) {
			authService.signIn(username, password, new AsyncCallback<String>() {
				@Override
				public void onSuccess(String result)
				{
					if (result == null) {
						infoLabel.setVisible(true);
						infoLabel.setText("Incorrect username or password.");
					} else {
						infoLabel.setVisible(true);
						infoLabel.setText("Sign-in Sucessful! Welcome "
								+ result);
						History.newItem("HOME");
					}
				}

				@Override
				public void onFailure(Throwable caught)
				{
					infoLabel.setVisible(true);
					infoLabel.setText("Sign-in Failed! Try again..");
					// Log.debug("DM: Failure in edu.jhu.twacker.client.view Authview.requestSignIn");
				}
			});
		}
<<<<<<< HEAD

=======
>>>>>>> 11c1086808d3ac2665951a9d782f9a4114c7e31a
	}

	/**
	 * Validate input fields as being at least viable
	 * 
	 * @param username
	 *            the requested username
	 * @param pwd
	 *            the requested password
	 * @return true if all fields are OK else false
	 */
	public boolean validateFields(String username, String pwd)
	{

		// Confirm valid user name
		if (!FieldVerifier.isValidUsername(username)) {
			infoLabel.setVisible(true);
			if (username.length() < 3) {
				infoLabel
						.setText("Username too short. That can't be your username!");
			} else {
				infoLabel.setText("Invalid username. Try again");
			}
			return false;
		}

		// Confirm valid password
		if (!FieldVerifier.isValidPassword(pwd)) {
			infoLabel.setVisible(true);
			if (pwd.length() < 5) {
				infoLabel.setText("Password too short. Min 5 characters");
			} else {
				infoLabel.setText("Invalid Password. No whitespace permitted");
			}
			return false;
		}

		infoLabel.setVisible(true);
		infoLabel.setText("Requesting validation...");
		return true;
	}

	@Override
	public void updateView()
	{
		super.updateView();
		usernameTextBox.setText("e.g. gangnam");
		usernameTextBox.addStyleName("preClickTextBox");
		infoLabel.setText("");
		signInPasswordBox.setText("");
	}
}

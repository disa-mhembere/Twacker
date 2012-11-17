/**
 * OOSE Project - Group 4
 * LoginView.java
 */
package edu.jhu.twacker.client.view;

import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.PasswordTextBox;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.client.ui.Label;


/**
 * View for user login & registration
 * @author Disa Mhembere 
 */
public class LoginView extends View
{
	private VerticalPanel mainPanel = new VerticalPanel();
	private TextBox usernameTextBox = new TextBox();
	private PasswordTextBox loginPassword = new PasswordTextBox();
	private Button loginButton = new Button("Add");
	private Label infoLabel = new Label();

	public LoginView()
	{

		// Assemble main panel
		mainPanel.add(usernameTextBox);
		mainPanel.add(loginPassword);
		mainPanel.add(loginButton);
		mainPanel.add(infoLabel);

		// Move cursor focus to the username Textbox
		usernameTextBox.setFocus(true);
		
		initWidget(mainPanel);

	}
}

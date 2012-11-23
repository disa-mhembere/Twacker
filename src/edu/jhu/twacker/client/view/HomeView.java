package edu.jhu.twacker.client.view;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.dom.client.KeyCodes;
import com.google.gwt.event.dom.client.KeyUpEvent;
import com.google.gwt.event.dom.client.KeyUpHandler;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.Hyperlink;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.VerticalPanel;

import edu.jhu.twacker.client.service.SearchService;
import edu.jhu.twacker.client.service.SearchServiceAsync;


public class HomeView extends View {

	private final SearchServiceAsync queryService = GWT.create(SearchService.class);
	
	private VerticalPanel mainPanel;
	private Hyperlink signInUp;
//	private Hyperlink logOut;  // TODO : DM
	private Label homeLabel;
	private Label saveStatusLabel;
	
	private TextBox searchBox = new TextBox();
	private Button searchButton = new Button("Search");
	private Label infoLabel = new Label();
	
//	private List<Search> searchTerms; // TODO : DM

	/**
	 * Default ctor
	 */
	public HomeView() {

		mainPanel = new VerticalPanel();
		homeLabel = new Label("HOMEPAGE"); // To be moved to a static constantly loaded page
		signInUp = new Hyperlink("Sign-in", "AUTH"); // To be moved to a static constantly loaded page
		//logOut = new Hyperlink("Log out", "LOGOUT"); // To be moved to a static constantly loaded page
		saveStatusLabel = new Label();
		saveStatusLabel.setVisible(false);
		
		mainPanel.add(signInUp);
		//mainPanel.add(logOut);		
		mainPanel.add(homeLabel);
		
		mainPanel.add(searchBox);
		mainPanel.add(searchButton);
		mainPanel.add(infoLabel);
		mainPanel.add(saveStatusLabel);
		
		initWidget(mainPanel);
		
		searchBox.setFocus(true);
		searchBox.selectAll();
		
		// Create a handler for the sendButton and nameField
		class MyHandler implements ClickHandler, KeyUpHandler {
			/**
			 * Fired when the user clicks on the sendButton.
			 */
			public void onClick(ClickEvent event) {
				saveSearchTerm(searchBox.getText());
				sendQueryToServer();
			}
	
			/**
			 * Fired when the user types in the nameField.
			 */
			public void onKeyUp(KeyUpEvent event) {
				if (event.getNativeKeyCode() == KeyCodes.KEY_ENTER) {
					saveSearchTerm(searchBox.getText());
					sendQueryToServer();
				}
			}
	
			/**
			 * Send the name from the nameField to
			 *  the server and wait for a response.
			 */
			private void sendQueryToServer() {
				infoLabel.setText("Gathering Data!");
				String textToServer = searchBox.getText();
				queryService.queryServer(textToServer, new AsyncCallback<String>() {
							public void onFailure(Throwable caught) {
								infoLabel.setText("There was an error");
							}
	
							public void onSuccess(String result) {
								infoLabel.setText(result);
							}
						});
			}
		}
				
		// Add a handler to send the name to the server
		MyHandler handler = new MyHandler();
		searchButton.addClickHandler(handler);
		searchBox.addKeyUpHandler(handler);
		
	}
	
	private void saveSearchTerm(final String searchTerm)
	{
		saveStatusLabel.setVisible(false);
		queryService.saveSearch(searchTerm, new AsyncCallback<Void>()
		{
			@Override
			public void onSuccess(Void result)
			{
				saveStatusLabel.setVisible(true);
				saveStatusLabel.setText("Search term(s) saved!");
			}
			
			@Override
			public void onFailure(Throwable caught)
			{
				saveStatusLabel.setVisible(true);
				saveStatusLabel.setText("Search term(s) unsucessful! " + caught.getMessage());	
			}
		});
	}
	
	
}


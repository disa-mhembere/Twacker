/** 
 * OOSE Project - Group 4
 * {@link HomeView}.java
 */
package edu.jhu.twacker.client.view;

import java.util.LinkedList;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.dom.client.KeyCodes;
import com.google.gwt.event.dom.client.KeyUpEvent;
import com.google.gwt.event.dom.client.KeyUpHandler;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.HasHorizontalAlignment;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Hyperlink;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.StackPanel;
import com.google.gwt.user.client.ui.TabPanel;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.VerticalPanel;
import edu.jhu.twacker.client.manager.ChartManager;
//import edu.jhu.twacker.client.service.AuthService;
//import edu.jhu.twacker.client.service.AuthServiceAsync;
import edu.jhu.twacker.client.service.SearchService;
import edu.jhu.twacker.client.service.SearchServiceAsync;
import edu.jhu.twacker.shared.FieldVerifier;

/**
 * The Home view
 * 
 * @author Disa Mhembere, Alex Long, Andy Tien
 * 
 */
public class HomeView extends View
{
	// private final String logoURL =
	// "http://www.ugrad.cs.jhu.edu/~group4/final/twacker.png";
	private final SearchServiceAsync queryService = GWT
			.create(SearchService.class);
	// private final AuthServiceAsync authService =
	// GWT.create(AuthService.class);

	// Left side Panel Widgets
	private Hyperlink signInUp = new Hyperlink("Sign-in", "AUTH");
	private Hyperlink signOut = new Hyperlink("Sign-out", "SIGNOUT");
	private Hyperlink personalHistory = new Hyperlink("Personal History",
			"HISTORY");
	private HorizontalPanel signPanel = new HorizontalPanel();
	// private Label signInStatusLabel = new Label();

	// TabPanel and Widgets
	private TabPanel resultsTab = new TabPanel();
	private VerticalPanel histogramPanel = new VerticalPanel();
	private VerticalPanel sentimentPanel = new VerticalPanel();
	private StackPanel expertsPanel = new StackPanel();

	// Right Panel Widgets
	private VerticalPanel searchPanel = new VerticalPanel();
	private HorizontalPanel boxPanel = new HorizontalPanel();
	private TextBox searchBox = new TextBox();
	private TextBox searchBox2 = new TextBox();
	private TextBox searchBox3 = new TextBox();
	private Button searchButton = new Button("Search");
	private Label saveStatusLabel = new Label();
	private Label infoLabel = new Label();

	/**
	 * Constructor initializes all components of the view
	 */
	public HomeView()
	{
		super();

		if (isSignedIn()) {
			signPanel.setSpacing(10);
			signPanel.add(signOut);
			signPanel.add(new Label("  |  "));
			signPanel.add(personalHistory);
		} else {
			signPanel.setSpacing(10);
			signPanel.add(signInUp);
			signPanel.add(new Label("  |  "));
			signPanel.add(personalHistory);
		}

		leftSidePanel.add(signPanel);

		boxPanel.add(searchBox);
		boxPanel.add(searchBox2);
		boxPanel.add(searchBox3);
		searchPanel.add(boxPanel);
		searchPanel.add(searchButton);
		searchPanel.setCellHorizontalAlignment(searchButton,
				HasHorizontalAlignment.ALIGN_RIGHT);
		// searchPanel.setWidth("700px");

		searchBox.setFocus(true);
		searchBox.setText(FieldVerifier.PRIMARY_DEFAULT);
		searchBox2.setText(FieldVerifier.SECONDARY_DEFAULT);
		searchBox3.setText(FieldVerifier.SECONDARY_DEFAULT);
		
		searchBox.addStyleName("preClickTextBox");
		searchBox2.addStyleName("preClickTextBox");
		searchBox3.addStyleName("preClickTextBox");
		
		searchBox.selectAll();

		resultsTab.add(histogramPanel, "Histogram");
		resultsTab.add(sentimentPanel, "Sentiment");
		resultsTab.add(expertsPanel, "Experts");
		resultsTab.setVisible(false);

		rightSidePanel.add(searchPanel);
		rightSidePanel.add(infoLabel);
		rightSidePanel.add(saveStatusLabel);
		rightSidePanel.add(resultsTab);

		/**
		 *	Inner Class that provides Handlers for the searchBox
		 */
		class SearchButtonHandler implements ClickHandler, KeyUpHandler
		{

			private LinkedList<String> searchTerms;

			/**
			 * 	Registers the button click and submits the search terms.
			 * 	@param event	the button click event
			 */
			public void onClick(ClickEvent event)
			{

				if (!FieldVerifier.isValidSearch(searchBox.getText())) {
					infoLabel.setText("Invalid Required Field");
					return;
				} else {
					searchTerms = new LinkedList<String>();
					saveSearchTerm(searchBox.getText());
					searchTerms.add(searchBox.getText());
					if (FieldVerifier.isValidSearch(searchBox2.getText())) {
						searchTerms.add(searchBox2.getText());
						saveSearchTerm(searchBox2.getText());
					}
					if (FieldVerifier.isValidSearch(searchBox3.getText())) {
						searchTerms.add(searchBox3.getText());
						saveSearchTerm(searchBox3.getText());
					}
					sendQueryToServer(searchTerms);
					searchButton.setEnabled(false);
				}
			}

			/**
			 * 	Submits search terms upon pressing enter.
			 * 	@param	event	The key press event
			 */
			public void onKeyUp(KeyUpEvent event)
			{
				if (event.getNativeKeyCode() == KeyCodes.KEY_ENTER) {
					onClick(null);
				}
			}

			/**
			 * 	Submits each of the user's search terms to the server
			 * 	through the queryServer service. Displays graph upon
			 * 	receiving all callbacks.
			 * 	@param searchTerms	The user's entered search terms.
			 */
			private void sendQueryToServer(final LinkedList<String> searchTerms)
			{
				infoLabel.setText("Gathering Data!");

				ChartManager.initDataTable();

				for (String s : searchTerms) {
					queryService.queryServer(s, new AsyncCallback<String>() {
						/**
						 * 	Twacker analysis failed
						 * 	@param	caught the Throwable from the server.
						 */
						public void onFailure(Throwable caught)
						{
							ChartManager.plusCount();	//increment the number of callbacks
							onEither();
						}

						/**
						 * 	Twacker Analysis was successful and graphs
						 * 	will be created.
						 * 	@param result The JSON formated data string from the server.
						 */
						public void onSuccess(String result)
						{
							infoLabel.setText("");
							if (ChartManager.getCount() == 0) {
								sentimentPanel.clear();
								histogramPanel.clear();
								expertsPanel.clear();
							}

							ChartManager.updateTable(result);	//add data to the LineChart data table;
							
							/* create pie chart and experts if result contains data */
							if (!result.contains("\"experts\" : []")) {
								sentimentPanel.add(ChartManager
										.createPieChart(result));
								
								expertsPanel.add(ChartManager
										.createExpertsPanel(result), result
										.substring(result.indexOf(": \"") + 3,
												result.indexOf("\",")));
							}
							onEither();
						}
						
						/**
						 * 	Creates the line chart and shows the results on getting the final
						 * 	callback, whether or not there were successes or failures.
						 */
						public void onEither() {
							if (ChartManager.getCount() == searchTerms.size()) {
								histogramPanel.add(ChartManager
										.createLineChart());
								resultsTab.setVisible(true);
								resultsTab.selectTab(0);
								saveStatusLabel.setText("");
								searchButton.setEnabled(true);
							}
						}
					});
				}
			}
		}
		
		// This handler takes care of the removal of text from textbox
		class TextBoxClickHandler implements ClickHandler
		{
			@Override
			public void onClick(ClickEvent event)
			{
				if (searchBox.getStyleName().contains("preClickTextBox")){
					searchBox.setText("");
					searchBox2.setText("");
					searchBox3.setText("");
					searchBox.removeStyleName("preClickTextBox");
					searchBox2.removeStyleName("preClickTextBox");
					searchBox3.removeStyleName("preClickTextBox");
				}
			}
		}
		
		
		// Add a handler to send the name to the server
		SearchButtonHandler handler = new SearchButtonHandler();
		TextBoxClickHandler clickHandler = new TextBoxClickHandler();
		searchButton.addClickHandler(handler);
		searchBox.addKeyUpHandler(handler);
		searchBox2.addKeyUpHandler(handler);
		searchBox3.addKeyUpHandler(handler);
		searchBox.addClickHandler(clickHandler);
		searchBox2.addClickHandler(clickHandler);
		searchBox3.addClickHandler(clickHandler);
	}

	/**
	 * Save search Terms(s) provided to users profile for
	 * {@link PersonalHistoryView}
	 * 
	 * @param searchTerm
	 */
	private void saveSearchTerm(final String searchTerm)
	{
		saveStatusLabel.setVisible(false);
		queryService.saveSearch(searchTerm, new AsyncCallback<Void>() {
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
				saveStatusLabel.setText("Search term(s) unsucessful! "
						+ caught.getMessage());
			}
		});
	}

	/**
	 * Provide logic for updating view in event that the user's logged in status
	 * has changed.
	 */
	@Override
	public void updateView()
	{
		super.updateView();
		signPanel.clear();
		if (isSignedIn()) {
			signPanel.setSpacing(10);
			signPanel.add(signOut);
			signPanel.add(new Label("  |  "));
			signPanel.add(personalHistory);
		} else {
			signPanel.setSpacing(10);
			signPanel.add(signInUp);
			signPanel.add(new Label("  |  "));
			signPanel.add(personalHistory);
		}
		
		searchBox.setFocus(true);
		searchBox.setText(FieldVerifier.PRIMARY_DEFAULT);
		searchBox2.setText(FieldVerifier.SECONDARY_DEFAULT);
		searchBox3.setText(FieldVerifier.SECONDARY_DEFAULT);
		
		searchBox.addStyleName("preClickTextBox");
		searchBox2.addStyleName("preClickTextBox");
		searchBox3.addStyleName("preClickTextBox");
		
		sentimentPanel.clear();
		histogramPanel.clear();
		expertsPanel.clear();
		resultsTab.setVisible(false);
		saveStatusLabel.setText("");
	}
}

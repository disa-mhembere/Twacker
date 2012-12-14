/** 
 * OOSE Project - Group 4
 * {@link HomeView}.java
 */
package edu.jhu.twacker.client.view;

import java.util.LinkedList;

import com.google.gwt.core.client.GWT;
import com.google.gwt.dom.client.Style.Unit;
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
import com.google.gwt.user.client.ui.Image;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.StackPanel;
import com.google.gwt.user.client.ui.TabPanel;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.VerticalPanel;
import edu.jhu.twacker.client.manager.ChartManager;
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
	private final String logoURL = "http://www.ugrad.cs.jhu.edu/~group4/final/twacker.png";
	private final SearchServiceAsync queryService = GWT
			.create(SearchService.class);
	// private final AuthServiceAsync authService =
	// GWT.create(AuthService.class);

	// private boolean isSignedIn = false;

	// Picture and mainPanels
	private Image logoImage = new Image(logoURL);
	private HorizontalPanel superPanel = new HorizontalPanel();
	private VerticalPanel leftSidePanel = new VerticalPanel();
	private VerticalPanel rightSidePanel = new VerticalPanel();
	
	//Left side Panel Widgets
	private Hyperlink signInUp = new Hyperlink("Sign-in", "AUTH");
	private Hyperlink signOut= new Hyperlink("Sign-out", "SIGNOUT");
	private Hyperlink personalHistory = new Hyperlink("Personal History", "HISTORY");
	private HorizontalPanel signPanel = new HorizontalPanel();
	
	//TabPanel and Widgets
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
		logoImage.getElement().getStyle().setWidth(700, Unit.PX);
		logoImage.getElement().getStyle().setHeight(100, Unit.PX);

		// Initialize title label, add to RSP
		// titleLabel.setText("Twacker");
		// titleLabel.setHeight("100px");
		// titleLabel.setHorizontalAlignment(HasHorizontalAlignment.ALIGN_CENTER);

		// rightSidePanel.add(titleLabel);
		rightSidePanel.add(logoImage);
		rightSidePanel.setWidth("700px");
		rightSidePanel.setBorderWidth(1);

		if (isSignedIn()) {
			signPanel.add(signOut);
			signPanel.add(personalHistory);
		} else {
			signPanel.add(signInUp);
		}
		//
		// signPanel.setSpacing(10);
		// signPanel.add(signInUp);
		// signPanel.add(new Label("  |  "));
		// signPanel.add(signOut);
		//
		leftSidePanel.add(signPanel);
		leftSidePanel.setWidth("200px");

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
		searchBox.selectAll();

		resultsTab.add(histogramPanel, "Histogram");
		resultsTab.add(sentimentPanel, "Sentiment");
		resultsTab.add(expertsPanel, "Experts");
		resultsTab.setVisible(false);

		rightSidePanel.add(searchPanel);
		rightSidePanel.add(infoLabel);
		rightSidePanel.add(saveStatusLabel);
		rightSidePanel.add(resultsTab);

		// Create a handler for the sendButton and nameField
		class SearchButtonHandler implements ClickHandler, KeyUpHandler
		{

			private LinkedList<String> searchTerms;

			/**
			 * Fired when the user clicks on the sendButton.
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
				}
			}

			/**
			 * Fired when the user types in the nameField.
			 */
			public void onKeyUp(KeyUpEvent event)
			{
				if (event.getNativeKeyCode() == KeyCodes.KEY_ENTER) {
					onClick(null);
				}
			}

			/**
			 * Send the name from the nameField to the server and wait for a
			 * response.
			 */
			private void sendQueryToServer(final LinkedList<String> searchTerms)
			{
				infoLabel.setText("Gathering Data!");

				ChartManager.initDataTable();

				for (String s : searchTerms) {
					queryService.queryServer(s, new AsyncCallback<String>() {
						public void onFailure(Throwable caught)
						{
						}

						public void onSuccess(String result)
						{
							infoLabel.setText("");
							if (ChartManager.getCount() == 0) {
								sentimentPanel.clear();
								histogramPanel.clear();
								expertsPanel.clear();
							}

							ChartManager.updateTable(result);
							sentimentPanel.add(ChartManager
									.createPieChart(result));
							expertsPanel.add(ChartManager
									.createExpertsPanel(result), result
									.substring(result.indexOf(": \"") + 3,
											result.indexOf("\",")));
							if (ChartManager.getCount() == searchTerms.size()) {
								histogramPanel.add(ChartManager
										.createLineChart(result));
								resultsTab.setVisible(true);
								resultsTab.selectTab(0);
							}
						}
					});
				}
			}
		}

		// Add a handler to send the name to the server
		SearchButtonHandler handler = new SearchButtonHandler();
		searchButton.addClickHandler(handler);
		searchBox.addKeyUpHandler(handler);
		searchBox2.addKeyUpHandler(handler);
		searchBox3.addKeyUpHandler(handler);

		superPanel.add(leftSidePanel);
		superPanel.add(rightSidePanel);
		superPanel.setBorderWidth(4);
		// superPanel.setWidth("900px");

		initWidget(superPanel);
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
	protected void updateView()
	{
		super.updateView();
		signPanel.clear();

		if (isSignedIn()) {
			signPanel.add(signOut);
			signPanel.add(new Label("  |  "));
			signPanel.add(personalHistory);
		} else {
			signPanel.add(signInUp);
		}
	}
}

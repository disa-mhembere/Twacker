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
import com.google.gwt.user.client.ui.TabPanel;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.visualization.client.AbstractDataTable;
import com.google.gwt.visualization.client.AbstractDataTable.ColumnType;
import com.google.gwt.visualization.client.DataTable;
import com.google.gwt.visualization.client.visualizations.corechart.AxisOptions;
import com.google.gwt.visualization.client.visualizations.corechart.LineChart;
import com.google.gwt.visualization.client.visualizations.corechart.Options;
import com.google.gwt.visualization.client.visualizations.corechart.PieChart;

import edu.jhu.twacker.client.service.SearchService;
import edu.jhu.twacker.client.service.SearchServiceAsync;
import edu.jhu.twacker.shared.FieldVerifier;

/**
 * The Home view
 * 
 * @author Disa Mhembere, Alex Long, Andy Tien
 * 
 */
public class HomeView extends View {

	private final SearchServiceAsync queryService = GWT
			.create(SearchService.class);
	// private final AuthServiceAsync authService =
	// GWT.create(AuthService.class);

	private VerticalPanel mainPanel = new VerticalPanel();
	private VerticalPanel histogramPanel = new VerticalPanel();
	private VerticalPanel sentimentPanel = new VerticalPanel();
	private TabPanel resultsTab = new TabPanel();
	private Hyperlink signInUp;
	private Hyperlink signOut;
	private Label saveStatusLabel = new Label();
	private Label titleLabel = new Label();

	private VerticalPanel searchPanel = new VerticalPanel();
	private HorizontalPanel boxPanel = new HorizontalPanel();
	private TextBox searchBox = new TextBox();
	private TextBox searchBox2 = new TextBox();
	private TextBox searchBox3 = new TextBox();
	private Button searchButton = new Button("Search");

	private Label infoLabel = new Label();

	private static DataTable table;
	private static int count = 0;

	/**
	 * Default constructor assembles the visual portions of the view
	 */
	public HomeView() {
		signInUp = new Hyperlink("Sign-in/Register", "AUTH"); 
		signOut = new Hyperlink("Sign-out", "SIGNOUT"); 
		
		HorizontalPanel signPanel = new HorizontalPanel();
		signPanel.setSpacing(10);
		signPanel.add(signInUp);
		signPanel.add(new Label("  |  "));
		signPanel.add(signOut);	
		
		mainPanel.add(signPanel);
		
		boxPanel.add(searchBox);
		boxPanel.add(searchBox2);
		boxPanel.add(searchBox3);
		searchPanel.add(boxPanel);
		searchPanel.add(searchButton);
	    searchPanel.setCellHorizontalAlignment(searchButton, HasHorizontalAlignment.ALIGN_RIGHT);
	    
	    searchBox.setFocus(true);
		searchBox.setText(FieldVerifier.PRIMARY_DEFAULT);
		searchBox2.setText(FieldVerifier.SECONDARY_DEFAULT);
		searchBox3.setText(FieldVerifier.SECONDARY_DEFAULT);
		searchBox.selectAll();
		
		mainPanel.add(searchPanel);
		mainPanel.add(infoLabel);
		mainPanel.add(saveStatusLabel);
		
		resultsTab.add(histogramPanel, "Histogram");
		resultsTab.add(sentimentPanel, "Sentiment");
		resultsTab.setVisible(false);
		mainPanel.add(resultsTab);

		initWidget(mainPanel);		

		// Create a handler for the sendButton and nameField
		class SearchButtonHandler implements ClickHandler, KeyUpHandler {
			
			private LinkedList<String> searchTerms;
			/**
			 * Fired when the user clicks on the sendButton.
			 */
			public void onClick(ClickEvent event) {
				if (!FieldVerifier.isValidSearch(searchBox.getText())) {
					infoLabel.setText("Invalid Required Field");
					return;
				}
				else {
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
			public void onKeyUp(KeyUpEvent event) {
				if (event.getNativeKeyCode() == KeyCodes.KEY_ENTER) {
					if (!FieldVerifier.isValidSearch(searchBox.getText())) {
						infoLabel.setText("Invalid Required Field");
						return;
					}
					else {
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
			}

			/**
			 * Send the name from the nameField to
			 *  the server and wait for a response.
			 */
			private void sendQueryToServer(final LinkedList<String> searchTerms) {
				infoLabel.setText("Gathering Data!");				
				
				table = DataTable.create();
				table.addColumn(ColumnType.NUMBER, "Day");
				table.addRows(10);
				count = 0;
				
				for (String s : searchTerms) {
					queryService.queryServer(s, new AsyncCallback<String>() {
						public void onFailure(Throwable caught) {
						}

						public void onSuccess(String result) {
							infoLabel.setText("");
							if (count == 0 ) {
								sentimentPanel.clear();
								histogramPanel.clear();
							}
							
							updateTable(result);
							sentimentPanel.add(new PieChart(createPieChart(result), createOptions(result)));
							if (count == searchTerms.size()) { 
								histogramPanel.add(new LineChart(table, createOptions(result)));
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
	}

	public static void updateTable(String s) {
		String histogram = s.substring(s.indexOf("histogram"),
				s.indexOf("sentiment"));
		String data = histogram.substring(histogram.indexOf("data"),
				histogram.indexOf("]"));
		String[] days = data.split(",");
		int[] counts = new int[days.length];
		for (int i = 0; i < days.length; i++) {
			counts[i] = Integer.parseInt(days[i].replaceAll("\\D", ""));
		}
		table.addColumn(ColumnType.NUMBER,
				s.substring(s.indexOf(": \""), s.indexOf("\",")));

		for (int i = 0; i < counts.length; i++) {
			if (count == 0)
				table.setValue(i, 0, i);
			table.setValue(i, count + 1, counts[i]);
		}
		count++;
	}

	/**
	 * Save search Terms(s) provided to users profile for
	 * {@link PersonalHistoryView}
	 * 
	 * @param searchTerm
	 */
	private void saveSearchTerm(final String searchTerm) {
		saveStatusLabel.setVisible(false);
		queryService.saveSearch(searchTerm, new AsyncCallback<Void>() {
			@Override
			public void onSuccess(Void result) {
				saveStatusLabel.setVisible(true);
				saveStatusLabel.setText("Search term(s) saved!");
			}

			@Override
			public void onFailure(Throwable caught) {
				saveStatusLabel.setVisible(true);
				saveStatusLabel.setText("Search term(s) unsucessful! "
						+ caught.getMessage());
			}
		});
	}

	/**
	 * TODO : AL
	 * 
	 * @param result
	 * @return
	 */
	private Options createOptions(String result) {
		Options options = Options.create();
		options.setWidth(400);
		options.setHeight(300);
		options.setTitle("Twacker Results for "
				+ result.substring(result.indexOf(": \""),
						result.indexOf("\",")));
		options.setPointSize(5);
		AxisOptions opt = AxisOptions.create();
		opt.set("viewWindowMode", "pretty");
		options.setHAxisOptions(opt);
		return options;
	}

	/**
	 * TODO : AL
	 * 
	 * @param result
	 * @return
	 */
	private AbstractDataTable createPieChart(String result) {
		String pie = result.substring(result.indexOf("sentiment"),
				result.indexOf("experts"));
		String data = pie.substring(pie.indexOf("positive"), pie.indexOf("}"));
		String[] days = data.split(",");
		int[] counts = new int[days.length];
		for (int i = 0; i < days.length; i++) {
			counts[i] = Integer.parseInt(days[i].replaceAll("\\D", ""));
		}

		DataTable data2 = DataTable.create();
		data2.addColumn(ColumnType.STRING, "Sentiment");
		data2.addColumn(ColumnType.NUMBER, "Number");
		data2.addRows(4);
		data2.setValue(0, 0, "Positive");
		data2.setValue(1, 0, "Negative");
		data2.setValue(2, 0, "Neutral");
		data2.setValue(3, 0, "ERROR");

		for (int i = 0; i < counts.length; i++) {
			if (i != 4)
				data2.setValue(i, 1, counts[i]);
			else
				data2.setValue(3, 1, counts[4]);
		}
		return data2;
	}
}

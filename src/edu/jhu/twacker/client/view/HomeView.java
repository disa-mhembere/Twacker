/** 
 * OOSE Project - Group 4
 * {@link HomeView}.java
 */
package edu.jhu.twacker.client.view;

import java.util.LinkedList;

import com.google.gwt.core.client.GWT;
import com.google.gwt.core.client.JsArray;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.dom.client.KeyCodes;
import com.google.gwt.event.dom.client.KeyUpEvent;
import com.google.gwt.event.dom.client.KeyUpHandler;
import com.google.gwt.user.client.Window;
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
import com.google.gwt.visualization.client.Selection;
import com.google.gwt.visualization.client.events.SelectHandler;
import com.google.gwt.visualization.client.visualizations.corechart.AxisOptions;
import com.google.gwt.visualization.client.visualizations.corechart.LineChart;
import com.google.gwt.visualization.client.visualizations.corechart.Options;
import com.google.gwt.visualization.client.visualizations.corechart.PieChart;

import edu.jhu.twacker.client.service.SearchService;
import edu.jhu.twacker.client.service.SearchServiceAsync;
import edu.jhu.twacker.shared.FieldVerifier;

/**
 * The Home view
 * @author Disa Mhembere, Alex Long, Andy Tien
 *
 */
public class HomeView extends View {

	private final SearchServiceAsync queryService = GWT.create(SearchService.class);

	private VerticalPanel mainPanel;
	private VerticalPanel histogramPanel = new VerticalPanel();
	private VerticalPanel sentimentPanel = new VerticalPanel();
	private TabPanel resultsTab = new TabPanel();
	private Hyperlink signInUp;
	//	private Hyperlink logOut;  // TODO : DM
	//	private Label homeLabel;
	private Label saveStatusLabel;

	private VerticalPanel searchPanel = new VerticalPanel();
	private HorizontalPanel boxPanel = new HorizontalPanel();
	private TextBox searchBox = new TextBox();
	private TextBox searchBox2 = new TextBox();
	private TextBox searchBox3 = new TextBox();		
	private Button searchButton = new Button("Search");
	
	private Label infoLabel = new Label();
	private Label errLabel = new Label();
	
	private LineChart line;
	private PieChart pie;

	//	private List<Search> searchTerms; // TODO : DM

	/**
	 * Default constructor assembles the visual portions of the view
	 */
	public HomeView() {

		mainPanel = new VerticalPanel();
		//		homeLabel = new Label("HOMEPAGE"); // To be moved to a static constantly loaded page
		signInUp = new Hyperlink("Sign-in", "AUTH"); // To be moved to a static constantly loaded page
		//logOut = new Hyperlink("Log out", "LOGOUT"); // To be moved to a static constantly loaded page
		saveStatusLabel = new Label();
		saveStatusLabel.setVisible(false);

		mainPanel.add(signInUp);
		//mainPanel.add(logOut);		
		//		mainPanel.add(homeLabel);
		
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
		mainPanel.add(errLabel);
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
			private void sendQueryToServer(LinkedList<String> terms) {
				infoLabel.setText("Gathering Data!");
				errLabel.setText("");
				histogramPanel.clear();
				sentimentPanel.clear();
				for (String s : terms) {
					queryService.queryServer(s, new AsyncCallback<String>() {
						public void onFailure(Throwable caught) {
							errLabel.setText(caught.toString());
						}

						public void onSuccess(String result) {
							if (pie == null && line == null) {
								line = new LineChart(createLineGraphs(result), createOptions(result));
								line.addSelectHandler(createSelectHandler(line));

								pie = new PieChart(createPieChart(result), createOptions(result));
								pie.addSelectHandler(createSelectHandler(pie));

								histogramPanel.add(line);
								sentimentPanel.add(pie);
								resultsTab.setVisible(true);
								resultsTab.selectTab(0);							
							}
							else {
//								line.draw(createLineGraphs(result), createOptions());
//								pie.draw(createPieChart(result), createOptions());
								line = new LineChart(createLineGraphs(result), createOptions(result));
								line.addSelectHandler(createSelectHandler(line));

								pie = new PieChart(createPieChart(result), createOptions(result));
								pie.addSelectHandler(createSelectHandler(pie));

								histogramPanel.add(line);
								sentimentPanel.add(pie);
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

	/**
	 * Save search Terms(s) provided to users profile for {@link PersonalHistoryView}
	 * @param searchTerm
	 */
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

	private Options createOptions(String result) {
		Options options = Options.create();
		options.setWidth(400);
		options.setHeight(240);
		options.setTitle("Twacker Results for " + result.substring(result.indexOf(": \""), result.indexOf("\",")));
		options.setPointSize(5);
		AxisOptions opt = AxisOptions.create();
		opt.set("viewWindowMode", "pretty");
		options.setHAxisOptions(opt);
		return options;
	}

	private SelectHandler createSelectHandler(final com.google.gwt.visualization.client.visualizations.corechart.CoreChart chart) {
		return new SelectHandler() {
			@Override
			public void onSelect(SelectEvent event) {
				String message = "";

				// May be multiple selections.
				JsArray<Selection> selections = chart.getSelections();

				for (int i = 0; i < selections.length(); i++) {
					// add a new line for each selection
					message += i == 0 ? "" : "\n";

					Selection selection = selections.get(i);

					if (selection.isCell()) {
						// isCell() returns true if a cell has been selected.

						// getRow() returns the row number of the selected cell.
						int row = selection.getRow();
						// getColumn() returns the column number of the selected cell.
						int column = selection.getColumn();
						message += "cell " + row + ":" + column + " selected";
					} else if (selection.isRow()) {
						// isRow() returns true if an entire row has been selected.

						// getRow() returns the row number of the selected row.
						int row = selection.getRow();
						message += "row " + row + " selected";
					} else {
						// unreachable
						message += "Pie chart selections should be either row selections or cell selections.";
						message += "  Other visualizations support column selections as well.";
					}
				}

				Window.alert(message);
			}
		};
	}

	private AbstractDataTable createLineGraphs(String result) {
		String histogram = result.substring(result.indexOf("histogram"), result.indexOf("sentiment"));
		String data = histogram.substring(histogram.indexOf("data"), histogram.indexOf("]"));
		String[] days = data.split(",");
		int[] counts = new int[days.length];
		for (int i = 0; i < days.length; i++) {
			counts[i] = Integer.parseInt(days[i].replaceAll("\\D", ""));
		}

		DataTable data2 = DataTable.create();
		data2.addColumn(ColumnType.NUMBER, "Day");
		data2.addColumn(ColumnType.NUMBER, "Search");
		data2.addRows(days.length);
		for (int i = 0; i < counts.length; i++) {
			data2.setValue(i, 0, i);
			data2.setValue(i, 1, counts[i]);
		}
		return data2;
	}
	
	private AbstractDataTable createPieChart(String result) {
		String pie = result.substring(result.indexOf("sentiment"), result.indexOf("experts"));
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


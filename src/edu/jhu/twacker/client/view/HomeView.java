package edu.jhu.twacker.client.view;

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
import com.google.gwt.user.client.ui.Hyperlink;
import com.google.gwt.user.client.ui.Label;
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


public class HomeView extends View {

	private final SearchServiceAsync queryService = GWT.create(SearchService.class);

	private VerticalPanel mainPanel;
	private Hyperlink signInUp;
	//	private Hyperlink logOut;  // TODO : DM
	//	private Label homeLabel;
	private Label saveStatusLabel;

	private TextBox searchBox = new TextBox();
	private Button searchButton = new Button("Search");
	private Label infoLabel = new Label();
	
	private LineChart line;
	private PieChart pie;

	//	private List<Search> searchTerms; // TODO : DM

	/**
	 * Default ctor
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
						infoLabel.setText("");						

						if (pie != null && line != null) {
							mainPanel.remove(line);
							mainPanel.remove(pie);
						}
						// Create a pie chart visualization.
						line = new LineChart(createLineGraphs(result), createOptions());
						line.addSelectHandler(createSelectHandler(line));
						
						// Create a pie chart visualization.
						pie = new PieChart(createPieChart(result), createOptions());
						pie.addSelectHandler(createSelectHandler(pie));
						mainPanel.add(line);
						mainPanel.add(pie);
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

	private Options createOptions() {
		Options options = Options.create();
		options.setWidth(400);
		options.setHeight(240);
		options.setTitle("Twacker Results");
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


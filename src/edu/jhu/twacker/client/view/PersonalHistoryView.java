/**
 * OOSE Project - Group 4
 * {@link PersonalHistoryView}.java 
 */
package edu.jhu.twacker.client.view;

import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.SortedSet;
import java.util.TreeSet;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.logical.shared.ValueChangeEvent;
import com.google.gwt.event.logical.shared.ValueChangeHandler;
import com.google.gwt.i18n.shared.DateTimeFormat;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.FlexTable;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Label;
//import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.datepicker.client.DatePicker;

import edu.jhu.twacker.client.service.SearchService;
import edu.jhu.twacker.client.service.SearchServiceAsync;

//import com.allen_sauer.gwt.log.client.Log;

/**
 * This view's purpose is to allow users to query their past search history
 * 
 * @author Disa Mhembere
 */
public class PersonalHistoryView extends View
{
	private final SearchServiceAsync historyService = GWT
			.create(SearchService.class);

	private DatePicker calendar;
	private Button submitButton;
	private Button searchAllButton;
	private HorizontalPanel buttonPanel;
	private FlexTable resultsTable;
	private Map<String, Integer> datesOnTable = new LinkedHashMap<String, Integer>();

	// data objects
	private Map<Date, String> allSearchesMap = new LinkedHashMap<Date, String>();
	private Map<Date, String> singleDaySearchesMap = new LinkedHashMap<Date, String>();
	private Date singleSearchDate = new Date(System.currentTimeMillis());
	private Label searchLabel = new Label("");

	private DateTimeFormat dateFormat = DateTimeFormat
			.getFormat("EEEE, MMMM d, yyyy");

	/**
	 * Default constructor initializes the visual/GUI aspects of the view and
	 * assembles the widget and handles clicks
	 */
	public PersonalHistoryView()
	{
		super();
		calendar = new DatePicker();
		calendar.setWidth("200px");
		buttonPanel = new HorizontalPanel();

		resultsTable = new FlexTable();
		resultsTable.setText(0, 0, "Date");
		resultsTable.setText(0, 1, "Searches");
		resultsTable.addStyleName("tableHeader");
		resultsTable.getCellFormatter().addStyleName(0,0, "tableColumn");
		resultsTable.getCellFormatter().addStyleName(0,1, "tableColumn");
		

		submitButton = new Button("Submit Search");
		searchAllButton = new Button("See all history");

		buttonPanel.add(submitButton);
		buttonPanel.add(searchAllButton);

		leftSidePanel.add(calendar);
		leftSidePanel.add(buttonPanel);
		rightSidePanel.add(searchLabel);
		rightSidePanel.add(resultsTable);

		/*
		 * Handle calendar events
		 */
		calendar.addValueChangeHandler(new ValueChangeHandler<Date>() {

			@Override
			/**
			 * Handles the event of a calendar object click
			 * @param event the event of a click on a specific dateFormat
			 */
			public void onValueChange(ValueChangeEvent<Date> event)
			{
				setSingleSearchDate(event.getValue());
			}
		});

		/*
		 * Handles the submission button clicks for single search date
		 */
		submitButton.addClickHandler(new ClickHandler() {

			@Override
			/**
			 * Invokes search service to return the searches on a particular date
			 * @param event the event of a click on the submit button
			 */
			public void onClick(ClickEvent event)
			{
				getDayHistory();
			}
		});

		/*
		 * Handle searchAllButton submits
		 */
		searchAllButton.addClickHandler(new ClickHandler() {
			@Override
			/**
			 * Launch searchService & query datastore for all searches for a user
			 * @param event The event of a click to the searchAllButton  
			 */
			public void onClick(ClickEvent event)
			{
				getAllHistory();
			}
		});
	}

	/**
	 * Get history for a specific day
	 */
	private Map<Date, String> getDayHistory()
	{

		historyService.getDaySearches(getSingleSearchDate(),
				new AsyncCallback<Map<Date, List<String>>>() {

					@Override
					public void onSuccess(Map<Date, List<String>> result)
					{
						for (Date d: result.keySet()){
							String dateString = dateFormat.format(d);
							if (!datesOnTable.containsKey(dateString)){
								String s = "";
								ArrayList<String> searchArrayLists = new ArrayList<String>(result.get(d));
								for (String st : searchArrayLists){
									s += st + ", ";
								}
								
								int newRowNumber = resultsTable.getRowCount();
								resultsTable.setText(newRowNumber, 0, dateString);
								resultsTable.setText(newRowNumber, 1, s);
								datesOnTable.put(dateString, newRowNumber);	
							} else { 
								int rowNumber = datesOnTable.get(dateString);
								String s = resultsTable.getText(rowNumber, 1);
								ArrayList<String> searchArrayLists = new ArrayList<String>(result.get(d));
								for (String st : searchArrayLists){
									s += st + ", ";
								}
								resultsTable.setText(rowNumber, 1, s);
							}
						}
					}

					@Override
					public void onFailure(Throwable caught)
					{
						Window.Location.reload(); // Something wen't wrong.
													// Reload and re-try
						// Log.debug("DM getAllSearches onFailure: "
						// + caught.getMessage()); // Debug
					}
				});
		return singleDaySearchesMap;
	}

	/**
	 * Get users entire history
	 */
	private Map<Date, String> getAllHistory()
	{

		historyService
				.getAllSearches(new AsyncCallback<Map<Date, List<String>>>() {

					@Override
					public void onSuccess(Map<Date, List<String>> result)
					{
						SortedSet<Date> sortedDates = new TreeSet<Date>(result.keySet());
						for (Date d: sortedDates){
							String dateString = dateFormat.format(d);
							
							if (!datesOnTable.containsKey(dateString)){
								String s = "";
								ArrayList<String> searchArrayLists = new ArrayList<String>(result.get(d));
								for (String st : searchArrayLists){
									s += st + ", ";
								}
								
								int newRowNumber = resultsTable.getRowCount();
								resultsTable.setText(newRowNumber, 0, dateString);
								resultsTable.setText(newRowNumber, 1, s);
								datesOnTable.put(dateString, newRowNumber);	
							} else { 
								int rowNumber = datesOnTable.get(dateString);
								String s = resultsTable.getText(rowNumber, 1);
								ArrayList<String> searchArrayLists = new ArrayList<String>(result.get(d));
								for (String st : searchArrayLists){
									s += st + ", ";
								}
								resultsTable.setText(rowNumber, 1, s);
							}
						}
					}

					@Override
					public void onFailure(Throwable caught)
					{
						// Log.debug("DM getAllSearches onFailure: "
						// + caught.getMessage()); // Debug
					}
				});

		return allSearchesMap;
	}

	/**
	 * Get single days history for a user
	 * 
	 * @return the singleSearchDate
	 */
	public Date getSingleSearchDate()
	{
		return singleSearchDate;
	}

	/**
	 * @param singleSearchDate
	 *            the singleSearchDate to set
	 */
	public void setSingleSearchDate(Date singleSearchDate)
	{
		this.singleSearchDate = singleSearchDate;
	}
}

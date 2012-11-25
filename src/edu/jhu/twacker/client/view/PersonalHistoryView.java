/**
 * OOSE Project - Group 4
 * PersonalHistoryView.java 
 */
package edu.jhu.twacker.client.view;

import java.sql.Time;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.HashMap;
import java.util.Map;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.logical.shared.ValueChangeEvent;
import com.google.gwt.event.logical.shared.ValueChangeHandler;
import com.google.gwt.i18n.shared.DateTimeFormat;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.datepicker.client.DatePicker;

import edu.jhu.twacker.client.service.SearchService;
import edu.jhu.twacker.client.service.SearchServiceAsync;

import com.allen_sauer.gwt.log.client.Log;
	
/**
 * This views purpose is to allow users to query their past
 * search history
 * @author Disa Mhembere
 */
public class PersonalHistoryView extends View
{

	private final SearchServiceAsync historyService = GWT.create(SearchService.class);
	private DatePicker calendar;
	private Button submitButton;
	private Button searchAllButton;
	private HorizontalPanel buttonPanel;
	private VerticalPanel histPanel;
	

	//data objects
	private Map<Date, String> allSearchesMap = new HashMap<Date, String>();
	private Map<Date, String> singleDaySearchesMap = new HashMap<Date, String>();
	private Date singleSearchDate;
	private Label searchLabel = new Label("");
	
	private DateTimeFormat dateFormat = DateTimeFormat.getFormat("EEEE, MMMM d, yyyy");
	
	
	/**
	 * Default constructor 
	 */
	public PersonalHistoryView()
	{
		histPanel = new VerticalPanel();
		calendar = new DatePicker();
		buttonPanel = new HorizontalPanel();
		
		submitButton = new Button("Submit Search");
		searchAllButton = new Button("See all history");
		
		buttonPanel.add(submitButton); buttonPanel.add(searchAllButton);
		
		histPanel.add(calendar);
		histPanel.add(buttonPanel);
		
		histPanel.add(searchLabel);
		
		initWidget(histPanel);
		
		/*
		 * Handle calendar events
		 */
		calendar.addValueChangeHandler(new ValueChangeHandler<Date>()
		{
			
			@Override
			/**
			 * Handles the event of a calendar object click
			 * @param event the event of a click on a specific dateFormat
			 */
			public void onValueChange(ValueChangeEvent<Date> event)
			{
			   setSingleSearchDate(event.getValue());
			   
				Log.debug("Time is:"
						+ new Time(getSingleSearchDate().getTime()).toString()
						+ " Date is: " + dateFormat.format(getSingleSearchDate()));
			}
		});
		
		/*
		 * Handles the submission button clicks for single search date
		 */
		submitButton.addClickHandler(new ClickHandler()
		{
			
			@Override
			/**
			 * Invokes search service to return the searches on a particular date
			 * @param event the event of a click on the submit button
			 */
			public void onClick(ClickEvent event)
			{
				
				Collection<String> daySearchHist = new ArrayList<String>();
				daySearchHist = getDayHistory().values();
				
				String s = "";
				for (String st : daySearchHist)
				{
					s += st + ", ";  
				}
				searchLabel.setText(dateFormat.format(getSingleSearchDate()) +
						" Searches: " + s);
				// Log.debug(historyService + "s value: " + s);
				
				
			}
		});
		
		/*
		 * Handle searchAllButton submits
		 */
		searchAllButton.addClickHandler(new ClickHandler()
		{
			@Override
			/**
			 * Launch searchService & query datastore for all searches for a user
			 * @param event The event of a click to the searchAllButton  
			 */
			public void onClick(ClickEvent event)
			{
				Collection<String> searchHist = new ArrayList<String>();
				searchHist = getAllHistory().values();
				
				String s = "";
				for (String st : searchHist)
				{
					s += st + ", ";  
				}
				searchLabel.setText("All Searches:" + s);
				//Log.debug(historyService + "s value: " + s);
			}	
		});
		
		
	}
	
	
	/**
	 * Get history for a specific day
	 */
	private Map<Date, String> getDayHistory()
	{
		historyService.getDaySearches(getSingleSearchDate(), new AsyncCallback<Map<Date,String>>()
		{
			
			@Override
			public void onSuccess(Map<Date, String> result)
			{
				singleDaySearchesMap = result;
			}
			
			@Override
			public void onFailure(Throwable caught)
			{
				Log.debug("DM getDaySearches onFailure: " + caught.getMessage());
			}
		});
		return singleDaySearchesMap;
	}
	
	/**
	 * Get users entire history
	 */
	private Map<Date, String> getAllHistory()
	{
		historyService.getAllSearches(new AsyncCallback<Map<Date,String>>()
				{
					
					@Override
					public void onSuccess(Map<Date,String> result)
					{
						allSearchesMap = result;
					}
					
					@Override
					public void onFailure(Throwable caught)
					{
						Log.debug("DM getAllSearches onFailure: " + caught.getMessage()); // Debug mode
						
					}
				});
		
		return allSearchesMap;
	}
	

	/**
	 * @return the singleSearchDate
	 */
	public Date getSingleSearchDate()
	{
		return singleSearchDate;
	}


	/**
	 * @param singleSearchDate the singleSearchDate to set
	 */
	public void setSingleSearchDate(Date singleSearchDate)
	{
		this.singleSearchDate = singleSearchDate;
	}


	
}

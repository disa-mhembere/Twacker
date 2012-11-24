/**
 * OOSE Project - Group 4
 * PersonalHistoryView.java 
 */
package edu.jhu.twacker.client.view;

import java.util.Date;
import java.util.List;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.logical.shared.ValueChangeEvent;
import com.google.gwt.event.logical.shared.ValueChangeHandler;
import com.google.gwt.i18n.shared.DateTimeFormat;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.HorizontalPanel;
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
		
		initWidget(histPanel);
		
		calendar.addValueChangeHandler(new ValueChangeHandler<Date>()
		{
			
			@Override
			public void onValueChange(ValueChangeEvent<Date> event)
			{
			   Date date = event.getValue();
			   
			   Log.debug(date.toString());
			   
	         //String dateString = DateTimeFormat.getMediumDateFormat().format(date);
	     		
			}
		});
		
		
		submitButton.addClickHandler(new ClickHandler()
		{
			
			@Override
			public void onClick(ClickEvent event)
			{
				// TODO Auto-generated method stub
				
			}
		});
		
	}
	
	/**
	 * Get history for a specific day
	 */
	private void getDayHistory()
	{
		historyService.getDaySearches(new AsyncCallback<List<String>>()
		{
			
			@Override
			public void onSuccess(List<String> result)
			{
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void onFailure(Throwable caught)
			{
				// TODO Auto-generated method stub
				
			}
		}); 
	}
	
	/**
	 * Get users entire history
	 */
	private void getAllHistory()
	{
		{
			historyService.getAllSearches(new AsyncCallback<List<String>>()
			{
				
				@Override
				public void onSuccess(List<String> result)
				{
					// TODO Auto-generated method stub
					
				}
				
				@Override
				public void onFailure(Throwable caught)
				{
					// TODO Auto-generated method stub
					
				}
			}); 
		}
		
	}
	
}

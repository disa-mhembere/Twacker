package edu.jhu.twacker.client.view;

//import java.util.List;
//import com.google.gwt.core.client.GWT;
import com.google.gwt.event.logical.shared.ValueChangeEvent;
import com.google.gwt.event.logical.shared.ValueChangeHandler;
//import com.google.gwt.event.shared.HandlerRegistration;
//import com.google.gwt.i18n.shared.DateTimeFormat;
//import com.google.gwt.user.client.History;
//import com.google.gwt.user.client.rpc.AsyncCallback;
//import com.google.gwt.user.client.ui.Hyperlink;
//import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.VerticalPanel;

import edu.jhu.twacker.model.TwackerModel;

//import edu.jhu.twacker.client.manager.ViewManager;


public class HomeView extends View implements ValueChangeHandler<String> {

	private VerticalPanel masterPanel;
//	private List<Search> searchTerms;
//	
//	private final static DateTimeFormat formatterNoTime = DateTimeFormat.getFormat("EEEE, MMMM d, yyyy");
//	private final static DateTimeFormat formatterTime = DateTimeFormat.getFormat("EEEE, MMMM d, yyyy, h:mm aa");
//	private HandlerRegistration handlerRegistration;

	/**
	 * Defautl ctor
	 */
	public HomeView() {

		masterPanel = new VerticalPanel();
		initWidget(masterPanel);
		
		// Testing - not unsucccessfull but not quite successful
		TwackerModel tm = new TwackerModel("Obama"); 
		tm.run();
		System.out.println(tm.getResult());
		
//		handlerRegistration = History.addValueChangeHandler(this);
	}

	/**
	 * Processes the Announcement hyperlink list clicked
	 * @param event The event of the hyperlink click
	 */
	@Override
	public void onValueChange(ValueChangeEvent<String> event) {
//		int index;
//		try {
//			index = Integer.parseInt(event.getValue());
//		} catch(NumberFormatException e) {
//			return;
//		}
//		handlerRegistration.removeHandler();
//		ViewManager.getInstance(); // TODO: DM  .generateAnnouncementPage(announcements.get(index));
	}

}


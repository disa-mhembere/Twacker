package edu.jhu.twacker.client.view;

import com.google.gwt.user.client.ui.Hyperlink;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.VerticalPanel;

import edu.jhu.twacker.model.TwackerModel;


public class HomeView extends View {

	private VerticalPanel mainPanel;
	private Hyperlink signInUp;
//	private Hyperlink logOut;  // TODO : DM
	private Label homeLabel;
	
//	private List<Search> searchTerms; // TODO : DM

	/**
	 * Default ctor
	 */
	public HomeView() {

		mainPanel = new VerticalPanel();
		homeLabel = new Label("HOMEPAGE"); // To be moved to a static constantly loaded page
		signInUp = new Hyperlink("Sign-in / Sign-up", "AUTH"); // To be moved to a static constantly loaded page
		//logOut = new Hyperlink("Log out", "LOGOUT"); // To be moved to a static constantly loaded page
		
		mainPanel.add(signInUp);
		//mainPanel.add(logOut);
		
		mainPanel.add(homeLabel);
		initWidget(mainPanel);
		
		// Testing - not unsuccessful but not quite successful
//		TwackerModel tm = new TwackerModel("Obama"); 
//		tm.run();
//		System.out.println(tm.getResult());
		
	}
}


/**
 * OOSE Project - Group 4
 * {@link View}.java 
 */
package edu.jhu.twacker.client.view;

import com.google.gwt.core.client.GWT;
import com.google.gwt.dom.client.Style.Unit;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.History;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Image;
import com.google.gwt.user.client.ui.VerticalPanel;

import edu.jhu.twacker.client.service.AuthService;
import edu.jhu.twacker.client.service.AuthServiceAsync;

/**
 * The base class from which all views inherit
 * 
 * @author Disa Mhembere, Andy Tien
 * 
 */
public class View extends Composite
{
	private boolean isSignedIn = false;
	private final AuthServiceAsync authService = GWT.create(AuthService.class);

	private final String logoURL = "http://www.ugrad.cs.jhu.edu/~group4/final/twacker.png";

	// Picture and mainPanel, these stay consistent throughout
	private Image logoImage = new Image(logoURL);
	protected HorizontalPanel superPanel = new HorizontalPanel();
	protected VerticalPanel leftSidePanel = new VerticalPanel();
	protected VerticalPanel rightSidePanel = new VerticalPanel();

	protected View()
	{
		logoImage.getElement().getStyle().setWidth(700, Unit.PX);
		logoImage.getElement().getStyle().setHeight(100, Unit.PX);
		logoImage.setStyleName("imageClick");
		
		logoImage.addClickHandler(new ClickHandler() {
			
			@Override
			public void onClick(ClickEvent event)
			{
				History.newItem("HOME");
			}
		});

		// Initialize panel sizes
		rightSidePanel.setWidth("700px");
		rightSidePanel.setBorderWidth(1);
		leftSidePanel.setWidth("200px");

		rightSidePanel.add(logoImage);

		superPanel.setBorderWidth(4);
		superPanel.add(leftSidePanel);
		superPanel.add(rightSidePanel);

		initWidget(superPanel);
	}

	/**
	 * Initiates an asynchronous call to check whether the user is signed in.
	 * updateView() will be called so that any necessary changes are made if the
	 * user is signed in
	 */
	public void updateStatus()
	{
		authService.isSignedIn(new AsyncCallback<Boolean>() {
			@Override
			public void onSuccess(Boolean result)
			{
				isSignedIn = result;
//				if (isSignedIn != result) {
//					isSignedIn = result;
//					updateView();
//				}
			}

			@Override
			public void onFailure(Throwable caught)
			{
				isSignedIn = false;
			}
		});
	}

	/**
	 * Returns the view's perception of the user's current signed in state
	 * 
	 * @return
	 */
	public boolean isSignedIn()
	{
		return isSignedIn;
	}

	/**
	 * This method is called whenever updateStatus() results in a state change.
	 * This allows us to, if necessary, update the panels depending on the
	 * user's logged in status
	 */
	public void updateView()
	{

	}

}

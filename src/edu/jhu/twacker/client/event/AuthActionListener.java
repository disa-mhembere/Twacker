/**
 * OOSE Project - Group 4
 * AuthActionListener.java
 */
package edu.jhu.twacker.client.event;

/**
 * @author Disa Mhembere
 * Interface for the requests of sign-ins and sign-ups
 */
public interface AuthActionListener
{
	/**
	 * Called when 
	 */
	public void onSignIn();

	/**
	 * Called on log outs
	 */
	public void onSignOut();
}

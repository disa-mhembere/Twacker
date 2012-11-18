/**
 * OOSE Project - Group 4
 * AuthManagerCallback.java
 */
package edu.jhu.twacker.client.manager;

/**
 * When a service is called, it is called asynchronously, and non-blocking  
 * This callback executes when the service completes
 * @param <T> a generic parameter of type same as result
 * @author Disa Mhembere
 */

public interface AuthManagerCallback<T>
{
	/** 
	 * Called if a successful callback is achieved
	 *@param result the result of the call
	 */
	public void onSuccess(T result);

	/**
	 * Called if a successful callback is returned
	 */
	public void onFail();

}

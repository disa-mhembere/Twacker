/** 
 * OOSE Project - Group 4
 * Session.java
 */

package edu.jhu.twacker.shared;

import java.io.Serializable;


/**
 * A class to hold users session details
 *  @author Disa Mhembere
 */

@SuppressWarnings("serial")
public class Session implements Serializable {
	
	String SessionID;
	String username;

	/**Gets the current 
	 * @return The current username
	 */
	public String getUsername() {
		return username;
	}

	/** Sets the username in the session
	 * @param username The desired username
	 */
	public void setUsername(String username) {
		this.username = username;
	}

	/**
	 * Construct an empty Session
	 */
	public Session() {
		SessionID = null;
	}

	/**Gets the ID associated with this session
	 * @return The session, or null if none
	 */
	public String getSessionID() {
		return SessionID;
	}

	/**Sets the session ID
	 * @param sessionID The desired sessionID
	 */
	public void setSessionID(String sessionID) {
		SessionID = sessionID;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ ((SessionID == null) ? 0 : SessionID.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Session other = (Session) obj;
		if (SessionID == null) {
			if (other.SessionID != null)
				return false;
		} else if (!SessionID.equals(other.SessionID))
			return false;
		
		return true;
	}
}

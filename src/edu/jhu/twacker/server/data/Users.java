/**
 * OOSE Project - Group 4
 * {@link Users}.java
 */
package edu.jhu.twacker.server.data;

import java.util.Date;

import javax.jdo.annotations.IdGeneratorStrategy;
import javax.jdo.annotations.IdentityType;
import javax.jdo.annotations.PersistenceCapable;
import javax.jdo.annotations.Persistent;
import javax.jdo.annotations.PrimaryKey;

/**
 * Persistence class to hold a users data
 * 
 * @author Disa Mhembere	
 */
@PersistenceCapable(identityType = IdentityType.APPLICATION)
public class Users
{

	@PrimaryKey
	@Persistent(valueStrategy = IdGeneratorStrategy.IDENTITY)
	private String username;
	@Persistent
	private String firstName;
	@Persistent
	private String lastName;
	@Persistent
	private String email;
	@Persistent
	private Date createDate; // Day user created account
	@Persistent
	private String password;

	/**
	 * Default consructor
	 */
	public Users()
	{
		this.setCreateDate(new Date());
	}
	
	public Users(String firstName, String LastName, String userName, String password,  String email )
	{
		this.setCreateDate(new Date());
		this.setUsername(userName);
		this.setFirstName(firstName);
		this.setLastName(LastName);
		this.setEmail(email);
		this.setPassword(password);
	}
	
	/**
	 * @return the username
	 */
	public String getUsername()
	{
		return username;
	}

	/**
	 * @param username the username to set
	 */
	public void setUsername(String username)
	{
		this.username = username;
	}

	/**
	 * @return the firstName
	 */
	public String getFirstName()
	{
		return firstName;
	}

	/**
	 * @param firstName the firstName to set
	 */
	public void setFirstName(String firstName)
	{
		this.firstName = firstName;
	}

	/**
	 * @return the lastName
	 */
	public String getLastName()
	{
		return lastName;
	}

	/**
	 * @param lastName the lastName to set
	 */
	public void setLastName(String lastName)
	{
		this.lastName = lastName;
	}

	/**
	 * @return the email
	 */
	public String getEmail()
	{
		return email;
	}

	/**
	 * @param email the email to set
	 */
	public void setEmail(String email)
	{
		this.email = email;
	}

	/**
	 * @return the createDate
	 */
	public Date getCreateDate()
	{
		return createDate;
	}

	/**
	 * @param createDate the createDate to set
	 */
	public void setCreateDate(Date createDate)
	{
		this.createDate = createDate;
	}

	/**
	 * @return the password
	 */
	public String getPassword()
	{
		return password;
	}

	/**
	 * @param password the password to set
	 */
	public void setPassword(String password)
	{
		this.password = password;
	}

	

}

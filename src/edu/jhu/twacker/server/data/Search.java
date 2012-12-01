/**
 * OOSE Project - Group 4
 * {@link Search}.java
 */
package edu.jhu.twacker.server.data;

/**
 * Persistent data class to hold a searchTerm submitted
 * This class is not Javadoc'd because it is out of date & will be 
 * edited momentarily
 * @author Disa Mhembere
 *
 */
import java.util.Date;
import javax.jdo.annotations.IdGeneratorStrategy;
import javax.jdo.annotations.IdentityType;
import javax.jdo.annotations.PersistenceCapable;
import javax.jdo.annotations.Persistent;
import javax.jdo.annotations.PrimaryKey;


@PersistenceCapable(identityType = IdentityType.APPLICATION)
public class Search
{

	@PrimaryKey
	@Persistent(valueStrategy = IdGeneratorStrategy.IDENTITY)
	private Long id;
	@Persistent
	private String username;
	@Persistent
	private String searchTerm;
	@Persistent
	private Date createDate;

	public Search()
	{
		this.createDate = new Date();
	}

	public Search(String username, String searchTerm)
	{
		this();
		this.setUsername(username);
		this.setSearchTerm(searchTerm);
	}

	/**
	 * @return the id
	 */
	public Long getId()
	{
		return id;
	}

	/**
	 * @param id the id to set
	 */
	public void setId(Long id)
	{
		this.id = id;
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
	 * @return the searchTerm
	 */
	public String getSearchTerm()
	{
		return searchTerm;
	}

	/**
	 * @param searchTerm the searchTerm to set
	 */
	public void setSearchTerm(String searchTerm)
	{
		this.searchTerm = searchTerm;
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

	
}
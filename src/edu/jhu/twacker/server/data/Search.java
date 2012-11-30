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
import com.google.appengine.api.users.User;

@PersistenceCapable(identityType = IdentityType.APPLICATION)
public class Search
{

	@PrimaryKey
	@Persistent(valueStrategy = IdGeneratorStrategy.IDENTITY)
	private Long id;
	@Persistent
	private User user;
	@Persistent
	private String searchTerm;
	@Persistent
	private Date createDate;

	public Search()
	{
		this.createDate = new Date();
	}

	public Search(User user, String searchTerm)
	{
		this();
		if (user == null)
		{
			this.user = new User("guest", "gmail.com"); // Not signed in
		} else
		{
			this.user = user;
		}

		this.searchTerm = searchTerm;
	}

	public Long getId()
	{
		return this.id;
	}

	public User getUser()
	{
		return this.user;
	}

	public String getSearchTerm()
	{
		return this.searchTerm;
	}

	public Date getCreateDate()
	{
		return this.createDate;
	}

	public void setUser(User user)
	{
		this.user = user;
	}

	public void setQuery(String searchTerm)
	{
		this.searchTerm = searchTerm;
	}
}
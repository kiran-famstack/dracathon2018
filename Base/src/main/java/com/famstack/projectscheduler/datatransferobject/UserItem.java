package com.famstack.projectscheduler.datatransferobject;

import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

@Entity
@Table(name = "user_info", uniqueConstraints = {@UniqueConstraint(columnNames = {"user_id"})})
public class UserItem implements FamstackBaseItem
{

    /**
	 * 
	 */
    private static final long serialVersionUID = -4647776200318098517L;

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "id_Sequence")
    @SequenceGenerator(name = "id_Sequence", sequenceName = "ID_SEQ")
    private int id;

    /** The user id. */
    @Column(name = "user_id", nullable = false)
    private String userId;

    /** The password. */
    @Column(name = "password")
    private String password;

    /** The hashkey. */
    @Column(name = "hashkey")
    private String hashkey;

    @Column(name = "first_name")
    private String firstName;

    @Column(name = "created_by")
    private int createdBy;

    @Column(name = "created_date")
    private Timestamp createdDate;

    @Column(name = "last_modified_date")
    private Timestamp lastModifiedDate;

    @Column(name = "modified_by")
    private int modifiedBy;

    public int getCreatedBy()
    {
        return createdBy;
    }

    public void setCreatedBy(int createdBy)
    {
        this.createdBy = createdBy;
    }

    @Override
    public Timestamp getCreatedDate()
    {
        return createdDate;
    }

    @Override
    public void setCreatedDate(Timestamp createdDate)
    {
        this.createdDate = createdDate;
    }

    public int getModifiedBy()
    {
        return modifiedBy;
    }

    public void setModifiedBy(int modifiedBy)
    {
        this.modifiedBy = modifiedBy;
    }

    public String getFirstName()
    {
        return firstName;
    }

    public void setFirstName(String firstName)
    {
        this.firstName = firstName;
    }

    /**
     * Gets the user id.
     * 
     * @return the user id
     */
    public String getUserId()
    {
        return userId;
    }

    /**
     * Sets the user id.
     * 
     * @param userId the new user id
     */
    public void setUserId(String userId)
    {
        this.userId = userId;
    }

    /**
     * Gets the password.
     * 
     * @return the password
     */
    public String getPassword()
    {
        return password;
    }

    /**
     * Sets the password.
     * 
     * @param password the new password
     */
    public void setPassword(String password)
    {
        this.password = password;
    }

    /**
     * Gets the hashkey.
     * 
     * @return the hashkey
     */
    public String getHashkey()
    {
        return hashkey;
    }

    /**
     * Sets the hashkey.
     * 
     * @param hashkey the new hashkey
     */
    public void setHashkey(String hashkey)
    {
        this.hashkey = hashkey;
    }

    public int getId()
    {
        return id;
    }

    public void setId(int id)
    {
        this.id = id;
    }

    public Timestamp getLastModifiedDate()
    {
        return lastModifiedDate;
    }

    @Override
    public void setLastModifiedDate(Timestamp lastModifiedDate)
    {
        this.lastModifiedDate = lastModifiedDate;
    }
}

package com.famstack.projectscheduler.configuration;

import javax.annotation.Resource;

import com.famstack.projectscheduler.BaseFamstackService;
import com.famstack.projectscheduler.dataaccess.FamstackDataAccessObjectManager;
import com.famstack.projectscheduler.datatransferobject.OrderItem;
import com.famstack.projectscheduler.datatransferobject.UserItem;
import com.famstack.projectscheduler.manager.FamstackUserProfileManager;

public class FamstackApplicationConfiguration extends BaseFamstackService
{

    @Resource
    FamstackUserProfileManager famstackUserProfileManager;

    @Resource
    FamstackDataAccessObjectManager famstackDataAccessObjectManager;

    private String hostName;

    private int portNumber;

    private String protocol;

    public void initialize()
    {

        logDebug("Initializing FamstackApplicationConfiguration...");
        logDebug("END : Initializing FamstackApplicationConfiguration...");

    }

    public String getHostName()
    {
        return hostName;
    }

    public void setHostName(String hostName)
    {
        this.hostName = hostName;
    }

    public int getPortNumber()
    {
        return portNumber;
    }

    public void setPortNumber(int portNumber)
    {
        this.portNumber = portNumber;
    }

    public String getProtocol()
    {
        return protocol;
    }

    public void setProtocol(String protocol)
    {
        this.protocol = protocol;
    }

    public FamstackUserProfileManager getFamstackUserProfileManager()
    {
        return famstackUserProfileManager;
    }

    public UserItem getCurrentUser()
    {
        return getFamstackUserSessionConfiguration().getCurrentUser();
    }
    
    public OrderItem getCurrentOrder()
    {
        return getFamstackUserSessionConfiguration().getOrderItem();
    }

    public String getUrl()
    {
        return protocol + "://" + hostName + ":" + portNumber + "/" + "bops/dashboard";
    }

    public int getCurrentUserId()
    {
        int userId = 0;
        if (getCurrentUser() != null) {
            userId = getCurrentUser().getId();
        }
        return userId;
    }

}

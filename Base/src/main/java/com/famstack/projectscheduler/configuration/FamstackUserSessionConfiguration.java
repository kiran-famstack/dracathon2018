package com.famstack.projectscheduler.configuration;

import java.io.Serializable;

import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.stereotype.Component;

import com.famstack.projectscheduler.datatransferobject.OrderItem;
import com.famstack.projectscheduler.datatransferobject.UserItem;
import com.famstack.projectscheduler.security.login.LoginResult;
import com.famstack.projectscheduler.security.login.LoginResult.Status;

/**
 * The Class FamstackUserSessionConfiguration.
 */
@Component
@Scope(value = "session", proxyMode = ScopedProxyMode.TARGET_CLASS)
public class FamstackUserSessionConfiguration implements Serializable
{

    /** The Constant serialVersionUID. */
    private static final long serialVersionUID = -2779591617048681125L;

    /** The login result. */
    private LoginResult loginResult;
    
    private OrderItem orderItem;

	public LoginResult getLoginResult()
    {
        if (loginResult == null) {
            loginResult = new LoginResult();
            loginResult.setStatus(Status.FAILED);
        }
        return loginResult;
    }

    /**
     * Sets the login result.
     * 
     * @param loginResult the new login result
     */
    public void setLoginResult(LoginResult loginResult)
    {
        this.loginResult = loginResult;
    }

    public UserItem getCurrentUser()
    {
        return getLoginResult().getUserItem();
    }

	public OrderItem getOrderItem() {
		return orderItem;
	}

	public void setOrderItem(OrderItem orderItem) {
		this.orderItem = orderItem;
	}
}

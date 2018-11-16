package com.famstack.projectscheduler.manager;

import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

import com.famstack.projectscheduler.contants.HQLStrings;
import com.famstack.projectscheduler.datatransferobject.UserItem;
import com.famstack.projectscheduler.employees.bean.UserDetails;
import com.famstack.projectscheduler.security.FamstackAuthenticationToken;
import com.famstack.projectscheduler.security.hasher.FamstackSecurityTokenManager;
import com.famstack.projectscheduler.security.hasher.generator.PasswordTokenGenerator;
import com.famstack.projectscheduler.security.login.LoginResult;
import com.famstack.projectscheduler.security.login.LoginResult.Status;

/**
 * The Class UserProfileManager.
 * 
 * @author Aneeshkumar
 * @version 1.0
 */
@Component
public class FamstackUserProfileManager extends BaseFamstackManager
{

    @Resource
    PasswordTokenGenerator passwordTokenGenerator;

    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

    /**
     * Login.
     * 
     * @param name the name
     * @param password the password
     * @return the login result
     */
    public LoginResult login(String name, String password)
    {
        LoginResult loginResult = new LoginResult();
        UserItem userItem = getUserItem(name);
        if (userItem != null) {
            String encryptPassword = FamstackSecurityTokenManager.encryptString(password, userItem.getHashkey());
            if (userItem.getPassword().equals(encryptPassword)) {
                loginResult.setStatus(Status.SUCCESS);
                loginResult.setUserItem(userItem);
                return loginResult;
            }
        }
        loginResult.setStatus(Status.FAILED);
        return loginResult;

    }

    /**
     * Gets the user token.
     * 
     * @return the user token
     */
    public Authentication getUserToken()
    {
        return SecurityContextHolder.getContext().getAuthentication();
    }

    /**
     * Gets the login result.
     * 
     * @return the login result
     */
    public LoginResult getLoginResult()
    {
        Authentication authentication = getUserToken();
        if (authentication instanceof FamstackAuthenticationToken) {
            return ((FamstackAuthenticationToken) authentication).getLoginResult();
        }
        return null;
    }

    public void createUserItem(UserDetails userDetails)
    {
        
    }
    
    public void createUserItem(String email, String password, String name) {
    	String hashKey = passwordTokenGenerator.generate(32);
        UserItem userItem = new UserItem();
        String encryptedPassword = FamstackSecurityTokenManager.encryptString(password, hashKey);
        userItem.setHashkey(hashKey);
        userItem.setPassword(encryptedPassword);
        userItem.setUserId(email);
        userItem.setFirstName(name);
        getFamstackDataAccessObjectManager().saveOrUpdateItem(userItem);
	}

    public void updateUserItem(UserDetails userDetails)
    {
        UserItem userItem = getUserItem(userDetails.getEmail());
        if (userItem != null) {
            saveUserItem(userDetails, userItem);
        }
    }

    private void saveUserItem(UserDetails userDetails, UserItem userItem)
    {
        userItem.setUserId(userDetails.getEmail());
        userItem.setFirstName(userDetails.getFirstName());
        getFamstackDataAccessObjectManager().saveOrUpdateItem(userItem);
    }

    public UserItem getUserItem(String userId)
    {
        Map<String, Object> dataMap = new HashMap<String, Object>();
        dataMap.put("userId", userId);
        List<?> userItemList =
            getFamstackDataAccessObjectManager().executeAllGroupQuery(
                HQLStrings.getString("FamstackQueryStringsusersByUserId"), dataMap);
        if (!userItemList.isEmpty()) {
            UserItem userItem = (UserItem) userItemList.get(0);
            return  userItem;
        }
        return null;
    }

	
}

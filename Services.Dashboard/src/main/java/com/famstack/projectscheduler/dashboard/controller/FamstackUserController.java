package com.famstack.projectscheduler.dashboard.controller;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.servlet.ModelAndView;

import com.famstack.projectscheduler.BaseFamstackService;
import com.famstack.projectscheduler.dashboard.manager.FamstackDashboardManager;
import com.famstack.projectscheduler.datatransferobject.OrderItem;
import com.famstack.projectscheduler.datatransferobject.UserItem;
import com.famstack.projectscheduler.security.FamstackAuthenticationToken;
import com.famstack.projectscheduler.security.hasher.FamstackSecurityTokenManager;
import com.famstack.projectscheduler.security.login.LoginResult.Status;
import com.famstack.projectscheduler.security.login.UserSecurityContextBinder;

@Controller
@SessionAttributes
public class FamstackUserController extends BaseFamstackService
{

    @Resource
    FamstackDashboardManager famstackDashboardManager;

    /** The authentication manager. */
    @Resource
    private AuthenticationManager authenticationManager;

    /** The user security context binder. */
    @Resource
    private UserSecurityContextBinder userSecurityContextBinder;

    
    @RequestMapping("/{path}")
    public String login(@PathVariable("path") String path, Model model)
    {
        logDebug("Request path :" + path);
        return path;
    }
    
    @RequestMapping(value = "/loginAjax", method = RequestMethod.POST)
    @ResponseBody
    public String loginAjax(@RequestParam("email") String username, @RequestParam("password") String password)
    {

        FamstackAuthenticationToken token = new FamstackAuthenticationToken(username, password);
        FamstackAuthenticationToken authentication =
            (FamstackAuthenticationToken) authenticationManager.authenticate(token);
        if (authentication.getLoginResult().getStatus() == Status.SUCCESS) {
            userSecurityContextBinder.bindUserAuthentication(authentication);
            return "{\"status\": true}";
        } 

        return "{\"status\": false, \"error\": \"Bad Credentials\"}";
    }

    @RequestMapping(value = "/logout", method = RequestMethod.GET)
    public String logOut(HttpServletRequest request)
    {
        userSecurityContextBinder.unbindUserAuthentication();
        request.getSession().invalidate();
        return "index";
    }

    @RequestMapping(value = "/createUser", method = RequestMethod.POST)
    @ResponseBody
    public String createUser(@RequestParam("email") String email,
    		@RequestParam("password") String password, 
    		@RequestParam("name") String name)
    {
        try {
            famstackDashboardManager.createUser(email, password, name);
            return loginAjax(email, password);
        } catch (Exception e) {
            return "{\"status\": false,\"errorCode\": \"Duplicate\"}";
        }
    }
    
    @RequestMapping(value = "/createOrder", method = RequestMethod.POST)
    public ModelAndView createOrder(HttpServletRequest request,
            HttpServletResponse response)
    {
    	
        OrderItem orderItem = famstackDashboardManager.createOrder(request);
        return new ModelAndView("checkoutPage").addObject("order", orderItem);
    }
    
    @RequestMapping(value = "/checkout", method = RequestMethod.GET)
    public ModelAndView checkoutPage()
    {
    	OrderItem currentOrderItem = getFamstackUserSessionConfiguration().getOrderItem();
        return new ModelAndView("checkoutPage").addObject("order", currentOrderItem);
    }
    
    @RequestMapping(value = "/orderDetails", method = RequestMethod.GET)
    public ModelAndView orderDetailsPage()
    {
    	UserItem userItem = getFamstackUserSessionConfiguration().getCurrentUser();
    	List<OrderItem> orderItemList = null;
    	if (userItem != null) {
    	 orderItemList = famstackDashboardManager.orderDetailsPage(userItem);
    	}
        return new ModelAndView("orderDetailsPage").addObject("orders", orderItemList);
    }
    
    @RequestMapping(value = "/orderconfirmation/{orderId}", method = RequestMethod.GET)
    public ModelAndView orderConfirmation( @PathVariable(value = "orderId") Integer orderId)
    {
    	OrderItem currentOrderItem = (OrderItem) famstackDashboardManager.getFamstackDataAccessObjectManager().getItemById(orderId, OrderItem.class);
        return new ModelAndView("orderConfirmationPage").addObject("order", currentOrderItem);
    }
    
    @RequestMapping(value = "/payment", method = RequestMethod.GET)
    public ModelAndView paymentPage(@RequestParam(name="paymentToken", defaultValue="") String paymentToken, HttpServletRequest request)
    {
    	OrderItem currentOrderItem = getFamstackUserSessionConfiguration().getOrderItem();
    	String orderOwnerEmailId = null;
    	if (StringUtils.isNotBlank(paymentToken)) {
			String paymentTokenString = FamstackSecurityTokenManager.decrypt(paymentToken, "aW4^06+iI=*MV^jl9AddsNxaR%4U2R^N");
			String[]tokens = paymentTokenString.split("#");
			if (tokens != null && tokens.length > 0) {
				String orderId = tokens[0];
				orderOwnerEmailId = tokens[1];
				UserItem currentUserItem = getFamstackUserSessionConfiguration().getCurrentUser();
				if (currentUserItem != null && StringUtils.isNotBlank(orderOwnerEmailId) && orderOwnerEmailId.equalsIgnoreCase(currentUserItem.getUserId())) {
					 userSecurityContextBinder.unbindUserAuthentication();
				     request.getSession().invalidate();
				}
				
				currentOrderItem = (OrderItem) famstackDashboardManager.getFamstackDataAccessObjectManager().getItemById(Integer.parseInt(orderId), OrderItem.class);
			}
		} else {
			paymentToken = getPaymentToken();
			if (StringUtils.isNotBlank(paymentToken)) {
				paymentToken =  FamstackSecurityTokenManager.encryptString(paymentToken, "aW4^06+iI=*MV^jl9AddsNxaR%4U2R^N");
			}
		}
    	try {
			paymentToken = URLEncoder.encode(paymentToken, "UTF-8");
		} catch (UnsupportedEncodingException e) {
		}
        return new ModelAndView("paymentPage").addObject("order", currentOrderItem).addObject("orderOwnerEmailId", orderOwnerEmailId).addObject("paymentToken", paymentToken);
    }

	private String getPaymentToken() {
		String paymentToken = "";
		UserItem currentUserItem = getFamstackUserSessionConfiguration().getCurrentUser();
		OrderItem orderItem = getFamstackUserSessionConfiguration().getOrderItem();
		if (orderItem != null) {
			paymentToken =  "" + orderItem.getOrderId();
			 if (currentUserItem != null){
				 paymentToken += "#" + currentUserItem.getUserId();
			 }
		}
		return paymentToken;
	}
    
    @RequestMapping(value = "/sendPaymentLink", method = RequestMethod.POST)
    @ResponseBody
    public String sendPaymentLink(@RequestParam(name="paymentToken", defaultValue="") String paymentToken
    		, @RequestParam(name="email1", defaultValue="") String email1
    		, @RequestParam(name="email2", defaultValue="") String email2
    		, @RequestParam(name="email3", defaultValue="") String email3
    		, @RequestParam(name="orderOwnerName", defaultValue="") String orderOwnerName)
    {
    	List<String> emailIdList = new ArrayList<>();
    	if (StringUtils.isNotBlank(email1)) {
    		emailIdList.add(email1);
    	}
    	
    	if (StringUtils.isNotBlank(email2)) {
    		emailIdList.add(email2);
    	}
    	if (StringUtils.isNotBlank(email3)) {
    		emailIdList.add(email3);
    	}
    	
    	famstackDashboardManager.sendEmails(emailIdList, paymentToken,orderOwnerName);
		famstackDashboardManager.addPayment("PBO", paymentToken);
		getFamstackUserSessionConfiguration().setOrderItem(null);
		
        return "{\"status\": true}";
    }
    @RequestMapping(value = "/addBillingAddress", method = RequestMethod.POST)
    @ResponseBody
    public String addBillingAddress(
    		@RequestParam("fullName") String fullName, 
    		@RequestParam("landMark") String landMark, 
    		@RequestParam("mobileNumber") String mobileNumber,
    		@RequestParam("city") String city,
    		@RequestParam("type") String type)
    {
        OrderItem orderItem = famstackDashboardManager.addBillingAddress(fullName, landMark, mobileNumber, city, type);
        return "{\"status\": true}";
    }
    
    @RequestMapping(value = "/addPayment", method = RequestMethod.POST)
    @ResponseBody
    public String payment(@RequestParam("paymentType") String paymentType,
    		@RequestParam(name="paymentToken", defaultValue="") String paymentToken)
    {
       OrderItem orderItem = famstackDashboardManager.addPayment(paymentType, paymentToken);
       return "{\"status\": true}";
    }
}

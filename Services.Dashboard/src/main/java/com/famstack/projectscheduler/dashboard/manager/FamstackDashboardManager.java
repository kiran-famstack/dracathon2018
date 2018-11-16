package com.famstack.projectscheduler.dashboard.manager;

import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

import com.famstack.email.FamstackEmailSender;
import com.famstack.email.util.FamstackTemplateEmailInfo;
import com.famstack.projectscheduler.contants.HQLStrings;
import com.famstack.projectscheduler.datatransferobject.AddressItem;
import com.famstack.projectscheduler.datatransferobject.OrderItem;
import com.famstack.projectscheduler.datatransferobject.PaymentItem;
import com.famstack.projectscheduler.datatransferobject.SkuItem;
import com.famstack.projectscheduler.datatransferobject.UserItem;
import com.famstack.projectscheduler.employees.bean.UserDetails;
import com.famstack.projectscheduler.manager.BaseFamstackManager;
import com.famstack.projectscheduler.manager.FamstackUserProfileManager;
import com.famstack.projectscheduler.security.hasher.FamstackSecurityTokenManager;

@Component
public class FamstackDashboardManager extends BaseFamstackManager
{

    @Resource
    FamstackUserProfileManager userProfileManager;
    
    @Resource
    FamstackEmailSender emailSender;

	public void createUser(String email, String password, String name) {
        userProfileManager.createUserItem(email, password, name);
        
        FamstackTemplateEmailInfo emailInfo = new FamstackTemplateEmailInfo();
		Map dataMap = new HashMap<>();
		dataMap.put("firstName", name);
        String[] emailIdList = new String[]{email};
		sendMail("You have been registed in EShoppy", "userRegistraion", dataMap, emailIdList);
		
	}

    public UserItem getUserItem(String emailId)
    {
        return userProfileManager.getUserItem(emailId);
    }

	public OrderItem createOrder(HttpServletRequest request) 
	{
		OrderItem currentOrderItem = getFamstackUserSessionConfiguration().getOrderItem();
		if (currentOrderItem == null) {
			currentOrderItem = new OrderItem();
			currentOrderItem.setStatus("INCOMPLETE");
			currentOrderItem.setUserId(getFamstackUserSessionConfiguration().getCurrentUser().getId());
		} 
		
		Set<SkuItem> skuItems = currentOrderItem.getSkuItems();
		if (skuItems == null) {
			skuItems = new HashSet<>();
			currentOrderItem.setSkuItems(skuItems);
		} else {
			try {
				for (SkuItem skuItem : skuItems) {
					 getFamstackDataAccessObjectManager().deleteItem(skuItem);
				}
			}catch(Exception e){
				
			}
		}
		
		skuItems.clear();
		int index = 1;
		boolean exit = false;
		Double orderSubTotal = 0d;
		do {
			String itemNumber = request.getParameter("item_number_" + index);
			String itemName = request.getParameter("item_name_" + index);
			String amount = request.getParameter("amount_" + index);
			String discountAmount = request.getParameter("discount_amount_" + index);
			String quantity = request.getParameter("quantity_" + index);
			logDebug("itemNumber : " + itemNumber );
			if (StringUtils.isBlank(itemNumber)){
				exit = true;
			}
			if (!exit){
				SkuItem skuItem = new SkuItem();
				Double amountDouble = amount != null ? Double.parseDouble(amount) : 0d;
				int quantityInt =quantity != null ? Integer.parseInt(quantity) : 0;
				Double discountDouble = discountAmount != null ? Double.parseDouble(discountAmount) : 0d;
				skuItem.setAmount(amountDouble);
				skuItem.setItemName(itemName);
				skuItem.setItemNumber(itemNumber);
				skuItem.setQuantity(quantityInt);
				skuItem.setDiscountAmount(discountDouble);
				skuItem.setOrderItem(currentOrderItem);
				currentOrderItem.getSkuItems().add(skuItem);
				orderSubTotal+=(amountDouble*quantityInt) - discountDouble;
			}
			index++;
		} while(!exit);
		currentOrderItem.setSubTotal(orderSubTotal);
		OrderItem savedOrderItem = (OrderItem) getFamstackDataAccessObjectManager().saveOrUpdateItem(currentOrderItem);
		getFamstackUserSessionConfiguration().setOrderItem(savedOrderItem);
		return savedOrderItem;
	}

	public OrderItem addPayment(String paymentType, String paymentToken) {
		OrderItem currentOrderItem = getFamstackUserSessionConfiguration().getOrderItem();
		
		try {
			paymentToken = java.net.URLDecoder.decode(paymentToken, "UTF-8");
		} catch (UnsupportedEncodingException e) {
		}
		if (StringUtils.isNotBlank(paymentToken)) {
			
			String paymentTokenString = FamstackSecurityTokenManager.decrypt(paymentToken, "aW4^06+iI=*MV^jl9AddsNxaR%4U2R^N");
			String[]tokens = paymentTokenString.split("#");
			if (tokens != null && tokens.length > 0) {
				String orderId = tokens[0];
				currentOrderItem = (OrderItem) getFamstackDataAccessObjectManager().getItemById(Integer.parseInt(orderId), OrderItem.class);
			} else {
				currentOrderItem = null;
			}
		}
		
		if (currentOrderItem != null) {
			PaymentItem paymentItem = currentOrderItem.getPaymentItem();
			if (paymentItem == null) {
				paymentItem = new PaymentItem();
			}
			paymentItem.setAmount(currentOrderItem.getSubTotal());
			
			Map dataMap = new HashMap();
			UserItem orderUserItem = (UserItem) getFamstackDataAccessObjectManager().getItemById(currentOrderItem.getUserId(), UserItem.class);
			String emailId = orderUserItem.getUserId();
			dataMap.put("firstName", orderUserItem.getFirstName());
			if ("PBO".equalsIgnoreCase(paymentType)) {
				paymentItem.setStatus("PENDING_PBO");
				currentOrderItem.setStatus("PENDING_PAYMENT");
				dataMap.put("paymentStatus", "and pending for payment");
			} else {
				paymentItem.setStatus("DONE");
				currentOrderItem.setStatus("SUBMITED");
				dataMap.put("paymentStatus", "successfully");
				paymentItem.setPaidBy(getFamstackUserSessionConfiguration().getCurrentUser().getId());
			}
			paymentItem = (PaymentItem) getFamstackDataAccessObjectManager().saveOrUpdateItem(paymentItem);
			currentOrderItem.setPaymentItem(paymentItem);
			currentOrderItem = (OrderItem) getFamstackDataAccessObjectManager().saveOrUpdateItem(currentOrderItem);
			String[] emailIdList = new String[]{emailId};
			sendMail("Your order has been placed", "orderConfirmation", dataMap, emailIdList);
			
		}
		return currentOrderItem;
	}
	
	@Async
	public void sendMail(String subject, String templateName, Map dataMap, String[] toEmail){
		FamstackTemplateEmailInfo emailInfo = new FamstackTemplateEmailInfo();
		emailInfo.setMailFrom("eshoppysupport1443@gmail.com");
		emailInfo.setMailSubject(subject);
        emailInfo.setVelocityTemplateName(templateName);
        emailInfo.setTemplateParameters(dataMap);
        
    	emailSender.sendEmail(emailInfo, toEmail);
	}

	public OrderItem addBillingAddress(String fullName, String landMark,
			String mobileNumber, String city, String type) {
		
		OrderItem currentOrderItem = getFamstackUserSessionConfiguration().getOrderItem();

		if (currentOrderItem != null) {
			AddressItem addressItem = currentOrderItem.getAddressItem();
			if (addressItem == null) {
				addressItem = new AddressItem();
			}
			addressItem.setCity(city);
			addressItem.setFullName(fullName);
			addressItem.setLandMark(landMark);
			addressItem.setMobileNumber(mobileNumber);
			addressItem.setType(type);
			addressItem = (AddressItem) getFamstackDataAccessObjectManager().saveOrUpdateItem(addressItem);
			
			currentOrderItem.setAddressItem(addressItem);
			currentOrderItem = (OrderItem) getFamstackDataAccessObjectManager().saveOrUpdateItem(currentOrderItem);
			getFamstackUserSessionConfiguration().setOrderItem(currentOrderItem);
		}
		return currentOrderItem;
	}

	@Async
	public void sendEmails(List<String> emailIdList, String paymentToken, String orderOwnerName) {
		FamstackTemplateEmailInfo emailInfo = new FamstackTemplateEmailInfo();
		Map dataMap = new HashMap<>();
		dataMap.put("paymentToken", paymentToken);
		dataMap.put("orderOwnerName", orderOwnerName);
		emailInfo.setMailFrom("eshoppysupport1443@gmail.com");
		emailInfo.setMailSubject("Payment Request from E Shoppy for the order placed by " + orderOwnerName);
        emailInfo.setVelocityTemplateName("paymentLink");
        emailInfo.setTemplateParameters(dataMap);
        
		emailSender.sendEmail(emailInfo, (String[]) emailIdList.toArray(new String[0]));
	}

	public List<OrderItem> orderDetailsPage(UserItem userItem) {
		 Map<String, Object> dataMap = new HashMap<String, Object>();
	        dataMap.put("userId", userItem.getId());
	        List<?> orderItemList =
	            getFamstackDataAccessObjectManager().executeAllGroupQuery(
	                HQLStrings.getString("getAllUserOrders"), dataMap);
		return (List<OrderItem>) orderItemList;
	}


}

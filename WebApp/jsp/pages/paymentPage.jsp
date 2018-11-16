<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

    <%@include file="fragments/header.jspf" %>
    
    <style>
    
    .pbo-form .submit {
    outline: none;
    overflow: hidden;
    display: inline-block;
    visibility: visible!important;
    -webkit-font-smoothing: antialiased;
    border: none;
    padding: 1px;
    text-decoration: none;
    -webkit-touch-callout: none;
    -webkit-user-select: none;
    -moz-user-select: none;
    -ms-user-select: none;
    -o-user-select: none;
    user-select: none;
    cursor: pointer;
}

    .pbo-form .submit span {
    padding: 12px 25px;
    letter-spacing: 1px;
    background: #FF5722;
    font-size: 16px;
    color: #fff;
    border: none;
    outline: none;
    transition: 0.5s all;
    -webkit-transition: 0.5s all;
    -moz-transition: 0.5s all;
    -o-transition: 0.5s all;
    -ms-transition: 0.5s all;
    display: inline-block;
}
    </style>
    
	<!-- //navigation -->
	<!-- banner-2 -->
	<div class="hide page-head_agile_info_w3l">

	</div>
	<!-- //banner-2 -->
	<!-- page -->
	<div class="services-breadcrumb">
		<div class="agile_inner_breadcrumb">
			<div class="container">
				<ul class="w3_short">
					<li>
						<a href="index.html">Home</a>
						<i>|</i>
					</li>
					<li>Payment</li>
				</ul>
			</div>
		</div>
	</div>
	<!-- //page -->
	<!-- payment page-->
	<div class="privacy">
		<div class="container">
			<!-- tittle heading -->
			<h3 class="tittle-w3l">Payment
				<span class="heading-style">
					<i></i>
					<i></i>
					<i></i>
				</span>
			</h3>
			<div class="checkout-right" style="margin-bottom: 15px">
			<c:if test="${not empty order and  not empty order.skuItems}">
				<h4>Your shopping cart contains:
					<span>${fn:length(order.skuItems)}  Products</span>
				</h4>
				<%@include file="fragments/cartSummary.jspf" %>
			</c:if>
			<c:if test="${empty order or  empty order.skuItems}">
				<h4>Your shopping cart is empty!
				</h4>
			</c:if>
			</div>
			<!-- //tittle heading -->
			<div class="checkout-right">
				<!--Horizontal Tab-->
				<div id="parentHorizontalTab">
					<ul class="resp-tabs-list hor_1">
					<c:if test="${empty orderOwnerEmailId}">
							<li>Pay4Cart</li>
					</c:if>
						
						<li>Cash on delivery (COD)</li>
						<li>Credit/Debit</li>
						<li>Net Banking</li>
						<li>Paypal Account</li>
					</ul>
					<div class="resp-tabs-container hor_1">
					<input class="form-control" type="hidden" id="paymentToken" value="${paymentToken}">
					<input class="form-control" type="hidden" id="orderOwnerName" value="${currentUser.firstName}">
					<c:if test="${empty orderOwnerEmailId}">
							<div>
							<div class="vertical_post check_box_agile">
								<h5>P4C</h5>
								<div class="first-row form-group">
											<div class="controls">
											<span style="background-color: lightgray;border: 1px solid gray;float: left;margin-right: 5px;">
											http://localhost:8080/gc/store/payment?paymentToken=${paymentToken}</span>
												<button onclick="copyFunction()">Copy text</button>
											</div>
										</div>
									<div class="credit-card-wrapper">
										<div class="first-row form-group">
											<div class="controls">
												<label class="control-label">First Email id</label>
												<input class="form-control" type="text" id="emailid1" placeholder="name1@email.com">
											</div>
										</div>
										<div class="first-row form-group">
											<div class="controls">
												<label class="control-label">Second Email id</label>
												<input class="form-control" type="text" id="emailid2" placeholder="name2@email.com">
											</div>
										</div>
										<div class="first-row form-group">
											<div class="controls">
												<label class="control-label">Third Email id</label>
												<input class="form-control" type="text" id="emailid3" placeholder="name3@email.com">
											</div>
										</div>
										<button class="submit" onclick="sendPaymentLink();">
											<span>Send payment link </span>
										</button>
									</div>
							</div>
						</div>
						</c:if>
						<div>
							<div class="vertical_post check_box_agile">
								<h5>COD</h5>
								<div class="checkbox">
									<div class="check_box_one cashon_delivery">
										<label class="anim">
											<input type="checkbox" class="checkbox">
											<span> We also accept Credit/Debit card on delivery. Please Check with the agent.</span>
										</label>
									</div>

								</div>
							</div>
						</div>
						<div>
							<form action="#" method="post" class="creditly-card-form agileinfo_form">
								<div class="creditly-wrapper wthree, w3_agileits_wrapper">
									<div class="credit-card-wrapper">
										<div class="first-row form-group">
											<div class="controls">
												<label class="control-label">Name on Card</label>
												<input class="billing-address-name form-control" type="text" name="name" placeholder="John Smith">
											</div>
											<div class="w3_agileits_card_number_grids">
												<div class="w3_agileits_card_number_grid_left">
													<div class="controls">
														<label class="control-label">Card Number</label>
														<input class="number credit-card-number form-control" type="text" name="number" inputmode="numeric" autocomplete="cc-number"
														    autocompletetype="cc-number" x-autocompletetype="cc-number" placeholder="&#149;&#149;&#149;&#149; &#149;&#149;&#149;&#149; &#149;&#149;&#149;&#149; &#149;&#149;&#149;&#149;">
													</div>
												</div>
												<div class="w3_agileits_card_number_grid_right">
													<div class="controls">
														<label class="control-label">CVV</label>
														<input class="security-code form-control" Â· inputmode="numeric" type="text" name="security-code" placeholder="&#149;&#149;&#149;">
													</div>
												</div>
												<div class="clear"> </div>
											</div>
											<div class="controls">
												<label class="control-label">Expiration Date</label>
												<input class="expiration-month-and-year form-control" type="text" name="expiration-month-and-year" placeholder="MM / YY">
											</div>
										</div>
										<button class="submit">
											<span>Make a payment </span>
										</button>
									</div>
								</div>
							</form>

						</div>
						<div>
							<div class="vertical_post">
								<form action="#" method="post">
									<h5>Select From Popular Banks</h5>
									<div class="swit-radio">
										<div class="check_box_one">
											<div class="radio_one">
												<label>
													<input type="radio" name="radio" checked="">
													<i></i>Syndicate Bank</label>
											</div>
										</div>
										<div class="check_box_one">
											<div class="radio_one">
												<label>
													<input type="radio" name="radio">
													<i></i>Bank of Baroda</label>
											</div>
										</div>
										<div class="check_box_one">
											<div class="radio_one">
												<label>
													<input type="radio" name="radio">
													<i></i>Canara Bank</label>
											</div>
										</div>
										<div class="check_box_one">
											<div class="radio_one">
												<label>
													<input type="radio" name="radio">
													<i></i>ICICI Bank</label>
											</div>
										</div>
										<div class="check_box_one">
											<div class="radio_one">
												<label>
													<input type="radio" name="radio">
													<i></i>State Bank Of India</label>
											</div>
										</div>
										<div class="clearfix"></div>
									</div>
									<h5>Or Select Other Bank</h5>
									<div class="section_room_pay">
										<select class="year">
											<option value="">=== Other Banks ===</option>
											<option value="ALB-NA">Allahabad Bank NetBanking</option>
											<option value="ADB-NA">Andhra Bank</option>
											<option value="BBK-NA">Bank of Bahrain and Kuwait NetBanking</option>
											<option value="BBC-NA">Bank of Baroda Corporate NetBanking</option>
											<option value="BBR-NA">Bank of Baroda Retail NetBanking</option>
											<option value="BOI-NA">Bank of India NetBanking</option>
											<option value="BOM-NA">Bank of Maharashtra NetBanking</option>
											<option value="CSB-NA">Catholic Syrian Bank NetBanking</option>
											<option value="CBI-NA">Central Bank of India</option>
											<option value="CUB-NA">City Union Bank NetBanking</option>
											<option value="CRP-NA">Corporation Bank</option>
											<option value="DBK-NA">Deutsche Bank NetBanking</option>
											<option value="DCB-NA">Development Credit Bank</option>
											<option value="DC2-NA">Development Credit Bank - Corporate</option>
											<option value="DLB-NA">Dhanlaxmi Bank NetBanking</option>
											<option value="FBK-NA">Federal Bank NetBanking</option>
											<option value="IDS-NA">Indusind Bank NetBanking</option>
											<option value="IOB-NA">Indian Overseas Bank</option>
											<option value="ING-NA">ING Vysya Bank (now Kotak)</option>
											<option value="JKB-NA">Jammu and Kashmir NetBanking</option>
											<option value="JSB-NA">Janata Sahakari Bank Limited</option>
											<option value="KBL-NA">Karnataka Bank NetBanking</option>
											<option value="KVB-NA">Karur Vysya Bank NetBanking</option>
											<option value="LVR-NA">Lakshmi Vilas Bank NetBanking</option>
											<option value="OBC-NA">Oriental Bank of Commerce NetBanking</option>
											<option value="CPN-NA">PNB Corporate NetBanking</option>
											<option value="PNB-NA">PNB NetBanking</option>
											<option value="RSD-DIRECT">Rajasthan State Co-operative Bank-Debit Card</option>
											<option value="RBS-NA">RBS (The Royal Bank of Scotland)</option>
											<option value="SWB-NA">Saraswat Bank NetBanking</option>
											<option value="SBJ-NA">SB Bikaner and Jaipur NetBanking</option>
											<option value="SBH-NA">SB Hyderabad NetBanking</option>
											<option value="SBM-NA">SB Mysore NetBanking</option>
											<option value="SBT-NA">SB Travancore NetBanking</option>
											<option value="SVC-NA">Shamrao Vitthal Co-operative Bank</option>
											<option value="SIB-NA">South Indian Bank NetBanking</option>
											<option value="SBP-NA">State Bank of Patiala NetBanking</option>
											<option value="SYD-NA">Syndicate Bank NetBanking</option>
											<option value="TNC-NA">Tamil Nadu State Co-operative Bank NetBanking</option>
											<option value="UCO-NA">UCO Bank NetBanking</option>
											<option value="UBI-NA">Union Bank NetBanking</option>
											<option value="UNI-NA">United Bank of India NetBanking</option>
											<option value="VJB-NA">Vijaya Bank NetBanking</option>
										</select>
									</div>
									<input type="submit" value="PAY NOW">
								</form>
							</div>
						</div>
						<div>
							<div id="tab4" class="tab-grid" style="display: block;">
								<div class="row">
									<div class="col-md-6">
										<img class="pp-img" src="${fn:escapeXml(images)}/paypal.png" alt="Image Alternative text" title="Image Title">
										<p>Important: You will be redirected to PayPal's website to securely complete your payment.</p>
										<a class="btn btn-primary">Checkout via Paypal</a>
									</div>
									<div class="col-md-6 number-paymk">
											<div class="clearfix">
												<div class="form-group form-group-cc-number">
													<label>Card Number</label>
													<input class="form-control" placeholder="xxxx xxxx xxxx xxxx" type="text">
													<span class="cc-card-icon"></span>
												</div>
												<div class="form-group form-group-cc-cvc">
													<label>CVV</label>
													<input class="form-control" placeholder="xxxx" type="text">
												</div>
											</div>
											<div class="clearfix">
												<div class="form-group form-group-cc-name">
													<label>Card Holder Name</label>
													<input class="form-control" type="text">
												</div>
												<div class="form-group form-group-cc-date">
													<label>Valid Thru</label>
													<input class="form-control" placeholder="mm/yy" type="text">
												</div>
											</div>
											<div class="checkbox checkbox-small">
												<label>
													<input class="i-check" type="checkbox" checked="">Add to My Cards</label>
											</div>
											<input type="submit" class="submit" value="Proceed Payment">
									</div>
								</div>

							</div>
						</div>

					</div>
				</div>
				<!--Plug-in Initialisation-->
			</div>
		</div>
	</div>
	<!-- //payment page -->

	<!-- newsletter -->
	<%@include file="fragments/footer.jspf" %>
	
	
	<!-- easy-responsive-tabs -->
	<link rel="stylesheet" type="text/css" href="${fn:escapeXml(css)}/easy-responsive-tabs.css " />
	<script src="${fn:escapeXml(js)}/easyResponsiveTabs.js"></script>

	<script>
		$(document).ready(function () {
			//Horizontal Tab
			$('#parentHorizontalTab').easyResponsiveTabs({
				type: 'default', //Types: default, vertical, accordion
				width: 'auto', //auto or any width like 600px
				fit: true, // 100% fit in a container
				tabidentify: 'hor_1', // The tab groups identifier
				activate: function (event) { // Callback function if tab is switched
					var $tab = $(this);
					var $info = $('#nested-tabInfo');
					var $name = $('span', $info);
					$name.text($tab.text());
					$info.show();
				}
			});
		});
	</script>
	<!-- //easy-responsive-tabs -->

	<!-- credit-card -->
	<script src="${fn:escapeXml(js)}/creditly.js"></script>
	<link rel="stylesheet" href="${fn:escapeXml(css)}/creditly.css" type="text/css" media="all" />

	<script>
		$(function () {
			var creditly = Creditly.initialize(
				'.creditly-wrapper .expiration-month-and-year',
				'.creditly-wrapper .credit-card-number',
				'.creditly-wrapper .security-code',
				'.creditly-wrapper .card-type');

			$(".creditly-card-form .submit").click(function (e) {
				e.preventDefault();
				var output = creditly.validate();
				if (output) {
					addPayment("CC");
					console.log(output);
				}
			});
		});
		
		function sendPaymentLink(){
			
			var email1 = $('#emailid1').val();
			var email2 = $('#emailid2').val();
			var email3 = $('#emailid3').val();
			var orderOwnerName = $('#orderOwnerName').val();
			var paymentToken = $("#paymentToken").val();
			if (email1 == "" && email2 == "" && email3 == "") {
				return;
			}
			var dataString = {"email1": email1 , "email2": email2, "email3": email3, "paymentToken" : paymentToken,"orderOwnerName" : orderOwnerName};
			
			doAjaxRequest("POST", "/gc/store/sendPaymentLink", dataString,
						function(response) {
							var responseJsonObj = JSON.parse(response);
							if (responseJsonObj.status == true) {
								var items = paypalm.minicartk.cart.items();
								var len = items.length;
								// Count the number of each item in the cart
								for (i = 0; i < len; i++) {
									paypalm.minicartk.cart.remove();
								}
								window.location.href = "/gc/store/orderconfirmation/${order.orderId}?paymentstatus=pending";
							} 
						}, function(e) {
						});
			}
		
			function addPayment(paymentType){
				var paymentToken = $("#paymentToken").val();
				var dataString = {"paymentType": paymentType,"paymentToken" : paymentToken};
				
				doAjaxRequest("POST", "/gc/store/addPayment", dataString,
							function(response) {
							var responseJsonObj = JSON.parse(response);
							if (responseJsonObj.status == true) {
								var items = paypalm.minicartk.cart.items();
								var len = items.length;
								// Count the number of each item in the cart
								for (i = 0; i < len; i++) {
									paypalm.minicartk.cart.remove();
								}
								window.location.href = "/gc/store/orderconfirmation/${order.orderId}?paymentstatus=complete";
							} 
						}, function(e) {
						});
			}
		
		function copyFunction() {
			  var copyText = $("#paymentToken");
			  var $temp = $("<input>");
			  $("body").append($temp);
			  $temp.val($(copyText).text()).select();
			  document.execCommand("copy");
			  $temp.remove();
			}
		
		 if (!isUserLoggedIn) {
			 $("#loginModelDiv").click();
		 }
	</script>
	<!-- //credit-card -->
	
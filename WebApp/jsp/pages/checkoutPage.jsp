<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

    <%@include file="fragments/header.jspf" %>
	<!-- //navigation -->
	<!-- banner-2 -->
	<div class="hide page-head_agile_info_w3l">

	</div>
	<!-- //banner-2 -->
	<!-- page -->
	<div class="services-breadcrumb hide">
		<div class="agile_inner_breadcrumb">
			<div class="container">
				<ul class="w3_short">
					<li>
						<a href="index.html">Home</a>
						<i>|</i>
					</li>
					<li>Checkout</li>
				</ul>
			</div>
		</div>
	</div>
	<!-- //page -->
	<!-- checkout page -->
	<div class="privacy">
		<div class="container">
			<!-- tittle heading -->
			<h3 class="tittle-w3l">Checkout
				<span class="heading-style">
					<i></i>
					<i></i>
					<i></i>
				</span>
			</h3>
			<!-- //tittle heading -->
			<div class="checkout-right">
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
			<div class="checkout-left">
				<div class="address_form_agile">
					<h4>Add a new Details</h4>
						<div class="creditly-wrapper wthree, w3_agileits_wrapper">
							<div class="information-wrapper">
								<div class="first-row">
									<div class="controls">
										<input class="billing-address-name" type="text" name="name" id="fullName" placeholder="Full Name" required="">
									</div>
									<div class="w3_agileits_card_number_grids">
										<div class="w3_agileits_card_number_grid_left">
											<div class="controls">
												<input type="text" placeholder="Mobile Number" name="number" id="mobileNumber" required="">
											</div>
										</div>
										<div class="w3_agileits_card_number_grid_right">
											<div class="controls">
												<input type="text" placeholder="Landmark" name="landmark" id="landMark" required="">
											</div>
										</div>
										<div class="clear"> </div>
									</div>
									<div class="controls">
										<input type="text" placeholder="Town/City" name="city" id="city" required="">
									</div>
									<div class="controls">
										<select class="option-w3ls" id="type">
											<option>Select Address type</option>
											<option>Office</option>
											<option>Home</option>
											<option>Commercial</option>

										</select>
									</div>
								</div>
								<button class="submit check_out" onclick="addBillingAddress();">Delivery to this Address</button>
							</div>
						</div>
					<div class="checkout-right-basket hide">
						<a href="payment.html">Make a Payment
							<span class="fa fa-hand-o-right" aria-hidden="true"></span>
						</a>
					</div>
				</div>
				<div class="clearfix"> </div>
			</div>
		</div>
	</div>
	<!-- //checkout page -->
	<!-- newsletter -->
	<%@include file="fragments/footer.jspf" %>
	
	<script>
	
	function addBillingAddress(){
		
		var fullName = $('#fullName').val();
		var landMark = $('#landMark').val();
		var mobileNumber = $('#mobileNumber').val();
		var city = $('#city').val();
		var type = $('#type').val();
		
		if (fullName == "" || landMark == "") {
			return;
		}
		var dataString = {"fullName":fullName , "landMark":landMark, "mobileNumber": mobileNumber, "city": city, "type": type };
		
		doAjaxRequest("POST", "/gc/store/addBillingAddress", dataString,
					function(response) {
						var responseJsonObj = JSON.parse(response);
						if (responseJsonObj.status == true) {
							window.location.href = "/gc/store/payment";
						} 
					}, function(e) {
					});
		}
	</script>
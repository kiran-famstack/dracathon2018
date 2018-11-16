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
					<li>Confirmation</li>
				</ul>
			</div>
		</div>
	</div>
	<!-- //page -->
	<!-- checkout page -->
	<div class="privacy">
		<div class="container">
			<!-- tittle heading -->
			<h3 class="tittle-w3l">Order Confirmaion
				<span class="heading-style">
					<i></i>
					<i></i>
					<i></i>
				</span>
			</h3>
				<div class="checkout-right">
			<c:if test="${not empty order and  not empty order.skuItems}">
				<h4>Your order has been placed successfully
				<c:if test="${param.paymentstatus == 'pending'}">
				 and Pending for payment!
				</c:if>
				<c:if test="${param.paymentstatus == 'complete'}">
				 completed!
				</c:if>
				</h4>
				<%@include file="fragments/cartSummary.jspf" %>
			</c:if>
			<c:if test="${empty order or  empty order.skuItems}">
				<h4>Your shopping cart is empty!
				</h4>
			</c:if>
			</div>
			</div>
		</div>
	</div>
	<!-- //checkout page -->
	<!-- newsletter -->
	<%@include file="fragments/footer.jspf" %>
	
	<script>
	 if (!isUserLoggedIn) {
		 $("#loginModelDiv").click();
	 }
	</script>
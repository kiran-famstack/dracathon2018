package com.famstack.projectscheduler.datatransferobject;

import java.sql.Timestamp;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

@Entity
@Table(name = "order_info", uniqueConstraints = {@UniqueConstraint(columnNames = {"order_id"})})
public class OrderItem implements FamstackBaseItem
{

    private static final long serialVersionUID = -5628656638213113049L;

    @Id
    @Column(name = "order_id")
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "id_Sequence")
    @SequenceGenerator(name = "id_Sequence", sequenceName = "ID_SEQ")
    private int orderId;

    @Column(name = "user_id")
    private int userId;
    
    @Column(name = "status")
    private String status;
    
    @Column(name = "sub_total")
    private Double subTotal;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "orderItem", cascade = CascadeType.ALL)
    private Set<SkuItem> skuItems;
    
    @OneToOne(fetch = FetchType.EAGER, orphanRemoval = false)
    @JoinColumn(name = "address_id")
    private AddressItem addressItem;
    
    @OneToOne(fetch = FetchType.EAGER, orphanRemoval = false)
    @JoinColumn(name = "payment_id")
    private PaymentItem paymentItem;
    
    @Column(name = "created_date")
    private Timestamp createdDate;

    @Column(name = "last_modified_date")
    private Timestamp lastModifiedDate;

	public int getOrderId() {
		return orderId;
	}

	public void setOrderId(int orderId) {
		this.orderId = orderId;
	}

	public int getUserId() {
		return userId;
	}

	public void setUserId(int userId) {
		this.userId = userId;
	}

	public Set<SkuItem> getSkuItems() {
		return skuItems;
	}

	public void setSkuItems(Set<SkuItem> skuItems) {
		this.skuItems = skuItems;
	}

	public AddressItem getAddressItem() {
		return addressItem;
	}

	public void setAddressItem(AddressItem addressItem) {
		this.addressItem = addressItem;
	}

	public PaymentItem getPaymentItem() {
		return paymentItem;
	}

	public void setPaymentItem(PaymentItem paymentItem) {
		this.paymentItem = paymentItem;
	}

	public Timestamp getCreatedDate() {
		return createdDate;
	}

	public void setCreatedDate(Timestamp createdDate) {
		this.createdDate = createdDate;
	}

	public Timestamp getLastModifiedDate() {
		return lastModifiedDate;
	}

	public void setLastModifiedDate(Timestamp lastModifiedDate) {
		this.lastModifiedDate = lastModifiedDate;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public Double getSubTotal() {
		return subTotal;
	}

	public void setSubTotal(Double subTotal) {
		this.subTotal = subTotal;
	}


}
package com.famstack.projectscheduler.datatransferobject;

import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

@Entity
@Table(name = "sku_info", uniqueConstraints = {@UniqueConstraint(columnNames = {"sku_id"})})
public class SkuItem implements FamstackBaseItem
{

    private static final long serialVersionUID = -5628656638213113049L;

    @Id
    @Column(name = "sku_id")
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "id_Sequence")
    @SequenceGenerator(name = "id_Sequence", sequenceName = "ID_SEQ")
    private int skuId;

    @ManyToOne
    @JoinColumn(name = "order_id")
    private OrderItem orderItem;
    
    @Column(name = "item_number")
    private String itemNumber;
    
    @Column(name = "item_name")
    private String itemName;
    
    @Column(name = "amount")
    private Double amount;
    
    @Column(name = "discount_amount")
    private Double discountAmount;
    
    @Column(name = "quantity")
    private Integer quantity;
    
    @Column(name = "created_date")
    private Timestamp createdDate;

    @Column(name = "last_modified_date")
    private Timestamp lastModifiedDate;

	public int getSkuId() {
		return skuId;
	}

	public void setSkuId(int skuId) {
		this.skuId = skuId;
	}

	public OrderItem getOrderItem() {
		return orderItem;
	}

	public void setOrderItem(OrderItem orderItem) {
		this.orderItem = orderItem;
	}

	public String getItemNumber() {
		return itemNumber;
	}

	public void setItemNumber(String itemNumber) {
		this.itemNumber = itemNumber;
	}

	public String getItemName() {
		return itemName;
	}

	public void setItemName(String itemName) {
		this.itemName = itemName;
	}

	public Double getAmount() {
		return amount;
	}

	public void setAmount(Double amount) {
		this.amount = amount;
	}

	public Double getDiscountAmount() {
		return discountAmount;
	}

	public Double getTotal() {
		return (amount * quantity)- discountAmount;
	}
	
	public void setDiscountAmount(Double discountAmount) {
		this.discountAmount = discountAmount;
	}

	public Integer getQuantity() {
		return quantity;
	}

	public void setQuantity(Integer quantity) {
		this.quantity = quantity;
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

}
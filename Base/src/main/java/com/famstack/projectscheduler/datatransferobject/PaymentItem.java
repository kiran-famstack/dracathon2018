package com.famstack.projectscheduler.datatransferobject;

import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

@Entity
@Table(name = "payment_info", uniqueConstraints = {@UniqueConstraint(columnNames = {"payment_id"})})
public class PaymentItem implements FamstackBaseItem
{

    private static final long serialVersionUID = -5628656638213113049L;

    @Id
    @Column(name = "payment_id")
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "id_Sequence")
    @SequenceGenerator(name = "id_Sequence", sequenceName = "ID_SEQ")
    private int paymentId;

    @Column(name = "user_id")
    private int paidBy;
    
    @Column(name = "amount")
    private Double amount;
    
    @Column(name = "status")
    private String status;

    @Column(name = "created_date")
    private Timestamp createdDate;

    @Column(name = "last_modified_date")
    private Timestamp lastModifiedDate;

	public int getPaymentId() {
		return paymentId;
	}

	public void setPaymentId(int paymentId) {
		this.paymentId = paymentId;
	}

	public int getPaidBy() {
		return paidBy;
	}

	public void setPaidBy(int paidBy) {
		this.paidBy = paidBy;
	}

	public Double getAmount() {
		return amount;
	}

	public void setAmount(Double amount) {
		this.amount = amount;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
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
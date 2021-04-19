package com.qa.ims.persistence.domain;

import java.text.SimpleDateFormat;
import java.util.Date;

public class Order {

    private Long id;
	private Long customerId;
	private Date date;
	private Double total;
	private String customerName;
	
	// Used for creating a fresh order
	public Order(Long customerId) {
		this.setCustomerId(customerId);
	}
	
	// Used for updating an order
	public Order(Long id, Long customerId) {
		this.setId(id);
		this.setCustomerId(customerId);
	}
	
	// Used for displaying an order
	public Order(Long id, String customerName, Date date) {
		this.setId(id);
		this.setCustomerName(customerName);
		this.setDate(date);
	}
	
	// Used for displaying order total
	public Order(Long id, String customerName, Date date, double total) {
		this.setId(id);
		this.setCustomerName(customerName);
		this.setDate(date);
		this.setTotal(total);
	}
	
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getCustomerId() {
		return customerId;
	}

	public void setCustomerId(Long customerId) {
		this.customerId = customerId;
	}

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}
	
	public Double getTotal() {
		return total;
	}

	public void setTotal(double total) {
		this.total = total;
	}
	
	public String getCustomerName() {
		return customerName;
	}
	
	public void setCustomerName(String name) {
		this.customerName = name;
	}
	@Override
	public String toString() {
		SimpleDateFormat fmt = new SimpleDateFormat("EEE MMM dd H:mmaa");
		String datefmt = fmt.format(date);
		return (total == null)?
			String.format("|Order ID   |%s%n|Customer   |%s%n|Order Date |%s", id, customerName, datefmt):
			String.format("|Order ID   |%s%n|Customer   |%s%n|Order Date |%s%n|Total      |£%s", id, customerName, datefmt, total);
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((customerId == null) ? 0 : customerId.hashCode());
		result = prime * result + ((customerName == null) ? 0 : customerName.hashCode());
		result = prime * result + ((date == null) ? 0 : date.hashCode());
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		result = prime * result + ((total == null) ? 0 : total.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Order other = (Order) obj;
		if (customerId == null) {
			if (other.customerId != null)
				return false;
		} else if (!customerId.equals(other.customerId))
			return false;
		if (customerName == null) {
			if (other.customerName != null)
				return false;
		} else if (!customerName.equals(other.customerName))
			return false;
		if (date == null) {
			if (other.date != null)
				return false;
		} else if (!date.equals(other.date))
			return false;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		if (total == null) {
			if (other.total != null)
				return false;
		} else if (!total.equals(other.total))
			return false;
		return true;
	}
}

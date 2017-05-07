package com.aspecttest.model;

import java.math.BigDecimal;

/**
 * @author duygu
 *
 */
public class Entity {

	private String id;
	// volatile is used to make field visible to all readers after a write operation completes on it
	private volatile BigDecimal price;
	private volatile boolean isPriceChanged;

	public Entity(String id, BigDecimal price) {
		this.id = id;
		this.price = price;
		isPriceChanged = true;
	}

	public BigDecimal getPrice() {
		return price;
	}

	public void setPrice(BigDecimal price) {
		this.price = price;
	}

	public boolean isPriceChanged() {
		return isPriceChanged;
	}

	public void setPriceChanged(boolean isPriceChanged) {
		this.isPriceChanged = isPriceChanged;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}
	
	@Override
	public String toString() {
		StringBuffer buff = new StringBuffer();
		buff.append("id:");
		buff.append(id);
		buff.append(" - ");
		buff.append("price:");
		buff.append(price);
		buff.append(" - ");
		buff.append("isPriceChanged:");
		buff.append(isPriceChanged);
		return buff.toString();
	}

}

package com.myretail.restservice.dto;

import com.fasterxml.jackson.annotation.JsonAlias;
import com.myretail.restservice.model.ProductPrice;

public class Product {

	private long id;
	private String name;
	@JsonAlias("current_price")
	private ProductPrice currentPrice;

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public ProductPrice getCurrentPrice() {
		return currentPrice;
	}

	public void setCurrentPrice(ProductPrice currentPrice) {
		this.currentPrice = currentPrice;
	}
}

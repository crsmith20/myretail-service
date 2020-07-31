package com.myretail.restservice.model;

import java.math.BigDecimal;

import org.springframework.data.annotation.Id;

import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonIgnore;

public class ProductPrice {

	@Id
	@JsonIgnore
	private long id;
	private BigDecimal value;
	@JsonAlias("current_code")
	private String currencyCode;

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public BigDecimal getValue() {
		return value;
	}

	public void setValue(BigDecimal value) {
		this.value = value;
	}

	public String getCurrencyCode() {
		return currencyCode;
	}

	public void setCurrencyCode(String currencyCode) {
		this.currencyCode = currencyCode;
	}
}

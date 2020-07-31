package com.myretail.restservice.service;

import com.myretail.restservice.model.ProductPrice;

public interface ProductPriceService {

	/**
	 * Gets price for the given products id. Throws a NotFoundException if the price
	 * isn't found in the data store.
	 *
	 * @param id the id of the product to get the price
	 * @return price the product price for the given id
	 */
	ProductPrice getProductPrice(long id);

	/**
	 * Saves the given product. Throws a IllegalArgumentException if the price given
	 * is null.
	 *
	 * @param price product price to save in the data store
	 * @return saved product price
	 */
	ProductPrice saveProductPrice(ProductPrice price);
}

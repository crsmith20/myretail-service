package com.myretail.restservice.service;

import com.myretail.restservice.dto.Product;

public interface ProductService {

	/**
	 * Gets the product for the given products id. Throws a NotFoundException if the
	 * product isn't found in the external api
	 *
	 * @param id the id of the product to get
	 * @return product the product for the given id
	 */
	Product getProduct(long id);

	/**
	 * Pass through to saveProductPrice. Checks if product price was given then
	 * saves it based on the product's id Throws a IllegalArgumentException if the
	 * product given is null or the price is null.
	 *
	 * @param id      the given id of the path
	 * @param product product to be saved
	 * @return product with the saved product price
	 */
	Product saveProduct(long id, Product product);
}

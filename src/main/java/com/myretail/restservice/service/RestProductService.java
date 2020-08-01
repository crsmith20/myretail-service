package com.myretail.restservice.service;

import com.myretail.restservice.dto.Product;

public interface RestProductService {

	/**
	 * Gets the product for the given id. If none found, throws NotFoundException.
	 *
	 * @param id of the product to get
	 * @return found product of the given id
	 */
	Product callForProduct(long id);
}

package com.myretail.restservice.service;

import com.myretail.restservice.dto.Product;

public interface ProductService {

	Product getProduct(long id);

	Product saveProduct(Product product);
}

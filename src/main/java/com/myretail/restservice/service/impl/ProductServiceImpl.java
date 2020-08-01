package com.myretail.restservice.service.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.myretail.restservice.dto.Product;
import com.myretail.restservice.model.ProductPrice;
import com.myretail.restservice.service.ProductPriceService;
import com.myretail.restservice.service.ProductService;
import com.myretail.restservice.service.RestProductService;

@Service
public class ProductServiceImpl implements ProductService {

	private static final Logger LOG = LoggerFactory.getLogger(ProductServiceImpl.class);

	@Autowired private ProductPriceService productPriceService;
	@Autowired private RestProductService restProductService;

	@Override
	public Product getProduct(long id) {
		final Product product = restProductService.callForProduct(id);
		final ProductPrice price = productPriceService.getProductPrice(id);
		product.setCurrentPrice(price);
		return product;
	}

	@Override
	public Product saveProduct(Product product) {
		LOG.debug("Saving product");

		if (product == null) {
			throw new IllegalArgumentException("product is null");
		}

		if (product.getCurrentPrice() == null) {
			throw new IllegalArgumentException("price is null");
		}

		if (product.getId() <= 0) {
			throw new IllegalArgumentException("id cannot be negative");
		}

		final ProductPrice price = product.getCurrentPrice();
		price.setId(product.getId());
		final ProductPrice saved = productPriceService.saveProductPrice(price);
		product.setCurrentPrice(saved);

		return product;
	}
}

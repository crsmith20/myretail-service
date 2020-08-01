package com.myretail.restservice.service.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.myretail.restservice.dto.Product;
import com.myretail.restservice.exception.NotFoundException;
import com.myretail.restservice.model.ProductPrice;
import com.myretail.restservice.service.ProductPriceService;
import com.myretail.restservice.service.ProductService;
import com.myretail.restservice.service.RestService;

@Service
public class ProductServiceImpl implements ProductService, RestService<Product> {

	private static final Logger LOG = LoggerFactory.getLogger(ProductServiceImpl.class);

	@Value("${product.url}")
	private String url;

	@Autowired private ProductPriceService productPriceService;

	@Override
	public Product getProduct(long id) {
		final Product product = get();
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

		if (product.getCurrentPrice().getId() <= 0) {
			throw new IllegalArgumentException("invalid id given");
		}

		final ProductPrice price = product.getCurrentPrice();
		price.setId(product.getId());
		final ProductPrice saved = productPriceService.saveProductPrice(price);
		product.setCurrentPrice(saved);

		return product;
	}

	@Override
	public Product get() {
		final RestTemplate get = new RestTemplate();
		final ResponseEntity<Product> product = get.getForEntity(url, Product.class);
		if (product.getBody() == null) {
			throw new NotFoundException("product not found");
		}
		return product.getBody();
	}
}

package com.myretail.restservice.service.impl;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.myretail.restservice.dto.Product;
import com.myretail.restservice.exception.NotFoundException;
import com.myretail.restservice.service.RestProductService;

@Service
public class RestProductServiceImpl extends AbstractRestService<Product> implements RestProductService {

	@Value("${product.url}")
	private String url;

	@Override
	public Product callForProduct(long id) {
		final ResponseEntity<Product> product = super.get(getUrl(id));
		if (product.getBody() == null) {
			throw new NotFoundException("product not found");
		}
		return product.getBody();
	}

	private String getUrl(long id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Class<Product> getDTOClass() {
		return Product.class;
	}

}

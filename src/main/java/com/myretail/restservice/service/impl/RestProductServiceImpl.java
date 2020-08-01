package com.myretail.restservice.service.impl;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.util.UriComponentsBuilder;

import com.myretail.restservice.dto.Product;
import com.myretail.restservice.exception.NotFoundException;
import com.myretail.restservice.service.RestProductService;

@Service
public class RestProductServiceImpl extends AbstractRestService<Product> implements RestProductService {

	@Value("${product.url}")
	private String url;

	@Value("${product.url.query}")
	private boolean query;

	@Override
	public Product callForProduct(long id) {
		final ResponseEntity<Product> product = super.get(getUrl(id));
		if (product.getBody() == null) {
			throw new NotFoundException("product not found");
		}
		return product.getBody();
	}

	/**
	 * Creates url from a given id. This is based off a given url and the type of
	 * url the id is passed by. If query is set to true then id is based through as
	 * a query parameter. If false, then it is based on inline part of url.
	 *
	 * @param id used to generate url
	 * @return url string
	 */
	private String getUrl(long id) {
		String completeUrl;
		if (query) {
			completeUrl = UriComponentsBuilder.fromUriString(url).queryParam("id", id).toUriString();
		} else {
			StringBuilder b = new StringBuilder(url);
			b.append(id);
			completeUrl = b.toString();
		}
		return completeUrl;
	}

	@Override
	public Class<Product> getDTOClass() {
		return Product.class;
	}
}

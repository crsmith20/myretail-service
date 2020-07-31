package com.myretail.restservice.service.impl;

import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.myretail.restservice.exception.NotFoundException;
import com.myretail.restservice.model.ProductPrice;
import com.myretail.restservice.repository.ProductPriceRepository;
import com.myretail.restservice.service.ProductPriceService;

@Service
public class ProductPriceServiceImpl implements ProductPriceService {

	private static final Logger LOG = LoggerFactory.getLogger(ProductPriceServiceImpl.class);

	@Autowired private ProductPriceRepository productPriceRepository;

	@Override
	public ProductPrice getProductPrice(long id) {
		LOG.debug("Getting product price for id {}", id);

		final Optional<ProductPrice> price = productPriceRepository.findById(id);
		if (price.isEmpty()) {
			throw new NotFoundException("Price for product " + id + " not found");
		}

		return price.get();
	}

	@Override
	public ProductPrice saveProductPrice(ProductPrice price) {
		LOG.debug("Saving product");

		if (price == null) {
			throw new IllegalArgumentException("Cannot save null object");
		}

		final ProductPrice savedPrice = productPriceRepository.save(price);
		return savedPrice;
	}
}

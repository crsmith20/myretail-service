package com.myretail.restservice.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.myretail.restservice.dto.Product;
import com.myretail.restservice.exception.NotFoundException;
import com.myretail.restservice.service.ProductService;

@RestController
@RequestMapping("/product")
public class ProductController {

	private final static Logger LOG = LoggerFactory.getLogger(ProductController.class);

	@Autowired private ProductService productService;

	@GetMapping("/{id}")
	public ResponseEntity<Product> getProduct(@PathVariable long id) {
		LOG.info("getting product for id {}", id);

		ResponseEntity<Product> response = null;
		try {
			final Product product = productService.getProduct(id);
			response = new ResponseEntity<Product>(product, HttpStatus.OK);
		} catch (NotFoundException | IllegalArgumentException e) {
			LOG.error("error getting product for id {}", id, e);
			response = new ResponseEntity<>(HttpStatus.NOT_FOUND);
		} catch (Exception e) {
			LOG.error("error getting product for id {}", id, e);
			response = new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
		return response;
	}

	@PutMapping("/{id}")
	public HttpStatus putProduct(@PathVariable long id, @RequestBody Product product) {
		LOG.info("putting product price for id {}", id);

		HttpStatus response = null;
		try {
			if (id != product.getId()) {
				throw new IllegalArgumentException("ids do not match");
			}
			productService.saveProduct(product);
			response = HttpStatus.OK;
		} catch (IllegalArgumentException e) {
			LOG.error("error saving product due to " + e.getMessage(), e);
			response = HttpStatus.NO_CONTENT;
		} catch (Exception e) {
			LOG.error("error saving product", e);
			response = HttpStatus.NOT_FOUND;
		}
		return response;
	}
}

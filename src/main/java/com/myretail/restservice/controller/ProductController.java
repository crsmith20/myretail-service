package com.myretail.restservice.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.myretail.restservice.service.ProductService;

@RequestMapping("/product")
@RestController
public class ProductController {

	private final static Logger LOG = LoggerFactory.getLogger(ProductController.class);

	@Autowired private ProductService productService;

	@GetMapping("/{id}")
	public void getProduct(@PathVariable long id) {

	}

	@PutMapping("/{id}")
	public void putProduct(@PathVariable long id) {

	}
}

package com.myretail.restservice.repository;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.myretail.restservice.model.ProductPrice;

public interface ProductPriceRepository extends MongoRepository<ProductPrice, Long> {

}

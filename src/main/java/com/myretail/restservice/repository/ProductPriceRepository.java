package com.myretail.restservice.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import com.myretail.restservice.model.ProductPrice;

@Repository
public interface ProductPriceRepository extends MongoRepository<ProductPrice, Long> {

}

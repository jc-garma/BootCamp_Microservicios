package com.everis.bootcamp.productservice;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ProductService {

    List<Product> findAll();

    Product findOne(String productId);

    Product insert (Product product);
}

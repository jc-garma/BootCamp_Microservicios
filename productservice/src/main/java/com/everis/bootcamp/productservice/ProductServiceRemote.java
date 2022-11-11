package com.everis.bootcamp.productservice;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@ConditionalOnProperty(name = "app.everis.bootcamp.services.price.type", havingValue = "remote")
public class ProductServiceRemote  implements ProductService{
    private static final Logger LOG = LoggerFactory.getLogger(ProductServiceRemote.class);

    private static final String SUFFIX_GET_PRICE = "/{id}";

    private final Map<Integer, Product> internalStorage = new HashMap<>(); //Mapa
    @Value("${app.everis.bootcamp.services.price.endpoint}")
    private String endpoint;

    private final RestTemplate restTemplate = new RestTemplate();

    @Override
    public List<Product> findAll() {
        final List<Product> resultList = this.internalStorage.values().stream().map(product -> {
                   final Price response = this.restTemplate.getForObject(endpoint+SUFFIX_GET_PRICE, Price.class, product.getId());
                   product.setPrice(response.getPriceValue());
                   return product; //Collect recoje el product
                }).collect(Collectors.toList());
                return resultList;

    }

    @Override
    public Product findOne(String productId) {
        if(productId != null){
            Product product = internalStorage.get(Integer.valueOf(productId));
            //Obtenemos toda la ResponseEntity, no solo el Body
            final ResponseEntity<Price> response = this.restTemplate.getForEntity(endpoint+SUFFIX_GET_PRICE, Price.class, product.getId());
            product.setPrice(response.getBody().getPriceValue());
            return product;
        }
        return null;
    }

    //Cuando añadimos un Producto además de su id y nombre, también su precio
    @Override
    public Product insert(Product product) {
        if(product != null && product.getId() != null){
            Integer priceValue = product.getPrice();
            product.setPrice(null);
            Price price = new Price(product.getId(), priceValue); //Para tenerlos asociados guadar en Price el precio del producto
            Price response = this.restTemplate.postForObject(endpoint,price, Price.class); //Solo /, post
            return this.internalStorage.putIfAbsent(product.getId(), product);
        }
        return null;
    }
}

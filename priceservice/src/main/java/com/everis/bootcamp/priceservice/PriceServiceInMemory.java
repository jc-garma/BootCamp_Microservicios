package com.everis.bootcamp.priceservice;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class PriceServiceInMemory implements PriceService {

    private final Map<Integer, Price> internalStorage = new HashMap<>();
    @Override
    public List<Price> findAll() {
        final List<Price> resultList = new ArrayList<>(internalStorage.values().size()); //inicializamos con tamaño por defecto
        resultList.addAll(internalStorage.values());
        return resultList;
    }

    @Override
    public Price findOne(Integer productId) {
        return (productId != null) ? this.internalStorage.getOrDefault(productId, new Price(productId, 50)) : null;

    }

    @Override
    public Price insert(Price price) {
        return(price != null && price.getProductId() != null) ? this.internalStorage.putIfAbsent(price.getProductId(), price) : price;
    }
}

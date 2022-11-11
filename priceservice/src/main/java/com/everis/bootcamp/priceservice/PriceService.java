package com.everis.bootcamp.priceservice;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface PriceService  {

    List<Price> findAll();


    Price findOne(Integer id);


    Price insert (Price price);
}

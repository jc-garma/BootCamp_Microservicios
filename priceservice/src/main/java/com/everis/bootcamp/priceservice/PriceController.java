package com.everis.bootcamp.priceservice;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.ws.rs.core.Response;
import java.util.List;

@RestController
public class PriceController {
    @Autowired
    private PriceService priceService;


    //No indicarle una url lo mismo que @GetMapping("/")
    @GetMapping
    public ResponseEntity<List<Price>> getPrices() {
        return ResponseEntity.ok(priceService.findAll());
    }

    @PostMapping
    public ResponseEntity<Price> addPrice(@RequestBody final Price price) {
        return (price != null && price.getProductId() != null) ? ResponseEntity.ok(priceService.insert(price)) : ResponseEntity.badRequest().build();
    }

    @GetMapping("/{idProduct}")
    public ResponseEntity<Price> getPrice(@PathVariable final Integer idProduct) {
        if (idProduct == 0) {
            throw new IllegalArgumentException();
        }
        return (idProduct != null) ? ResponseEntity.ok(priceService.findOne(idProduct)) : ResponseEntity.badRequest().build();
    }


    /*@PutMapping("/{id}")
    public Price updatePrice(@RequestBody Price price, @PathVariable Integer id) {
        return this.priceService.findById(id).map(
                price1 -> {
                    price1.setPriceValue(price.getPriceValue());
                    return this.priceService.save(price1);
                }).orElseGet(
                () -> {
                    price.setProductId(id);
                    return this.priceService.save(price);
                });
    }*/

    /*@DeleteMapping("/{id}")
    public void deletePrice(@PathVariable Integer id){
        this.priceService.deleteById(id);
    }*/
}


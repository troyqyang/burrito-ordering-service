package com.generali.burritoorderingservice.controller;

import com.generali.burritoorderingservice.dto.OrderDto;
import com.generali.burritoorderingservice.service.BurritoOrderingService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.concurrent.Future;

@RestController
@Slf4j
public class BurritoOrderingController {

    @Autowired
    BurritoOrderingService burritoOrderingService;

    @GetMapping("/")
    public String index() { return "Burrito Ordering Service Application is running."; }

    @GetMapping(value = "/orders/{orderId}", produces = { "application/json" })
    public ResponseEntity getOrder(@PathVariable(name = "orderId", required = true) Long orderId) {
        try {
            OrderDto orderDto = burritoOrderingService.getOrder(orderId);
            return new ResponseEntity(orderDto, HttpStatus.OK);
        } catch (Exception ex) {
            log.error("Error retrieving order with id: {}", orderId);
            return new ResponseEntity(HttpStatus.NOT_FOUND);
        }
    }

    @PostMapping(value = "/orders", produces = { "application/json" })
    public ResponseEntity addOrder(@RequestBody OrderDto orderDto) {
        Future<String> status = burritoOrderingService.addOrder(orderDto.getTortilla(), orderDto.getProtein(),
                orderDto.getVegetables(), orderDto.getSalsa(), orderDto.getExtras());
        if (status.isDone()) {
            return new ResponseEntity(HttpStatus.CREATED);
        } else {
            return new ResponseEntity(HttpStatus.NOT_MODIFIED);
        }
    }
}

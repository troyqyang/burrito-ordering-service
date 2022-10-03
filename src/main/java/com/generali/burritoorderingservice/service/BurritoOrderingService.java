package com.generali.burritoorderingservice.service;

import com.generali.burritoorderingservice.constants.Constants;
import com.generali.burritoorderingservice.dto.OrderDto;
import com.generali.burritoorderingservice.entity.ExtrasEntity;
import com.generali.burritoorderingservice.entity.OrderEntity;
import com.generali.burritoorderingservice.entity.VegetableEntity;
import com.generali.burritoorderingservice.repository.ExtrasJPARepository;
import com.generali.burritoorderingservice.repository.OrderJPARepository;
import com.generali.burritoorderingservice.repository.VegetableJPARepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.AsyncResult;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.Future;

@Service
@Slf4j
public class BurritoOrderingService {

    @Autowired
    OrderJPARepository orderJPARepository;

    @Autowired
    VegetableJPARepository vegetableJPARepository;

    @Autowired
    ExtrasJPARepository extrasJPARepository;

    @Async
    public Future<String> addOrder(Constants.Tortilla tortilla, Constants.Protein protein, HashMap<Constants.Vegetable,
            Integer> vegetables, Constants.Salsa salsa, HashMap<Constants.Extra, Integer> extras) {

        Future<String> status = new AsyncResult<>(Constants.FAILED);
        Long orderId = null;

        /*
            TODO: Improve logic for adding a row by leveraging JPA One-To-Many, etc. annotations with cascading setting.
        */
        try {
            OrderEntity order = new OrderEntity(tortilla, protein, salsa);
            orderJPARepository.save(order);

            /*
            TODO: Getting the order id after saving to the database slows adding new entries and could lead to partial
             transactions. Implement a transaction begin and close mechanism to avoid this issue. Try/catch does not
             guarantee a complete transaction.
            */
            Optional<OrderEntity> savedOrder = orderJPARepository.findTopByOrderByIdDesc();
            orderId = savedOrder.get().getId();
            Long finalOrderId = orderId;

            List<VegetableEntity> stagedVegetables = new ArrayList<>();
            vegetables.forEach((item, count) -> {
                VegetableEntity vegetable = new VegetableEntity(finalOrderId, item, count);
                stagedVegetables.add(vegetable);
            });
            List<ExtrasEntity> stagedExtras = new ArrayList<>();
            extras.forEach((item, count) -> {
                ExtrasEntity extra = new ExtrasEntity(finalOrderId, item, count);
                stagedExtras.add(extra);
            });
            stagedVegetables.forEach(vegetable -> {
                vegetableJPARepository.save(vegetable);
            });
            stagedExtras.forEach(extra -> {
                extrasJPARepository.save(extra);
            });

            status = new AsyncResult<>(Constants.SUCCESS);

        } catch (Exception ex) {
            orderJPARepository.deleteById(orderId);
            vegetableJPARepository.deleteByOrderId(orderId);
            extrasJPARepository.deleteByOrderId(orderId);
        }

        return status;
    }

    public OrderDto getOrder(Long orderId) {
        Optional<OrderEntity> order = orderJPARepository.findById(orderId);
        List<VegetableEntity> vegetables = vegetableJPARepository.findByOrderId(orderId);
        List<ExtrasEntity> extras = extrasJPARepository.findByOrderId(orderId);

        HashMap<Constants.Vegetable, Integer> vegetablesObject = new HashMap<>();
        vegetables.forEach(vegetable -> {
            vegetablesObject.put(vegetable.getVegetable(), vegetable.getCount());
        });

        HashMap<Constants.Extra, Integer> extrasObject = new HashMap<>();
        extras.forEach(extra -> {
            extrasObject.put(extra.getExtra(), extra.getCount());
        });

        OrderDto result = new OrderDto(order.get().getTortilla(), order.get().getProtein(), order.get().getSalsa());
        result.setVegetables(vegetablesObject);
        result.setExtras(extrasObject);
        return result;
    }
}

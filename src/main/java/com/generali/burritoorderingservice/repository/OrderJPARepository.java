package com.generali.burritoorderingservice.repository;

import com.generali.burritoorderingservice.entity.OrderEntity;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface OrderJPARepository extends CrudRepository<OrderEntity, Long> {

    @Override
    Optional<OrderEntity> findById(Long orderId);

    List<OrderEntity> findAll();

    Optional<OrderEntity> findTopByOrderByIdDesc();
}

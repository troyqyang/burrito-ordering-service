package com.generali.burritoorderingservice.repository;

import com.generali.burritoorderingservice.entity.VegetableEntity;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface VegetableJPARepository extends CrudRepository<VegetableEntity, Long> {

    List<VegetableEntity> findByOrderId(Long orderId);

    List<VegetableEntity> findAll();

    void deleteByOrderId(Long orderId);
}


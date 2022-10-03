package com.generali.burritoorderingservice.repository;

import com.generali.burritoorderingservice.entity.ExtrasEntity;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ExtrasJPARepository extends CrudRepository<ExtrasEntity, Long> {

    List<ExtrasEntity> findByOrderId(Long orderId);

    void deleteByOrderId(Long orderId);
}

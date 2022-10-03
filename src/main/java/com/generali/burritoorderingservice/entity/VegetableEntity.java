package com.generali.burritoorderingservice.entity;

import com.generali.burritoorderingservice.constants.Constants;
import lombok.Data;

import javax.persistence.*;

@Entity
@Table(name = "VEGETABLES")
@Data
public class VegetableEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "ID")
    private Long id;

    @Column(name = "ORDER_ID")
    private Long orderId;

    @Column(name = "VEGETABLE")
    private Constants.Vegetable vegetable;

    @Column(name = "COUNT")
    private int count;

    public VegetableEntity() {
    }

    public VegetableEntity(Long orderId, Constants.Vegetable vegetable, Integer count) {
        this.orderId = orderId;
        this.vegetable = vegetable;
        this.count = count;
    }
}

package com.generali.burritoorderingservice.entity;

import com.generali.burritoorderingservice.constants.Constants;
import lombok.Data;

import javax.persistence.*;

@Entity
@Table(name = "EXTRAS")
@Data
public class ExtrasEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "ID")
    private Long id;

    @Column(name = "ORDER_ID")
    private Long orderId;

    @Column(name = "EXTRA")
    private Constants.Extra extra;

    @Column(name = "COUNT")
    private int count;

    public ExtrasEntity() {
    }

    public ExtrasEntity(Long orderId, Constants.Extra extra, Integer count) {
        this.orderId = orderId;
        this.extra = extra;
        this.count = count;
    }
}
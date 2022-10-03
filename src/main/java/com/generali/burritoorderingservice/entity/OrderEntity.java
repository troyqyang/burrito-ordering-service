package com.generali.burritoorderingservice.entity;

import com.generali.burritoorderingservice.constants.Constants;
import lombok.Data;

import javax.persistence.*;

@Entity
@Table(name = "ORDERS")
@Data
public class OrderEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "ID")
    private Long id;

    @Column(name = "TORTILLA")
    private Constants.Tortilla tortilla;

    @Column(name = "PROTEIN")
    private Constants.Protein protein;

    @Column(name = "SALSA")
    private Constants.Salsa salsa;

    public OrderEntity() {}

    public OrderEntity(Constants.Tortilla tortilla, Constants.Protein protein, Constants.Salsa salsa) {
        this.tortilla = tortilla;
        this.protein = protein;
        this.salsa = salsa;
    }
}

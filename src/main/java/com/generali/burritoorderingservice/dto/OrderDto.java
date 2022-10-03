package com.generali.burritoorderingservice.dto;

import com.generali.burritoorderingservice.constants.Constants;

import java.util.HashMap;

public class OrderDto {

    private Constants.Tortilla tortilla;

    private Constants.Protein protein;

    private HashMap<Constants.Vegetable, Integer> vegetables;

    private Constants.Salsa salsa;

    private HashMap<Constants.Extra, Integer> extras;

    public OrderDto(Constants.Tortilla tortilla, Constants.Protein protein, Constants.Salsa salsa) {
        this.setTortilla(tortilla);
        this.setProtein(protein);
        this.setSalsa(salsa);
    }

    public Constants.Tortilla getTortilla() { return tortilla; }

    public void setTortilla(Constants.Tortilla tortilla) {
        this.tortilla = tortilla;
    }

    public Constants.Protein getProtein() { return protein; }

    public void setProtein(Constants.Protein protein) {
        this.protein = protein;
    }

    public Constants.Salsa getSalsa() { return salsa; }

    public void setSalsa(Constants.Salsa salsa) {
        this.salsa = salsa;
    }

    public HashMap<Constants.Vegetable, Integer> getVegetables() { return vegetables; }

    public void setVegetables(HashMap<Constants.Vegetable, Integer> vegetables) {
        this.vegetables = vegetables;
    }

    public HashMap<Constants.Extra, Integer> getExtras() { return extras; }

    public void setExtras(HashMap<Constants.Extra, Integer> extras) {
        this.extras = extras;
    }
}
